package com.starry.latte.ec.main.cart;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.joanzapata.iconify.widget.IconTextView;
import com.starry.latte.app.Latte;
import com.starry.latte.ec.R;
import com.starry.latte.net.RestClient;
import com.starry.latte.net.callback.ISuccess;
import com.starry.latte.ui.recycler.MultipleFields;
import com.starry.latte.ui.recycler.MultipleItemEntity;
import com.starry.latte.ui.recycler.MultipleRecyclerAdapter;
import com.starry.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by wangsen on 2018/4/24.
 */

public class ShopCarAdapter extends MultipleRecyclerAdapter {

    private boolean mIsSeledtedAll = false;
    private ICartItemListener mICartItemListener = null;
    private double mTotalPrice = 0.0;

    protected ShopCarAdapter(List<MultipleItemEntity> data) {
        super(data);
        //初始化总价
        for(MultipleItemEntity entity : data){
            final double price = entity.getField(ShopCarItemFiles.PRICE);
            final int count = entity.getField(ShopCarItemFiles.COUNT);
            final double total = price*count;
            mTotalPrice = mTotalPrice + total;
        }
        //添加购物车item布局
        addItemType(ShopCarItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);

    }

    public void setIsSelectedAll(boolean isSelectedAll){
        this.mIsSeledtedAll = isSelectedAll;

    }

    public void setCartItemListener(ICartItemListener listener){
        this.mICartItemListener = listener;

    }

    public double getTotalPrice(){
        return mTotalPrice;
    }





    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity item) {
        super.convert(holder, item);
        switch (holder.getItemViewType()){
            case ShopCarItemType.SHOP_CART_ITEM:
                final int id = item.getField(MultipleFields.ID);
                final String thumb = item.getField(MultipleFields.IMAGE_URL);
                final String title = item.getField(ShopCarItemFiles.TITLE);
                final String desc = item.getField(ShopCarItemFiles.DESC);
                final int count = item.getField(ShopCarItemFiles.COUNT);
                final double price = item.getField(ShopCarItemFiles.PRICE);

                //取出所有控件
                final AppCompatImageView imgThumb = holder.getView(R.id.image_item_shop_cart);
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_item_shop_cart_title);
                final AppCompatTextView tvDesc = holder.getView(R.id.tv_item_shop_cart_desc);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_item_shop_cart_price);
                final IconTextView iconMinus = holder.getView(R.id.icon_item_minus);
                final IconTextView iconPlus = holder.getView(R.id.icon_item_plus);
                final AppCompatTextView tvCount = holder.getView(R.id.tv_item_shop_cart_count);
                final IconTextView iconIsSelected = holder.getView(R.id.icon_item_shop_cart);

                //赋值
                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvCount.setText(String.valueOf(count));
                tvPrice.setText(String.valueOf(price));
                Glide.with(mContext)
                        .load(thumb)
                        .into(imgThumb);

                //左侧勾勾宣言之前改变状态
                item.setField(ShopCarItemFiles.IS_SELECTED,mIsSeledtedAll);

                final boolean isSelected = item.getField(ShopCarItemFiles.IS_SELECTED);

                //根据数据状态，显示左侧勾勾
                if(isSelected){
                    iconIsSelected.setTextColor(ContextCompat.getColor(
                            Latte.getApplication(),R.color.app_main));
                }else {
                    iconIsSelected.setTextColor(Color.GRAY);
                }

                //添加点击事件
                iconIsSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean currentSelected = item.getField(ShopCarItemFiles.IS_SELECTED);
                        if(currentSelected){
                            iconIsSelected.setTextColor(Color.GRAY);
                            item.setField(ShopCarItemFiles.IS_SELECTED,false);
                        }else {
                            iconIsSelected.setTextColor
                                    (ContextCompat.getColor(Latte.getApplication(),R.color.app_main));
                            item.setField(ShopCarItemFiles.IS_SELECTED,true);
                        }
                    }
                });

                //添加加减事件
                iconMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentCount = item.getField(ShopCarItemFiles.COUNT);
                        if(Integer.parseInt(tvCount.getText().toString()) > 1){
                            RestClient.builder()
                                    .url("shop_cart_count.php")
                                    .loader(mContext)
                                    .params("count",currentCount)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSucess(String response) {
                                            int countNum = Integer.parseInt(tvCount.getText().toString());
                                            countNum--;
                                            tvCount.setText(String.valueOf(countNum));

                                            //加减对总价变更
                                            if(mICartItemListener != null){
                                                mTotalPrice = mTotalPrice - price;
                                                final double itemTotal = countNum * price;
                                                mICartItemListener.onItemClick(itemTotal);
                                            }
                                        }
                                    })
                                    .build()
                                    .post();
                        }
                    }
                });

                iconPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentCount = item.getField(ShopCarItemFiles.COUNT);
                            RestClient.builder()
                                    .url("shop_cart_count.php")
                                    .loader(mContext)
                                    .params("count",currentCount)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSucess(String response) {
                                            int countNum = Integer.parseInt(tvCount.getText().toString());
                                            countNum++;
                                            tvCount.setText(String.valueOf(countNum));

                                            //加减对总价变更
                                            if(mICartItemListener != null){
                                                mTotalPrice = mTotalPrice + price;
                                                final double itemTotal = countNum * price;
                                                mICartItemListener.onItemClick(itemTotal);
                                            }
                                        }
                                    })
                                    .build()
                                    .post();
                        }
                });
                break;
            default:
                break;
        }
    }
}
