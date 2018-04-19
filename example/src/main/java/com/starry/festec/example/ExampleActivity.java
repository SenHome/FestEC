package com.starry.festec.example;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.starry.latte.activity.ProxyActivity;
import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.ec.launcher.LauncherDelegate;
import com.starry.latte.ec.sign.ISignListener;
import com.starry.latte.ec.sign.SingInDelegate;
import com.starry.latte.ui.launcher.ILauncherListener;
import com.starry.latte.ui.launcher.OnLauncherFinishTag;

import android.support.v7.app.ActionBar;
import android.widget.Toast;


public class ExampleActivity extends ProxyActivity implements ISignListener, ILauncherListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }


    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSignUpSucess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                Toast.makeText(this, "启动结束，用户登陆了", Toast.LENGTH_SHORT).show();
                startWithPop(new ExampleDelegate());
                break;
            case NOT_SIGNED:
                Toast.makeText(this, "启动结束，用户没登陆了", Toast.LENGTH_SHORT).show();
                startWithPop(new SingInDelegate());
                break;
            default:
                break;
        }
    }
}
