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
import com.starry.latte.util.log.LatteLogger;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by wangsen on 2018/4/19.
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


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler = RefreshHandler.create(mRefreshLayout,mRecyclerView,new IndexDataConverter());
        //测试使用获取index_data数据
//        RestClient
//                .builder()
//                .url("http://192.168.0.127/RestServer/data/index_data.json")
//                .success(new ISuccess() {
//                    @Override
//                    public void onSucess(String response) {
//                        final IndexDataConverter converter = new IndexDataConverter();
//                        converter.setJsonData(response);
//                        String jsonData = converter.getJsonData();
//                        ArrayList<MultipleItemEntity> list = converter.convert();
//                        final String image = list.get(1).getField(MultipleFields.IMAGE_URL);
//                        Toast.makeText(getContext(), jsonData, Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .build()
//                .get();
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
        mRefreshHandler.firstPage("index_data.json");
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

}
