package com.starry.latte.ec.main.index;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.starry.latte.ec.R;
import com.starry.latte.ui.recycler.RgbValue;

/**
 * Created by wangsen on 2018/4/22.
 * 渐变式状态栏
 */

@SuppressWarnings("unused")
public class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar> {


    //关键代码变量
    //顶部距离
    private int mDistanceY = 0;
    //颜色变化速度
    private static final int SHOW_SPEED = 3;
    //定义传进来颜色，需要Bean存储颜色
    private final RgbValue RGB_VALUE = RgbValue.create(255,124,2);


    //Behavior实例化构造方法，必须两个参数
    public TranslucentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        return dependency.getId() == R.id.rv_index;
        //设定布局文件，在layout里设置app:layout_behavior
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    //关键代码

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull Toolbar child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);

        //增加滑动距离
        mDistanceY +=dy;
        //toolbar的高度
        final int targetHeight  = child.getBottom();

        //当滑动时后，并且距离小于toolbar高度的时候，调整渐变色
        if(mDistanceY > 0 && mDistanceY <= targetHeight){
            final float scale = (float) mDistanceY/targetHeight;
            final float alpha = scale * 255;
            child.setBackgroundColor(Color.argb((int)alpha,RGB_VALUE.red(),RGB_VALUE.green(),RGB_VALUE.blue()));
        }else if(mDistanceY > targetHeight){
            //防止滑动过快，颜色来不及变化
            child.setBackgroundColor(Color.rgb(RGB_VALUE.red(),RGB_VALUE.green(),RGB_VALUE.blue()));

        }

    }
}
