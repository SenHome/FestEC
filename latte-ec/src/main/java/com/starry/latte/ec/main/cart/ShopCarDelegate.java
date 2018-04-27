package com.starry.latte.ec.main.cart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.joanzapata.iconify.widget.IconTextView;
import com.starry.latte.delegates.IPageLoadListener;
import com.starry.latte.delegates.bottom.BottomItemDelegate;
import com.starry.latte.ec.R;
import com.starry.latte.ec.R2;
import com.starry.latte.ec.main.EcBottomDelegate;
import com.starry.latte.ec.pay.FastPay;
import com.starry.latte.ec.pay.IAIPayResultListener;
import com.starry.latte.net.RestClient;
import com.starry.latte.net.callback.ISuccess;
import com.starry.latte.ui.loader.LatteLoader;
import com.starry.latte.ui.recycler.MultipleItemEntity;
import com.starry.latte.util.log.LatteLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wangsen on 2018/4/24.
 */

public class ShopCarDelegate extends BottomItemDelegate implements ISuccess, ICartItemListener,IAIPayResultListener{

    private ShopCarAdapter mAdapter = null;
    //购物车数量标记
    private int mCurrentCount = 0;
    private int mTotalCount = 0;
    //初始化总价
    private double mTotalPrice = 0.00;

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelecAll = null;
    @BindView(R2.id.stub_no_item)
    ViewStubCompat mStubNoItem = null;
    @BindView(R2.id.tv_shop_cart_total_price)
    AppCompatTextView mTvTotalPrice = null;

    @OnClick(R2.id.icon_shop_cart_select_all)
    void onClickSelectAll(){
        final int tag = (int) mIconSelecAll.getTag();
        if(tag == 0){
            mIconSelecAll.setTextColor(ContextCompat.getColor(getContext(),R.color.app_main));
            mIconSelecAll.setTag(1);
            mAdapter.setIsSelectedAll(true);
            mAdapter.notifyItemRangeChanged(0,mAdapter.getItemCount());

        }else{
            mIconSelecAll.setTextColor(Color.GRAY);
            mIconSelecAll.setTag(0);
            mAdapter.setIsSelectedAll(false);
            mAdapter.notifyItemRangeChanged(0,mAdapter.getItemCount());
        }

    }


    @OnClick(R2.id.tv_top_shop_cart_remove_selected)
    void onClickRemoveSeletedItem(){
        final List<MultipleItemEntity> data = mAdapter.getData();
        //要删除的数据
        final List<MultipleItemEntity> deleteEntities = new ArrayList<>();
        for (MultipleItemEntity entity : data){
            final boolean isSelected = entity.getField(ShopCarItemFiles.IS_SELECTED);
            if(isSelected){
                deleteEntities.add(entity);
            }
        }

        for (int i = 0; i < deleteEntities.size(); i++) {
            int DataCount = data.size();
            int currentPosition = deleteEntities.get(i).getField(ShopCarItemFiles.POSITION);
            if(currentPosition < data.size()){
                mAdapter.remove(currentPosition);
                for(;currentPosition<DataCount -1;currentPosition++){
                    int rawItemPos = data.get(currentPosition).getField(ShopCarItemFiles.POSITION);
                    data.get(currentPosition).setField(ShopCarItemFiles.POSITION,rawItemPos - 1);
                }
            }
        }
        checkItemCount();

    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    void onClickClear(){
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        checkItemCount();

    }

    @OnClick(R2.id.tv_shop_cart_pay)
    void onClickPay(){
//        FastPay.create(this).beginPayDialog();
        createOrder();
    }

    //创建订单，和支付是没有关系的
    private void createOrder(){
        final String orderUrl = "你的生成订单的API";
        final WeakHashMap<String,Object> orderParams = new WeakHashMap<>();
        orderParams.put("userid","dfdf");
        orderParams.put("amount",0.01);
        orderParams.put("comment","测试支付");
        orderParams.put("type",1);
        RestClient.builder()
                .url(orderUrl)
                .loader(getContext())
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSucess(String response) {
                        //进行支付过程
                        LatteLogger.d("ORDER",response);

                        final int orderId = JSON.parseObject(response).getInteger("result");

                        FastPay.create(ShopCarDelegate.this)
                                .setPayResultListener(ShopCarDelegate.this)
                                .setOrderId(orderId)
                                .beginPayDialog();


                    }
                })
                .build()
                .post();
    }

    @SuppressWarnings("RestrictedApi")
    private void checkItemCount() {
        final int count = mAdapter.getItemCount();
        if (count == 0) {
            final View stubView = mStubNoItem.inflate();
            final AppCompatTextView tvToBuy = stubView.findViewById(R.id.tv_stub_to_buy);
            tvToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "你该购物啦！", Toast.LENGTH_SHORT).show();
                    final int indexTab = 0;
                    final EcBottomDelegate ecBottomDelegate = getParentDelegate();
                    final BottomItemDelegate indexDelegate = ecBottomDelegate.getItemDelegates().get(indexTab);
                    ecBottomDelegate
                            .getSupportDelegate()
                            .showHideFragment(indexDelegate, ShopCarDelegate.this);
                    ecBottomDelegate.changeColor(indexTab);
                }
            });
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_car;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
//        super.onBindView(savedInstanceState, rootView);
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
        mAdapter.setCartItemListener(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mTotalPrice = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(mTotalPrice));
        checkItemCount();

    }

    @Override
    public void onItemClick(double itemTotalPrice) {
        final double price = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(price));
    }


    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }


}
