package com.starry.latte.ec.sign;

import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.starry.latte.app.AccountManager;
import com.starry.latte.ec.database.DatabaseManager;
import com.starry.latte.ec.database.UserProfile;


/**
 * Created by wangsen on 2018/4/18.
 */

public class SignHandler {

    public static void onSignIn(String response,ISignListener signListener){

        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");


        final UserProfile profile = new UserProfile(userId,name,avatar,gender,address);
        DatabaseManager.getInstance().getDao().update(profile);

        //已经注册成功了保存用户状态
        AccountManager.setSignState(true);
        signListener.onSignInSuccess();
    }

    public static void onSignUp(String response,ISignListener signListener){

        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");


        final UserProfile profile = new UserProfile(userId,name,avatar,gender,address);
        DatabaseManager.getInstance().getDao().insert(profile);

        //已经注册成功了保存用户状态
        AccountManager.setSignState(true);
        signListener.onSignUpSucess();
    }
}
