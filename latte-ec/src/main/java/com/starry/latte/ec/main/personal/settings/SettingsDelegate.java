package com.starry.latte.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.ec.R;
import com.starry.latte.ec.R2;
import com.starry.latte.ec.main.personal.PersonOnClickListener;
import com.starry.latte.ec.main.personal.address.AddressDelegate;
import com.starry.latte.ec.main.personal.list.ListAdapter;
import com.starry.latte.ec.main.personal.list.ListBean;
import com.starry.latte.ec.main.personal.list.ListItemType;
import com.starry.latte.util.callback.CallBackManager;
import com.starry.latte.util.callback.CallbackType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangsen on 2018/4/29.
 */

public class SettingsDelegate extends LatteDelegate {

    @BindView(R2.id.rv_settings)
    RecyclerView mRecyclerView = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        ListBean push = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_SWITCH)
                .setId(1)
                .setDelegate(new AddressDelegate())
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            CallBackManager.getInstance().getCallback(CallbackType.TAG_OPEN_PUSH).executeCallback(null);
                        } else {
                            CallBackManager.getInstance().getCallback(CallbackType.TAG_STOP_PUSH).executeCallback(null);
                        }
                    }
                })
                .setText("消息推送")
                .build();

        ListBean about = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setDelegate(new AboutDelegate())
                .setText("关于")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(push);
        data.add(about);



        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        //加入地址监听
        mRecyclerView.addOnItemTouchListener(new SettingOnClickListener(this));

    }


}
