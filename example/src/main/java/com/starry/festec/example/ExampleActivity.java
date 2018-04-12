package com.starry.festec.example;



import com.starry.latte.activity.ProxyActivity;
import com.starry.latte.delegates.LatteDelegate;

public class ExampleActivity extends ProxyActivity {


    @Override
    public LatteDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
