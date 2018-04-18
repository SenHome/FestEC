package com.starry.latte.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.starry.latte.app.AccountManager;
import com.starry.latte.app.IUserChecker;
import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.ec.R;
import com.starry.latte.ec.R2;
import com.starry.latte.ui.launcher.ILauncherListener;
import com.starry.latte.ui.launcher.OnLauncherFinishTag;
import com.starry.latte.ui.launcher.ScrollLauncherTag;
import com.starry.latte.util.storage.LattePreference;
import com.starry.latte.util.timer.BaseTimerTask;
import com.starry.latte.util.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by wangsen on 2018/4/17.
 */

public class LauncherDelegate extends LatteDelegate implements ITimerListener {

    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer = null;

    private Timer mTimer = null;
    private int mCount = 5;

    private ILauncherListener mILauncherListener =null;

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView(){
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
            checkIsShowScroll();
        }
    }

    //初始化Timer
    private void initTimer(){
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        mTimer.schedule(task, 0, 1000);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof  ILauncherListener){
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
    }

    //判断是否展示滑动启动页
    private void checkIsShowScroll(){
        if(!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())){
            start(new LauncherScrollDelegate(),SINGLETASK);
        }else {
            //检查用户是否登陆啦
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if(mILauncherListener!= null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }

                @Override
                public void onNotSignIn() {
                    if(mILauncherListener!=null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }
                }
            });
        }
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mTvTimer!=null){
                        mTvTimer.setText(MessageFormat.format("跳过\n{0}秒",mCount));
                    mCount--;
                    if(mCount<0){
                        if(mTimer != null){
                            mTimer.cancel();
                            mTimer = null;

                            checkIsShowScroll();
                        }
                    }

                }
            }
        });
    }
}
