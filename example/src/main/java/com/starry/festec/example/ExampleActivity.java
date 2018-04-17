package com.starry.festec.example;



import com.starry.latte.activity.ProxyActivity;
import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.ec.launcher.LauncherDelegate;
import com.starry.latte.ec.launcher.LauncherScrollDelegate;

public class ExampleActivity extends ProxyActivity {


    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherScrollDelegate();
    }
}
