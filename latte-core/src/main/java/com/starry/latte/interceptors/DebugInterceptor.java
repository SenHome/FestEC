package com.starry.latte.interceptors;

import android.support.annotation.RawRes;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by wangsen on 2018/4/10.
 */

public class DebugInterceptor extends BaseInterceptor {

   private final String DEBUG_URL;
   private final int DEBUT_RAW_ID;

    public DebugInterceptor(String base_url, int debut_raw_id) {
        this.DEBUG_URL = base_url;
        this.DEBUT_RAW_ID = debut_raw_id;
    }

    //获取json文件
    private Response getResponse(Chain chain,String json){
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type","application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"),json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    private Response DebugResponse(Chain chain,@RawRes int rawID){
//        final String json =
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
