package com.starry.latte.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by wangsen on 2018/4/20.
 */

public class MultipleViewHolder extends BaseViewHolder {

    private MultipleViewHolder(View view) {
        super(view);
    }
    public static MultipleViewHolder create(View view){
        return new MultipleViewHolder(view);

    }
}
