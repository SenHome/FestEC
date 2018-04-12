package com.starry.latte.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by wangsen on 2018/4/5.
 * 中间层，权限检查
 */

public abstract class PermissionCheckerDelegate extends BaseDelegates {
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public Object setLayout() {
        return null;
    }
}
