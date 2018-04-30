package com.starry.latte.ec.main.personal.order;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.starry.latte.util.log.DeBugKeys;

/**
 * Created by wangsen on 2018/4/29.
 */

public class OrderListClickListener  extends SimpleClickListener{

    private final OrderListDelegate DELEGATE;

    public OrderListClickListener(OrderListDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DELEGATE.getSupportDelegate().start(new OrderCommentDelegate());
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

//


}
