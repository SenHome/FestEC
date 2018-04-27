package com.starry.latte.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.starry.latte.delegates.bottom.BottomItemDelegate;
import com.starry.latte.ec.R;
import com.starry.latte.ec.main.sort.content.ContentDelegate;
import com.starry.latte.ec.main.sort.list.VerticalListDelegate;

/**
 * Created by wangsen on 2018/4/20.
 * 分类页面
 */

public class SortDelegate extends BottomItemDelegate {


    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
//        super.onBindView(savedInstanceState, rootView);
    }



    //懒加载
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        //左边菜单列表
        final VerticalListDelegate listDelegate = new VerticalListDelegate();
        getSupportDelegate().loadRootFragment(R.id.vertical_list_container, listDelegate);
        //设置右侧第一个分类显示，默认显示分类一
        getSupportDelegate().loadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(1));
//        getSupportDelegate().loadRootFragment(R.id.sort_content_container,ContentDelegate.newInstance(1),false,false);


    }
}
