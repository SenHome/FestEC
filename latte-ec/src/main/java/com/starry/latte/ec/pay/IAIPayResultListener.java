package com.starry.latte.ec.pay;

/**
 * Created by wangsen on 2018/4/25.
 * 几个支付回调
 */

public interface IAIPayResultListener {

    void onPaySuccess();
    void onPaying();
    void onPayFail();
    void onPayCancel();
    void onPayConnectError();
}
