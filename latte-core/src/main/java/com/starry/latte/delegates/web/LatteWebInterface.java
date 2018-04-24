package com.starry.latte.delegates.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.starry.latte.delegates.web.event.Event;
import com.starry.latte.delegates.web.event.EventManager;

import retrofit2.http.DELETE;

/**
 * Created by wangsen on 2018/4/23.
 * 和原生进行交互
 */

public class LatteWebInterface {

    private final WebDelegate DELEGATE;

    public LatteWebInterface(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    //简单工厂方法
    static LatteWebInterface create(WebDelegate delegate){
        return new LatteWebInterface(delegate);
    }

    //js返回的Json数据，Android4.4以后加入注解事件才能相应
    @SuppressWarnings("unused")
    @JavascriptInterface
    public String event(String params){
        final String action = JSON.parseObject(params).getString("action");
        //事件统一加入到事件组里面
        final Event event = EventManager.getInstance().createEvent(action);
        if(event!=null){
            event.setAction(action);
            event.setDelegate(DELEGATE);
            event.setContext(DELEGATE.getContext());
            event.setUrl(DELEGATE.getUrl());
            return event.excutes(params);
        }
        return null;
    }
}
