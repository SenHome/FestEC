package com.starry.festec.example;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.starry.latte.app.Latte;
import com.starry.latte.ec.database.DatabaseManager;
import com.starry.latte.ec.icon.FontECModule;
import com.starry.latte.interceptors.DebugInterceptor;

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
                .withApiHost("http://127.0.0.1/")
                .withInterceptor(new DebugInterceptor("index", R.raw.text))
                .withWeChatAppId("")
                .withWeChatAppSecret("")
                .configure();

//        initStetho();
        //初始化数据库
        DatabaseManager.getInstance().init(this);

    }

//    private void initStetho() {
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                        .build());
//    }
}
