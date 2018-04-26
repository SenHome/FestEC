package com.starry.latte.ui.launcher;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * Created by wangsen on 2018/4/17.
 */

public class LauncherHolder implements Holder<Integer> {

    private AppCompatImageView mImageView = null;


    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);

        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        //每次滑动更新的东西
        mImageView.setBackgroundResource(data);
    }
}
