package com.starry.latte.net;

import android.content.Context;

import com.starry.latte.net.callback.IError;
import com.starry.latte.net.callback.IFailure;
import com.starry.latte.net.callback.IRequest;
import com.starry.latte.net.callback.ISuccess;
import com.starry.latte.ui.LoaderStyle;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by wangsen on 2018/4/5.
 */

public class RestClientBuilder {

    private  String mUrl = null;
    //参数
    private static final Map<String,Object> PARAMS = RestCreater.getParams();

    private  IRequest mIRequest= null;
    private  ISuccess mISuccess= null;
    private  IFailure mIFailure= null;
    private IError mIError= null;
    //请求体
    private  RequestBody mRequestBody= null;

    private LoaderStyle mLoaderStyle= null;
    private Context mContext= null;

    //只允许同包的RestClient
    RestClientBuilder(){

    }

    public final RestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }
    //传入Map
    public final RestClientBuilder params(WeakHashMap<String,Object> params){
        PARAMS.putAll(params);
        return this;
    }

    //传入键值对
    public final RestClientBuilder params(String key, Object value){

        PARAMS.put(key,value);
        return this;
    }

    //传入原始数据
    public final RestClientBuilder raw(String raw){
        this.mRequestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }

    //回调的处理
    public final RestClientBuilder success(ISuccess iSuccess){
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder error(IError iError){
        this.mIError = iError;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure){
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder request(IRequest iRequest){
        this.mIRequest = iRequest;
        return this;
    }


    //loading
    public final RestClientBuilder loader(Context context,LoaderStyle style){
        this.mContext = context;
        this.mLoaderStyle = style;
        return this;
    }
    //使用默认指定loader
    public final RestClientBuilder loader(Context context){
        this.mContext = context;
        this.mLoaderStyle = LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }

    //buildRestClient
    public final RestClient build(){
        return new RestClient(
                mUrl,
                PARAMS,
                mIRequest,
                mISuccess,
                mIFailure,
                mIError,
                mRequestBody,
                mLoaderStyle,
                mContext
        );
    }


}
