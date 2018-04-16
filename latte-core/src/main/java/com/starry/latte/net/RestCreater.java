package com.starry.latte.net;

import com.starry.latte.app.ConfigKeys;
import com.starry.latte.app.Latte;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by wangsen on 2018/4/5.
 */

public final class RestCreater {

    //内部类，惰性加载
    private static final class ParamsHolder{
        private static final WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();

    }

    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }


    private static final class OkhttpHolder{
        private static final int TIME_OUT = 60;
        private static final ArrayList<Interceptor> INTERCEPTORS = Latte.getConfiguration(ConfigKeys.INTERCEPTOR);
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();

        private static OkHttpClient.Builder addInterceptor(){
            if(INTERCEPTORS != null && !INTERCEPTORS.isEmpty()){
                for (Interceptor interceptor:INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }
        private static final OkHttpClient OK_HTTP_CLIENT =addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    //创建Retrofit
    private static final class RetrofitHolder{
        private static final String BASE_URL = Latte.getConfiguration(ConfigKeys.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkhttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }






   //建立RestService
    private static final class RestServiceHolder{
        private static final RestService RETROFIT_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
   }

    //get方法
    public static RestService getRestService(){
        return RestServiceHolder.RETROFIT_SERVICE;
    }



}
