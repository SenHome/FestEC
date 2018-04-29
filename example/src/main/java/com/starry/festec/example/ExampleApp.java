package com.starry.festec.example;

import android.app.Application;
import android.support.annotation.Nullable;
import android.telecom.Call;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.starry.latte.app.Latte;
import com.starry.festec.example.event.TestEvent;
import com.starry.latte.ec.database.DatabaseManager;
import com.starry.latte.ec.icon.FontECModule;
import com.starry.latte.interceptors.DebugInterceptor;
import com.starry.latte.util.callback.CallBackManager;
import com.starry.latte.util.callback.CallbackType;
import com.starry.latte.util.callback.IGlobalCallback;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by wangsen on 2018/4/5.
 */

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontECModule())
                .withLoaderDelayed(1000)
                .withApiHost("http://192.168.0.117/RestServer/api/")
                .withInterceptor(new DebugInterceptor("index", R.raw.text))
                .withWeChatAppId("")
                .withWeChatAppSecret("")
                .withJavascriptInterface("latte")
                .withWebEvent("test",new TestEvent())
                .configure();


        initStetho();
        //初始化数据库
        DatabaseManager.getInstance().init(this);
        //初始化极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        //设置打开推回调，接口形式
        CallBackManager.getInstance()
                .addCallback(CallbackType.TAG_OPEN_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (JPushInterface.isPushStopped(Latte.getApplication())) {
                            //开启极光推送
                            JPushInterface.setDebugMode(true);
                            JPushInterface.init(Latte.getApplication());
                        }
                    }
                })
                .addCallback(CallbackType.TAG_STOP_PUSH, new IGlobalCallback() {
                    @Override
                    public void executeCallback(@Nullable Object args) {
                        if (!JPushInterface.isPushStopped(Latte.getApplication())) {
                            JPushInterface.stopPush(Latte.getApplication());
                        }
                    }
                });

    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
