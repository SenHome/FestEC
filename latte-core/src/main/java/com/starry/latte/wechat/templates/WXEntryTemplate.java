package com.starry.latte.wechat.templates;

import com.starry.latte.wechat.BaseWXEntryActivity;
import com.starry.latte.wechat.LatterWeChat;

/**
 * Created by wangsen on 2018/4/18.
 * 登陆返回后的Activity,隐藏，finish掉
 */

public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        //finish时候不需要有动画效果
        overridePendingTransition(0,0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        LatterWeChat.getInstance().getSignInCallback().onSignInSuccess(userInfo);
    }
}
