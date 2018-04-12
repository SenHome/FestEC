package com.starry.latte.net;

import com.starry.latte.app.ConfigType;
import com.starry.latte.app.Latte;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by wangsen on 2018/4/5.
 */

public class RestCreater {

    //内部类，惰性加载
    public static final class ParamsHolder{
        public static final WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();

    }

    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }

    //get方法
    public static RestService getRestService(){
        return RestServiceHolder.RETROFIT_SERVICE;
    }

    //创建Retrofit
    private static final class RetrofitHolder{
        private static final String BASE_URL = (String) Latte.getConfigurations().get(ConfigType.API_HOST.name());
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkhttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private static final class OkhttpHolder{
        private static final int TIME_OUT = 60;
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
   }

   //建立RestService
    private static final class RestServiceHolder{
        private static final RestService RETROFIT_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
   }


}
