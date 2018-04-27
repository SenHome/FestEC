package com.starry.latte.util.callback;

/**
 * Created by wangsen on 2018/4/27.
 * 全局回调接口
 * T传入参数,更安全的形式，转换不同的类型
 */

public interface IGlobalCallback<T> {

    void executeCallback(T args);
}
