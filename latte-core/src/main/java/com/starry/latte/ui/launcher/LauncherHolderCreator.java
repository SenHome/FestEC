package com.starry.latte.ui.launcher;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * Created by wangsen on 2018/4/17.
 */

public class LauncherHolderCreator implements CBViewHolderCreator<LauncherHolder> {


    @Override
    public LauncherHolder createHolder() {
        return new LauncherHolder();
    }
}
