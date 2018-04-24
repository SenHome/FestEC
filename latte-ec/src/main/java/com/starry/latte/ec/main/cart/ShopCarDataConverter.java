package com.starry.latte.ec.main.cart;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.starry.latte.ui.recycler.DataConverter;
import com.starry.latte.ui.recycler.ItemType;
import com.starry.latte.ui.recycler.MultipleFields;
import com.starry.latte.ui.recycler.MultipleItemEntity;


import java.util.ArrayList;

/**
 * Created by wangsen on 2018/4/24.
 */

public class ShopCarDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            JSONObject data = dataArray.getJSONObject(i);
            final String thumb = data.getString("thumb");
            final String desc = data.getString("desc");
            final String title = data.getString("title");
            final int id = data.getInteger("id");
            final int count = data.getInteger("count");
            final double price = data.getDouble("price");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setFiled(MultipleFields.ITEM_TYPE,ShopCarItemType.SHOP_CART_ITEM)
                    .setFiled(MultipleFields.ID,id)
                    .setFiled(MultipleFields.IMAGE_URL,thumb)
                    .setFiled(ShopCarItemFiles.COUNT,count)
                    .setFiled(ShopCarItemFiles.DESC,desc)
                    .setFiled(ShopCarItemFiles.PRICE,price)
                    .setFiled(ShopCarItemFiles.TITLE,title)
                    .setFiled(ShopCarItemFiles.IS_SELECTED,false)
                    .build();

            dataList.add(entity);
        }
        return dataList;
    }
}
