package com.starry.festec.example;



import android.os.Bundle;
import android.support.annotation.Nullable;

import com.starry.latte.activity.ProxyActivity;
import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.ec.launcher.LauncherDelegate;
import android.support.v7.app.ActionBar;


public class ExampleActivity extends ProxyActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }
}
