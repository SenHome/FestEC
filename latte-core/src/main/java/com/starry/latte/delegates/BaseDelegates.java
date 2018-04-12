package com.starry.latte.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by wangsen on 2018/4/4.
 * 不希望new出实例，抽象类
 * 子类传入布局，View,layoutID
 */

public abstract class BaseDelegates extends SwipeBackFragment {

    //注解，拼写错误的检查，忽视掉
    @SuppressWarnings("SpellCheckingInspection")
    private Unbinder mUnbinder = null;

    //进行乙烯类操作，强制子类实现方法,可控的savedInstanceState，视图
    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rootView);

    public abstract Object setLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((Integer) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        }
        if (rootView != null) {
            mUnbinder = ButterKnife.bind(this,rootView);
            onBindView(savedInstanceState,rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mUnbinder != null){
            mUnbinder.unbind();
        }
    }
}
