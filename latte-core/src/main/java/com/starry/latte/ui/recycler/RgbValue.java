package com.starry.latte.ui.recycler;

import com.google.auto.value.AutoValue;

/**
 * Created by wangsen on 2018/4/22.
 * 定义成后想了，用到Google的auto
 */
@AutoValue
public abstract class RgbValue {

    public abstract int red();
    public abstract int green();
    public abstract int blue();

    //方便使用写一个方法
    public static RgbValue create(int red,int green,int blue){
        return new AutoValue_RgbValue(red, green, blue);
    }
}
