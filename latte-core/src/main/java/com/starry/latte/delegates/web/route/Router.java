package com.starry.latte.delegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.delegates.web.WebDelegate;
import com.starry.latte.delegates.web.WebDelegateImpl;

/**
 * Created by wangsen on 2018/4/23.
 * 路由类
 */

public class Router {

    private Router(){

    }

    private static class Holder{
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance(){
        return Holder.INSTANCE;
    }

    //处理URl方法，如果js里有电话链接，包含tel协议
    public final boolean handleWebUrl(WebDelegate delegate,String url){
        //弱国是电话协议
        if(url.contains("tel:")){
            callPhone(delegate.getContext(),url);
            return true;
        }


        final LatteDelegate topDelegate = delegate.getTopDelegate();
        final WebDelegateImpl webDelegate = WebDelegateImpl.create(url);
        topDelegate.start(webDelegate);
//        //不是我们的电话，进行原生天赚，上层有没有delegate,就是嵌套，防止光内层跳转，外层不跳转
//        final LatteDelegate parentDelegate = delegate.getParentDelegate();
//        final WebDelegateImpl webDelegate = WebDelegateImpl.create(url);
//        if(parentDelegate == null){
//            //我们传进来的delegate跳转
//            delegate.start(webDelegate);
//        }else {
//            //父Delegate进行跳转
//            parentDelegate.start(webDelegate);
//        }
        return true;
    }

    //电话方法，直接拨打，选择，跳转到电话
    private void callPhone(Context context,String uri){
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(uri);
        intent.setData(data);
        context.startActivity(intent);
        ContextCompat.startActivity(context,intent,null);

    }

    //页面load的方法，web页面
    private void loadWebPage(WebView webView, String url){
        if(webView != null){
            webView.loadUrl(url);
        }else {
            throw new NullPointerException("webView is null");
        }
    }

    //本地页面加载
    private void loadLocalPage(WebView webView,String url){
        loadWebPage(webView,"file:///android_asset/" + url);

    }


    private void loadPage(WebView webView, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView, url);
        } else {
            loadLocalPage(webView, url);
        }
    }
    public final void loadPage(WebDelegate delegate, String url) {
        loadPage(delegate.getWebView(), url);
    }


}
