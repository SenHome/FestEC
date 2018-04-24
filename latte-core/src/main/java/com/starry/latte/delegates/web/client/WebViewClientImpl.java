package com.starry.latte.delegates.web.client;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewDatabase;

import com.starry.latte.app.Latte;
import com.starry.latte.delegates.IPageLoadListener;
import com.starry.latte.delegates.web.WebDelegate;
import com.starry.latte.delegates.web.route.Router;
import com.starry.latte.ui.loader.LatteLoader;
import com.starry.latte.util.log.LatteLogger;

import java.util.logging.Handler;

/**
 * Created by wangsen on 2018/4/23.
 * 实现WebViewClient的类
 */

public class WebViewClientImpl extends WebViewClient {

    //传入Delegate
    private final WebDelegate DELEGATE;

    //监听web回调
    private IPageLoadListener mIPageLoadListener = null;
    //加入延迟，抗得清楚
    private static final android.os.Handler HANDLER = Latte.getHandler();

    public void setPageLoadListener(IPageLoadListener listener){
        this.mIPageLoadListener = listener;
    }

    public WebViewClientImpl(WebDelegate delegate) {
        this.DELEGATE = delegate;
    }



    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        LatteLogger.d("shouldOverrideUrlLoading",url);
        //进行路由的截断处理，原生接管
        return Router.getInstance().handleWebUrl(DELEGATE,url);
    }
    //加入延迟回调
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if(mIPageLoadListener != null){
            mIPageLoadListener.onLoadStrart();
        }
        LatteLoader.showLoading(view.getContext());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        if(mIPageLoadListener != null){
            mIPageLoadListener.onLoadEnd();
        }
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                LatteLoader.stopLoading();

            }
        },1000);
    }
}
