package com.starry.latte.wechat.templates;

import android.widget.Toast;

import com.starry.latte.activity.ProxyActivity;
import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.wechat.BaseWXPayEntryActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;

/**
 * Created by wangsen on 2018/4/18.
 * 在前台显示，finish掉
 */

public class WPayXEntryTemplate extends BaseWXPayEntryActivity {
    @Override
    protected void onPaySuccess() {
        Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onPayFail() {

        Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onPayCancel() {
        Toast.makeText(this, "支付取消", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0,0);

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }
}
