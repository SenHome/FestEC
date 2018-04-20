package com.starry.latte.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * Created by wangsen on 2018/4/20.
 */

public class MultipleItemEntity implements MultiItemEntity {

    //数据转换
    private final ReferenceQueue<LinkedHashMap<Object,Object>> ITEM_QUENE = new ReferenceQueue<>();
    private final LinkedHashMap<Object,Object> MULTIPLE_FILEDS = new LinkedHashMap<>();
    private final SoftReference<LinkedHashMap<Object,Object>> FILEDS_REFERENCE =
            new SoftReference<>(MULTIPLE_FILEDS,ITEM_QUENE);


    MultipleItemEntity(LinkedHashMap<Object,Object> fileds){
        FILEDS_REFERENCE.get().putAll(fileds);
    }


    //创建一个builder
    public static MultipleEntityBuilder builder(){
        return new MultipleEntityBuilder();
    }

    //获取数据，三个方法
    @Override
    public int getItemType() {
        return (int) FILEDS_REFERENCE.get().get(MultipleFields.ITEM_TYPE);
    }

    public final <T> T getFiled(Object key){
        return (T) FILEDS_REFERENCE.get().get(key);
    }

    public final LinkedHashMap<?,?> getFileds(){
        return FILEDS_REFERENCE.get();
    }

    //set方法插入数据
    public final MultiItemEntity setFiled(Object key,Object value){
        FILEDS_REFERENCE.get().put(key,value);
        return this;
    }
}
