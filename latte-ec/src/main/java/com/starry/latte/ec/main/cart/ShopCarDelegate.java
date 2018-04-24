package com.starry.latte.ec.main.cart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.starry.latte.delegates.bottom.BottomItemDelegate;
import com.starry.latte.ec.R;
import com.starry.latte.ec.R2;
import com.starry.latte.net.RestClient;
import com.starry.latte.net.callback.ISuccess;
import com.starry.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wangsen on 2018/4/24.
 */

public class ShopCarDelegate extends BottomItemDelegate implements ISuccess {

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelecAll = null;

    @OnClick(R2.id.icon_shop_cart_select_all)
    void onClickSelectAll(){
        final int tag = (int) mIconSelecAll.getTag();
        if(tag == 0){
            mIconSelecAll.setTextColor(ContextCompat.getColor(getContext(),R.color.app_main));
            mIconSelecAll.setTag(1);
            mAdapter.setIsSelectedAll(true);
            mAdapter.notifyItemRangeChanged(0,mAdapter.getItemCount());

        }else {
            mIconSelecAll.setTextColor(Color.GRAY);
            mIconSelecAll.setTag(0);
            mAdapter.setIsSelectedAll(false);
            mAdapter.notifyItemRangeChanged(0,mAdapter.getItemCount());
        }
    }

    private ShopCarAdapter mAdapter = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_car;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
        mIconSelecAll.setTag(0);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("shop_cart.php")
                .loader(getContext())
                .success(this)
                .build()
                .get();

    }

    @Override
    public void onSucess(String response) {

        final ArrayList<MultipleItemEntity> data =
                new ShopCarDataConverter().setJsonData(response).convert();
        mAdapter = new ShopCarAdapter(data);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
