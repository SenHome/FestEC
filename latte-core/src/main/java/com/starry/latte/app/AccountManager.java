package com.starry.latte.app;

import com.starry.latte.util.storage.LattePreference;

/**
 * Created by wangsen on 2018/4/18.
 */

public class AccountManager {

    private enum SignTag{
        SIGN_TAG
    }

    //保存用户登录状态，登陆后调用
    public static void setSignState(boolean state){
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(),state);
    }

    private static boolean isSignIn(){
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker){
        if(isSignIn()){
            checker.onSignIn();
        }else {
            checker.onNotSignIn();
        }
    }
}
