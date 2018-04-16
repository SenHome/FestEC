package com.starry.festec.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.net.RestClient;
import com.starry.latte.net.callback.IError;
import com.starry.latte.net.callback.IFailure;
import com.starry.latte.net.callback.ISuccess;

/**
 * Created by wangsen on 2018/4/5.
 */

public class ExampleDelegate extends LatteDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        testRestClient();
    }

    private void testRestClient(){
        RestClient.builder()
                .url("http://127.0.0.1/index")
//                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSucess(String response) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    }   
                })
                .failure(new IFailure() {
                    @Override
                    public void onFaliure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onEroor(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}

