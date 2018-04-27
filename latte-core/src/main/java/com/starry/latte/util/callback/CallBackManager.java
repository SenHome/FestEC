package com.starry.latte.util.callback;

import java.util.WeakHashMap;

/**
 * Created by wangsen on 2018/4/27.
 * 全局Callback处理工具
 */

public class CallBackManager {
    //存储Callback
    private static final WeakHashMap<Object,IGlobalCallback> CALLBACKS = new WeakHashMap<>();

    private static class Holder{
        private static final CallBackManager INSTANCE = new CallBackManager();
    }

    public static CallBackManager getInstance(){
        return Holder.INSTANCE;
    }

    public CallBackManager addCallback(Object tag,IGlobalCallback callback){
        CALLBACKS.put(tag,callback);
        return this;
    }

    public IGlobalCallback getCallback(Object tag){
        return CALLBACKS.get(tag);
    }
}
