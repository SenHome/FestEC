package com.starry.latte.ec.main.personal.settings;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.ec.main.personal.list.ListBean;

/**
 * Created by wangsen on 2018/4/29.
 */

public class SettingOnClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    public SettingOnClickListener(LatteDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        int id = bean.getId();
        switch (id) {
            case 1:
                //这是消息推送的开关
                break;
            case 2:
                DELEGATE.getSupportDelegate().start(bean.getDelegate());
                break;
            default:
                break;
        }
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
