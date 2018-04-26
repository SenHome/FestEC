package com.starry.latte.delegates.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.starry.latte.app.Latte;
import com.starry.latte.delegates.IPageLoadListener;
import com.starry.latte.delegates.web.chromeclient.ChormeClientImpl;
import com.starry.latte.delegates.web.client.WebViewClientImpl;
import com.starry.latte.delegates.web.route.RouteKeys;
import com.starry.latte.delegates.web.route.Router;

/**
 * Created by wangsen on 2018/4/23.
 * 具体实现WebDelegate的类
 */

public class WebDelegateImpl extends WebDelegate {


    //监听web回调
    private IPageLoadListener mIPageLoadListener = null;




    //静态工厂方法，创建WebDelegateImpl
    public static WebDelegateImpl create(String url){
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(),url);
        final WebDelegateImpl delegate = new WebDelegateImpl();
        delegate.setArguments(args);
        return delegate;
    }


    //整个webView作为主布局
    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        if(getUrl() != null){
            //用原生方式模拟web跳转并，进行页面加载，使用WebViewClient进行拦截
            Router.getInstance().loadPage(this,getUrl());
        }
    }

    public void setPageLoadListener(IPageLoadListener listener){
        this.mIPageLoadListener = listener;
    }

    @Override
    public IWebViewinitializer setInitializer() {
        return this;
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientImpl client = new WebViewClientImpl(this);
        client.setPageLoadListener(mIPageLoadListener);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new ChormeClientImpl();
    }

  
}
