package com.starry.latte.ec.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.ec.detail.GoodsDetailDelegate;

import java.security.Principal;

/**
 * Created by wangsen on 2018/4/22.
 * 首页item点击事件
 */

public class IndexItemClickListener extends SimpleClickListener {

    //传进Delegate实例
   private final LatteDelegate DELEGATE;

   private IndexItemClickListener(LatteDelegate delegate){
       this.DELEGATE = delegate;

   }

   //简单工厂模式
    public static SimpleClickListener create(LatteDelegate delegate){
       return new IndexItemClickListener(delegate);

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final GoodsDetailDelegate delegate = GoodsDetailDelegate.create();
        DELEGATE.start(delegate);
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
