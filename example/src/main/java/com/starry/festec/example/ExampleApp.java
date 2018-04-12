package com.starry.festec.example;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.starry.latte.app.Latte;
import com.starry.latte.ec.icon.FontECModule;

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
                .withApiHost("http://127.0.0.1/")
                .configure();
    }
}
