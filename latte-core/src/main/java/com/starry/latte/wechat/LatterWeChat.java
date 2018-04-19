package com.starry.latte.wechat;

import android.app.Activity;

import com.starry.latte.app.ConfigKeys;
import com.starry.latte.app.Latte;
import com.starry.latte.wechat.callbacks.IWeChatSignInCallback;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by wangsen on 2018/4/19.
 */

public class LatterWeChat {
    public static final String APP_ID = Latte.getConfiguration(ConfigKeys.WE_CHAT_APP_ID);
    public static final String APP_SECRET = Latte.getConfiguration(ConfigKeys.WE_CHAT_APP_SECRET);
    private final IWXAPI WXAPI;
    private IWeChatSignInCallback mSignInCallback = null;

    private static final class Holder{
        private static final LatterWeChat INSTANCE = new LatterWeChat();
    }

    public static LatterWeChat getInstance(){
        return Holder.INSTANCE;
    }

    private LatterWeChat(){
        final Activity activity = Latte.getConfiguration(ConfigKeys.ACTIVITY);
        WXAPI = WXAPIFactory.createWXAPI(activity,APP_ID,true);
        WXAPI.registerApp(APP_ID);
    }

    public final IWXAPI getWXAPI(){
        return WXAPI;
    }

    //给回调赋值
    public LatterWeChat onSignSuccess(IWeChatSignInCallback callback){
        this.mSignInCallback = callback;
        return this;
    }

    //获取回调方法
    public IWeChatSignInCallback getSignInCallback(){
        return mSignInCallback;
    }

    public final void signIn(){
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);
    }
}
