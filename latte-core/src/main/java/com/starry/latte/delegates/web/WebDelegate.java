package com.starry.latte.delegates.web;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.starry.latte.app.ConfigKeys;
import com.starry.latte.app.Latte;
import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.delegates.web.route.RouteKeys;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by wangsen on 2018/4/23.
 * 抽象类
 */

public abstract class WebDelegate extends LatteDelegate implements IWebViewinitializer{

    private WebView mWebView = null;
    //弱引用引用webView
    private final ReferenceQueue<WebView> WEB_VIEW_QUENE = new ReferenceQueue<>();
    private String mUrl = null;
    //判断WebView是否可以使用
    private boolean mIsWebViewAvailable = false;

    private LatteDelegate mTopDelegate = null;

    public WebDelegate(){
    }

    //必须进行初始化
    public abstract IWebViewinitializer setInitializer();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mUrl = args.getString(RouteKeys.URL.name());

        initWebView();
    }

    //初始化WebView
    @SuppressLint("JavascriptInterface")
    private void initWebView(){
        if(mWebView != null){
            mWebView.removeAllViews();
            mWebView.destroy();
        }else {
            final IWebViewinitializer initializer = setInitializer();
            if(initializer != null){
                //WebView 写在xml文件容易内存泄露，用代码new出来，加到队列里
                final WeakReference<WebView> webViewWeakReference =
                        new WeakReference<>(new WebView(getContext()),WEB_VIEW_QUENE);
                mWebView = webViewWeakReference.get();
                //初始化WebView,WebViewClient,WebChromeClient
                mWebView = initializer.initWebView(mWebView);
                mWebView.setWebViewClient(initializer.initWebViewClient());
                mWebView.setWebChromeClient(initializer.initWebChromeClient());

                final String name = Latte.getConfiguration(ConfigKeys.JAVASCRIPE_INTERFACE);

                //WebView与原生进行交互，需要出入WebViewIterface
                mWebView.addJavascriptInterface(LatteWebInterface.create(this),name);
                //WebView可以使用了，打个标记
                mIsWebViewAvailable = true;
            }else {
                throw new NullPointerException("Initializer is null");
            }
        }
    }

    public void setTopDelegate(LatteDelegate delegate){
        mTopDelegate = delegate;

    }

    public LatteDelegate getTopDelegate(){
        if(mTopDelegate == null){
            mTopDelegate = this;
        }
        return mTopDelegate;
    }

    //获取WebView
    public WebView getWebView(){
        if(mWebView == null){
            throw new NullPointerException("WEBVIEW IS NULL");
        }
        return mIsWebViewAvailable ? mWebView : null;
    }

    //获取Url方法
    public String getUrl(){
        if(mUrl == null){
            throw new NullPointerException("URL IS NULL");
        }
        return mUrl;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mWebView != null){
            mWebView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mWebView != null){
            mWebView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsWebViewAvailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mWebView != null){
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }
}
