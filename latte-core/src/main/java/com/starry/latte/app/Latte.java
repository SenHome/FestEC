package com.starry.latte.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import java.util.HashMap;

/**
 * Created by wangsen on 2018/4/3.
 * 对外的工具类，静态方法，
 * Latte相当于Buidler模式中的具体产品Computer
 */

public final class Latte {


    public static Configurator init(Context context){
        Configurator.getInstance().getLatteConfigs()
                .put(ConfigKeys.APPLICATION_CONTEXT,context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key){
        return getConfigurator().getConfiguration(key);
    }

    public static Application getApplication(){
        return getConfiguration(ConfigKeys.APPLICATION_CONTEXT);

    }

    public static Configurator getConfigurator(){
        return Configurator.getInstance();
    }

    public static Handler getHandler(){
        return getConfiguration(ConfigKeys.HANDLER);
    }

}
