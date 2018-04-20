package com.starry.latte.ui.recycler;

import java.util.LinkedHashMap;

/**
 * Created by wangsen on 2018/4/20.
 * builder类
 */

public class MultipleEntityBuilder {

    private static final LinkedHashMap<Object,Object> FILEDS = new LinkedHashMap<>();

    public MultipleEntityBuilder(){
        FILEDS.clear();
    }

    public final MultipleEntityBuilder setItemType(int itemType){
        FILEDS.put(MultipleFields.ITEM_TYPE,itemType);
        return this;
    }


    public final MultipleEntityBuilder setFiled(Object key,Object value){
        FILEDS.put(key,value);
        return this;
    }

    public final MultipleEntityBuilder setFileds(LinkedHashMap<?,?> map){
        FILEDS.putAll(map);
        return this;

    }

    //==========================
    public final MultipleItemEntity build(){
       return new MultipleItemEntity(FILEDS);
    }


}
