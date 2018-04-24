package com.starry.festec.example.event;

import android.webkit.WebView;
import android.widget.Toast;

import com.starry.latte.delegates.web.event.Event;

/**
 * Created by wangsen on 2018/4/24.
 */

public class TestEvent extends Event {
    @Override
    public String excutes(String params) {
        Toast.makeText(getContext(), getAction(), Toast.LENGTH_SHORT).show();

        if(getAction().equals("test")){
            final WebView webView = getWebView();
            webView.post(new Runnable() {
                @Override
                public void run() {
                    webView.evaluateJavascript("nativeCall();",null);
                }
            });
        }
        return null;
    }
}
