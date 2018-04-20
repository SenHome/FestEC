package com.starry.latte.net.callback;

import android.os.Handler;

import com.starry.latte.app.ConfigKeys;
import com.starry.latte.app.Latte;
import com.starry.latte.net.RestCreater;
import com.starry.latte.ui.loader.LatteLoader;
import com.starry.latte.ui.loader.LoaderStyle;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wangsen on 2018/4/6.
 */

public class RequestCallback implements Callback<String>{

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;


    //添加延时，看的更清楚
    private static final Handler HANDLER = Latte.getHandler();


    public RequestCallback(
            IRequest request,
            ISuccess success,
            IFailure failure,
            IError error,
            LoaderStyle loader_style) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = loader_style;
    }


    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if(response.isSuccessful()){
            if(call.isExecuted()){
                if(SUCCESS != null){
                    SUCCESS.onSucess(response.body());
                }
            }
        }else {
            if(ERROR!=null){
                ERROR.onEroor(response.code(),response.message());
            }
        }

        //停止Loading
        stopLoading();

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if(FAILURE != null){
            FAILURE.onFaliure();
        }

        if(REQUEST!= null){
            REQUEST.onRequestEnd();
        }
        //停止
        stopLoading();
//        onRequestFinish();
    }

    private void onRequestFinish() {
        final long delayed = Latte.getConfiguration(ConfigKeys.LOADER_DELAYED);
        if (LOADER_STYLE != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestCreater.getParams().clear();
                    LatteLoader.stopLoading();
                }
            }, delayed);
        }
    }

    private void stopLoading(){
        if(LOADER_STYLE != null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LatteLoader.stopLoading();
                }
            },1000);
        }
    }
}
