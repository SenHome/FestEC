package com.starry.latte.delegates.web.chromeclient;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by wangsen on 2018/4/23.
 * WebView内部处理控制,传到WebDelegateImpl中
 */

public class ChormeClientImpl extends WebChromeClient {

    //可以拦截alog,进行自己弹框处理
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }
}
