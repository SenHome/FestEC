package com.starry.latte.ec.main.personal.address;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.starry.latte.ui.recycler.DataConverter;
import com.starry.latte.ui.recycler.MultipleFields;
import com.starry.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by wangsen on 2018/4/27.
 */

public class AddressDataConverter extends DataConverter{


    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = array.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");
            final String phone = data.getString("phone");
            final String address = data.getString("address");
            final boolean isDefault = data.getBoolean("default");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(AddressItemType.ITEM_ADDRESS)
                    .setFiled(MultipleFields.ID,id)
                    .setFiled(MultipleFields.NAME,name)
                    .setFiled(MultipleFields.TAG,isDefault)
                    .setFiled(AddressItemFileds.PHONE,phone)
                    .setFiled(AddressItemFileds.ADDRESS,address)
                    .build();

            ENTITLES.add(entity);
        }
        return ENTITLES;
    }
}
