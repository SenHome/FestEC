package com.starry.latte.ui.scanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.util.callback.CallBackManager;
import com.starry.latte.util.callback.CallbackType;
import com.starry.latte.util.callback.IGlobalCallback;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by wangsen on 2018/4/30.
 */

public class ScannerDelegate extends LatteDelegate implements ZBarScannerView.ResultHandler {

    private ScanView mScanView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mScanView == null){
            mScanView = new ScanView(getContext());
        }


        mScanView.setAutoFocus(true);
        mScanView.setResultHandler(this);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public Object setLayout() {
        return mScanView;
    }

    @Override
    public void handleResult(Result result) {
        @SuppressWarnings("unchecked")
        final IGlobalCallback<String> callback = CallBackManager
                .getInstance()
                .getCallback(CallbackType.ON_SCAN);
        if (callback != null) {
            callback.executeCallback(result.getContents());
        }
        getSupportDelegate().pop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mScanView!=null){
            mScanView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mScanView!=null){
            mScanView.stopCameraPreview();
            mScanView.stopCamera();
        }
    }
}
