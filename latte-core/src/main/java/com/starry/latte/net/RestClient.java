package com.starry.latte.net;

import android.content.Context;

import com.starry.latte.net.callback.IError;
import com.starry.latte.net.callback.IFailure;
import com.starry.latte.net.callback.IRequest;
import com.starry.latte.net.callback.ISuccess;
import com.starry.latte.net.callback.RequestCallback;
import com.starry.latte.ui.LatteLoader;
import com.starry.latte.ui.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by wangsen on 2018/4/5.
 * 网络请求参数，url，参数，文件，加载圆圈，
 * 建造者模式，建造者和宿主类分开
 * 请求开始结束回掉
 * 成功，失败，错误回调
 *
 */

public class RestClient {

    private final String URL;
    //参数
    private static final WeakHashMap<String,Object> PARAMS = RestCreater.getParams();

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    //请求体
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;

    private final File FILE;

    private final Context CONTEXT;



    //构造方法赋值


    public RestClient(String url,
                      Map<String, Object> params,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      LoaderStyle style,
                      File file,
                      Context context) {
        this.URL = url;
        PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.LOADER_STYLE = style;
        this.FILE = file;
        this.CONTEXT = context;
    }

    //创建构造这
    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }


    private void request(HttpMethod method){
        final RestService service =RestCreater.getRestService();
        Call<String> call = null;

        if(REQUEST != null){
            REQUEST.onRequestStart();
        }

        //开始调用loader
        if(LOADER_STYLE != null){
            LatteLoader.showLoading(CONTEXT,LOADER_STYLE);
        }

        switch (method){
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.get(URL,PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL,BODY);
                break;
            case PUT:
                call = service.get(URL,PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL,BODY);
                break;
            case DELETE:
                call = service.get(URL,PARAMS);

                break;
            case UPLODE:
                //retrofit一些方法
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file ",FILE.getName(),requestBody);
                call = RestCreater.getRestService().upload(URL,body);
                break;
            default:
                break;
        }

        if(call != null){
            //传入callBack
            call.enqueue(getRequestCallback());
        }
    }

    //获取callback
    private Callback<String> getRequestCallback(){
        return new RequestCallback(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR,
                LOADER_STYLE
        );
    }

    //具体使用方法
    public final void get(){
        request(HttpMethod.GET);
    }

    public final void post(){

        if(BODY == null){

            request(HttpMethod.POST);
        }else{
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put()
    {

        if(BODY == null){

            request(HttpMethod.PUT);
        }else{
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.PUT_RAW);
        }
        request(HttpMethod.PUT_RAW);
    }

    public final void delete(){
        request(HttpMethod.DELETE);
    }
}
