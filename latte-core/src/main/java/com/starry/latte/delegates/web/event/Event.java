package com.starry.latte.delegates.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.delegates.web.WebDelegate;

/**
 * Created by wangsen on 2018/4/24.
 * 抽象方法，抽项每一个具体的事件
 */

public abstract class Event implements IEvent  {

    private Context mContext = null;
    private String mAction = null;
    private WebDelegate mDelegate = null;
    private String mUrl = null;
    private WebView mWebView = null;

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        mAction = action;
    }

    public WebDelegate getDelegate() {
        return mDelegate;
    }

    public void setDelegate(WebDelegate delegate) {
        mDelegate = delegate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public WebView getWebView() {
        return mWebView;
    }

    public void setWebView(WebView webView) {
        mWebView = webView;
    }
}
