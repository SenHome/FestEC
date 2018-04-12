package com.starry.latte.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.starry.latte.app.Latte;

/**
 * Created by wangsen on 2018/4/6.
 */

public class DimenUtil {
    public static int getScreenWidth(){
        final Resources resources = Latte.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }
    public static int getScreenHeight(){
        final Resources resources = Latte.getApplication().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }
}
