package com.starry.latte.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.starry.latte.net.RestClient;
import com.starry.latte.net.callback.IError;
import com.starry.latte.net.callback.IFailure;
import com.starry.latte.net.callback.ISuccess;
import com.starry.latte.ui.loader.LatteLoader;
import com.starry.latte.util.log.LatteLogger;
import com.starry.latte.wechat.BaseWXActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

/**
 * Created by wangsen on 2018/4/19.
 */

public abstract class BaseWXEntryActivity extends BaseWXActivity {

    //用户登陆成功后的回调
    protected abstract void onSignInSuccess(String userInfo);


    //微信发送请求到第三方应用后的回调
    @Override
    public void onReq(BaseReq baseReq) {

    }


    //第三方应用发送请求到微信的回调
    @Override
    public void onResp(BaseResp baseResp) {
        //获取微信Url
        final String code = ((SendAuth.Resp)baseResp).code;
        final StringBuilder authUrl = new StringBuilder();
        authUrl
                .append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(LatterWeChat.APP_ID)
                .append("&secret=")
                .append(LatterWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        LatteLogger.d("authUrl",authUrl.toString());


        getAuth(authUrl.toString());
    }

    private void getAuth(final String authUrl){
        //get请求
        RestClient.builder()
                .url(authUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSucess(String response) {
                        //拿到openId,和Token
                        final JSONObject authObj = JSON.parseObject(response);
                        final String accessToken = authObj.getString("access_token");
                        final String openId = authObj.getString("openid");

                        //获取名字，头像，地名等信息
                        final StringBuilder userInfoUrl = new StringBuilder();
                        userInfoUrl
                                .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessToken)
                                .append("&openid=")
                                .append(openId)
                                .append("&lang=")
                                .append("zh_CN");

                        LatteLogger.d("userInfoUrl",userInfoUrl.toString());
                        getUserInfoUrl(userInfoUrl.toString());
                    }
                })
                .build()
                .get();
    }

    //获取用户真正信息
    private void getUserInfoUrl(String userInfoUrl){
        RestClient
                .builder()
                .url(userInfoUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSucess(String response) {
                        //把之前写的抽象方法放进去
                        onSignInSuccess(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFaliure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onEroor(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}
