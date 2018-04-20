package com.starry.latte.ec.main.index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.starry.latte.ui.recycler.DataConverter;
import com.starry.latte.ui.recycler.ItemType;
import com.starry.latte.ui.recycler.MultipleFields;
import com.starry.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by wangsen on 2018/4/20.
 * index数据转换类
 */

public class IndexDataConverter extends DataConverter{

    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);

            final String imageUrl = data.getString("imageUrl");
            final String text = data.getString("text");
            final int spanSize = data.getInteger("spanSize");
            final int id = data.getInteger("goodsId");
            final JSONArray banners = data.getJSONArray("banners");

            final ArrayList<String> bannerImages = new ArrayList<>();
            int type = 0;
            if(imageUrl == null && text!= null){
                type = ItemType.TEXT;
            }else if(imageUrl != null && text == null){
                type = ItemType.IMAGE;
            }else if(imageUrl != null){
                type = ItemType.TEXT_IMAGE;
            }else if(banners !=null){
                type = ItemType.BANNER;

                //Banner初始化
                final int bannerSize = banners.size();
                for (int j = 0; j < bannerSize; j++) {
                    final String banner = banners.getString(j);
                    bannerImages.add(banner);

                }
            }

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setFiled(MultipleFields.ITEM_TYPE,type)
                    .setFiled(MultipleFields.SPAN_SIZE,spanSize)
                    .setFiled(MultipleFields.ID,id)
                    .setFiled(MultipleFields.TEXT,text)
                    .setFiled(MultipleFields.IMAGE_URL,imageUrl)
                    .setFiled(MultipleFields.BANNERS,bannerImages)
                    .build();

            ENTITLES.add(entity);


        }
        return ENTITLES;
    }




}
