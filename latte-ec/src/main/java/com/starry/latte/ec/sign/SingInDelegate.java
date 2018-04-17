package com.starry.latte.ec.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;

import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.ec.R;
import com.starry.latte.ec.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wangsen on 2018/4/17.
 */

public class SingInDelegate extends LatteDelegate {



    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;

    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChat(){

    }
    @OnClick(R2.id.tv_link_sign_up)
    void onClickLink(){
        start(new SignUpDelegate());
    }




    private boolean checkForm(){
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();


        boolean isPass = true;

        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        }else {
            mEmail.setError(null);
        }

        if(password.isEmpty() || password.length() < 6){
            mPassword.setError("密码格式错误");
            isPass = false;
        }else {
            mPassword.setError(null);
        }

        return isPass;

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        super.onBindView(savedInstanceState, rootView);
    }
}
