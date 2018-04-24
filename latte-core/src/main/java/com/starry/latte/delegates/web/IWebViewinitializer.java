package com.starry.latte.delegates.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by wangsen on 2018/4/23.
 * 初始化WebView,WebViewClient,WebChromeClient接口方法
 */

public interface IWebViewinitializer {

    WebView initWebView(WebView webView);
    WebViewClient initWebViewClient();
    WebChromeClient initWebChromeClient();
}
