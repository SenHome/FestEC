package com.starry.latte.ec.main.index.search;

import com.alibaba.fastjson.JSONArray;
import com.starry.latte.ui.recycler.DataConverter;
import com.starry.latte.ui.recycler.MultipleFields;
import com.starry.latte.ui.recycler.MultipleItemEntity;
import com.starry.latte.util.storage.LattePreference;

import java.util.ArrayList;

/**
 * Created by wangsen on 2018/4/30.
 */

public class SearchDataConvertr  extends DataConverter{

    public static final String TAG_SEARCH_HISTORY = "search_history";

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final String jsonStr =
                LattePreference.getCustomAppProfile(TAG_SEARCH_HISTORY);
        if (!jsonStr.equals("")) {
            final JSONArray array = JSONArray.parseArray(jsonStr);
            final int size = array.size();
            for (int i = 0; i < size; i++) {
                final String historyItemText = array.getString(i);
                final MultipleItemEntity entity = MultipleItemEntity.builder()
                        .setItemType(SearchItemType.ITEM_SEARCH)
                        .setFiled(MultipleFields.TEXT, historyItemText)
                        .build();
                ENTITLES.add(entity);
            }
        }
        return ENTITLES;
    }
}
