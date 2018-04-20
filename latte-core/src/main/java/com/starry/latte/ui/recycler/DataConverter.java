package com.starry.latte.ui.recycler;

import java.util.ArrayList;

/**
 * Created by wangsen on 2018/4/20.
 * 数据的转换处理
 */

public abstract class DataConverter {

    protected final ArrayList<MultipleItemEntity> ENTITLES = new ArrayList<>();
    private String mJsonData = null;

    //返回数组
    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConverter setJsonData(String json){
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData(){
        if(mJsonData == null || mJsonData.isEmpty()){
            throw new NullPointerException("DATA IS NULL");
        }
        return mJsonData;
    }

    public void clearData(){
        ENTITLES.clear();
    }


}
