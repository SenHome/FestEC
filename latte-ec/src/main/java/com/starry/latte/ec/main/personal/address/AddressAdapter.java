package com.starry.latte.ec.main.personal.address;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.starry.latte.ec.R;
import com.starry.latte.net.RestClient;
import com.starry.latte.net.callback.ISuccess;
import com.starry.latte.ui.recycler.MultipleFields;
import com.starry.latte.ui.recycler.MultipleItemEntity;
import com.starry.latte.ui.recycler.MultipleRecyclerAdapter;
import com.starry.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

/**
 * Created by wangsen on 2018/4/27.
 */

public class AddressAdapter extends MultipleRecyclerAdapter {

    protected AddressAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(AddressItemType.ITEM_ADDRESS, R.layout.item_address);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, MultipleItemEntity item) {
        super.convert(holder, item);
        switch (holder.getItemViewType()) {
            case AddressItemType.ITEM_ADDRESS:
                final String name = item.getField(MultipleFields.NAME);
                final Boolean isDefault = item.getField(MultipleFields.TAG);
                final int id = item.getField(MultipleFields.ID);
                final String phone = item.getField(AddressItemFileds.PHONE);
                final String address = item.getField(AddressItemFileds.ADDRESS);

                final AppCompatTextView nameText = holder.getView(R.id.tv_address_name);
                final AppCompatTextView phoneText = holder.getView(R.id.tv_address_phone);
                final AppCompatTextView addressText = holder.getView(R.id.tv_address_address);
                final AppCompatTextView deleteText = holder.getView(R.id.tv_address_delete);
                //地址右上角删除
                deleteText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RestClient.builder()
                                .url("address.php")
                                .params("id",id)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSucess(String response) {
                                        remove(holder.getLayoutPosition());
                                    }
                                })
                                .build()
                                .post();
                    }
                });

                //设置值
                nameText.setText(name);
                phoneText.setText(phone);
                addressText.setText(address);

                break;
            default:
                break;
        }
    }
}
