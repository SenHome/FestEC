package com.starry.latte.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.starry.latte.delegates.bottom.BottomItemDelegate;
import com.starry.latte.ec.R;
import com.starry.latte.ec.R2;
import com.starry.latte.ec.main.EcBottomDelegate;
import com.starry.latte.net.RestClient;
import com.starry.latte.net.callback.ISuccess;
import com.starry.latte.ui.recycler.BaseDecoration;
import com.starry.latte.ui.recycler.MultipleFields;
import com.starry.latte.ui.recycler.MultipleItemEntity;
import com.starry.latte.ui.refresh.RefreshHandler;
import com.starry.latte.util.callback.CallBackManager;
import com.starry.latte.util.callback.CallbackType;
import com.starry.latte.util.callback.IGlobalCallback;
import com.starry.latte.util.log.LatteLogger;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wangsen on 2018/4/19.
 * 主页界面
 */

public class IndexDelegate extends BottomItemDelegate {

    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;
    @BindView(R2.id.icon_idex_scan)
    IconTextView mIconScan = null;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchView = null;


    private RefreshHandler mRefreshHandler = null;

    @OnClick(R2.id.icon_idex_scan)
    void onClickScanQrCode(){
        startScanWithCheck(this.getParentDelegate());
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler = RefreshHandler.create(mRefreshLayout,mRecyclerView,new IndexDataConverter());
        //二维码
        CallBackManager.getInstance()
                .addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
                    @Override
                    public void executeCallback(@Nullable String args) {
                        Toast.makeText(getContext(), "得到的二维码是" + args, Toast.LENGTH_LONG).show();

                    }
                });
    }

    private void initRefreshLayout(){
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setProgressViewOffset(true,120,300);
    }


    private void initRecyclerView(){
        final GridLayoutManager manager = new GridLayoutManager(getContext(),4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(getContext(),R.color.app_background),5));

        //点击Item时候，fragent和BottomBar一块都要隐藏
        //得到父级元素，容器Delegate(fragment)
        final EcBottomDelegate ecBottomDelegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage("index.php");
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

}
