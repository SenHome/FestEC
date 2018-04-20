package com.starry.latte.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.starry.latte.app.Latte;
import com.starry.latte.net.RestClient;
import com.starry.latte.net.callback.ISuccess;

/**
 * Created by wangsen on 2018/4/20.
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {


    private final SwipeRefreshLayout REFRESH_LAYOUT;


    //    构造方法监听事件
    public RefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }


    //开始加载
    private void refresh(){
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //可以进行网络请求
                REFRESH_LAYOUT.setRefreshing(false);
            }
        },2000);
    }

    public void firstPage(String url){
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSucess(String response) {
//                        Toast.makeText(Latte.getApplication(), response, Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .get();
    }
    @Override
    public void onRefresh() {
        refresh();
    }
}
