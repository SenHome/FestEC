package com.starry.latte.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.ec.R;
import com.starry.latte.ec.R2;
import com.starry.latte.net.RestClient;
import com.starry.latte.net.callback.ISuccess;

import butterknife.BindView;

/**
 * Created by wangsen on 2018/4/29.
 */

public class AboutDelegate extends LatteDelegate {

    @BindView(R2.id.tv_info)
    AppCompatTextView mAppCompatTextView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_about;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        RestClient.builder()
                .url("about.php")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSucess(String response) {
                        final String info = JSON.parseObject(response).getString("data");
                        mAppCompatTextView.setText(info);
                    }
                })
                .build()
                .get();
    }


}
