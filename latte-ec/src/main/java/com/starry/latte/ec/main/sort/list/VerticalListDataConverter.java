package com.starry.latte.ec.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.starry.latte.ui.recycler.DataConverter;
import com.starry.latte.ui.recycler.ItemType;
import com.starry.latte.ui.recycler.MultipleFields;
import com.starry.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by wangsen on 2018/4/22.
 */

public class VerticalListDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();

        final JSONArray dataArray = JSON
                .parseObject(getJsonData())
                .getJSONObject("data")
                .getJSONArray("list");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setFiled(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setFiled(MultipleFields.ID,id)
                    .setFiled(MultipleFields.TEXT,name)
                    .setFiled(MultipleFields.TAG,false)
                    .build();

            dataList.add(entity);
            //设置选中第一个
            dataList.get(0).setField(MultipleFields.TAG,true);

        }
        return dataList;
    }
}
