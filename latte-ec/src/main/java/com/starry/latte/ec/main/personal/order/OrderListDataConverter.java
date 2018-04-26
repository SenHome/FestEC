package com.starry.latte.ec.main.personal.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.starry.latte.ui.recycler.DataConverter;
import com.starry.latte.ui.recycler.MultipleFields;
import com.starry.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by wangsen on 2018/4/26.
 */

public class OrderListDataConverter extends DataConverter {


    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = array.getJSONObject(i);
            final String thumb = data.getString("thumb");
            final String title = data.getString("title");
            final String time = data.getString("time");
            final int id = data.getInteger("id");
            final double price = data.getDouble("price");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(OrderListItemType.ITEM_ORDER_LIST)
                    .setFiled(MultipleFields.ID,id)
                    .setFiled(MultipleFields.IMAGE_URL,thumb)
                    .setFiled(MultipleFields.TITLE,title)
                    .setFiled(OrderItemFileds.PRICE,price)
                    .setFiled(OrderItemFileds.TIME,time)
                    .build();

            ENTITLES.add(entity);
        }
        return ENTITLES;
    }
}
