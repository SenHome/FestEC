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

import com.joanzapata.iconify.widget.IconTextView;
import com.starry.latte.delegates.bottom.BottomItemDelegate;
import com.starry.latte.ec.R;
import com.starry.latte.ec.R2;
import com.starry.latte.net.RestClient;
import com.starry.latte.net.callback.ISuccess;
import com.starry.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wangsen on 2018/4/24.
 */

public class ShopCarDelegate extends BottomItemDelegate implements ISuccess {

    private ShopCarAdapter mAdapter = null;
    //购物车数量标记
    private int mCurrentCount = 0;
    private int mTotalCount = 0;

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelecAll = null;
    @BindView(R2.id.stub_no_item)
    ViewStubCompat mStubNoItem = null;

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
//        final List<MultipleItemEntity> data = mAdapter.getData();
//        //要删除的数据
//        final List<MultipleItemEntity> deleteEntities = new ArrayList<>();
//        for(MultipleItemEntity entity : data){
//            final boolean isSelected = entity.getField(ShopCarItemFiles.IS_SELECTED);
//            if(isSelected){
//                deleteEntities.add(entity);
//            }
//        }
//        //循环选择删除的
//        for(MultipleItemEntity entity : deleteEntities){
//            int removePosition;
//            //item的position
//            final int entityPosition = entity.getField(ShopCarItemFiles.POSITION);
//            if(entityPosition > mCurrentCount - 1){
//                removePosition = entityPosition - (mTotalCount - mCurrentCount);
//            }else {
//                removePosition = entityPosition;
//            }
//
//            if(removePosition <= mAdapter.getItemCount()){
//                mAdapter.remove(removePosition);
//                mCurrentCount = mAdapter.getItemCount();
//                //更新数据
//                mAdapter.notifyItemRangeChanged(removePosition,mAdapter.getItemCount());
//            }
//
//
//        }

//        //============================================
//        final List<MultipleItemEntity> data = mAdapter.getData();
//        // 要删除的数据
//        final List<MultipleItemEntity> deleteEntities = new ArrayList<>();
//        int i = 0;
//        for (MultipleItemEntity entity : data){
//            final boolean isSelected = entity.getField(ShopCarItemFiles.IS_SELECTED);
//            entity.setField(ShopCarItemFiles.POSITION, i);
//            if (isSelected){
//                deleteEntities.add(entity);
//            }
//            i++;
//        }
//        for (MultipleItemEntity entity : deleteEntities){
//            final int removePosition = entity.getField(ShopCarItemFiles.POSITION);
//            if (removePosition <= mAdapter.getItemCount()) {
//                mAdapter.remove(removePosition);
//                //更新数据
//                mAdapter.notifyItemRangeChanged(removePosition, mAdapter.getItemCount());
//            }
//        }

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
