package com.starry.latte.ec.main.personal.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.ec.R;
import com.starry.latte.ec.R2;
import com.starry.latte.net.RestClient;
import com.starry.latte.net.callback.ISuccess;
import com.starry.latte.ui.recycler.MultipleItemEntity;
import com.starry.latte.util.log.LatteLogger;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangsen on 2018/4/27.
 */

public class AddressDelegate extends LatteDelegate implements ISuccess{

    @BindView(R2.id.rv_address)
    RecyclerView mRecyclerView = null;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        RestClient.builder()
                .url("address.php")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_address;
    }

    @Override
    public void onSucess(String response) {
        LatteLogger.d("AddressDelegate",response);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final List<MultipleItemEntity> data =
                new AddressDataConverter().setJsonData(response).convert();
        final AddressAdapter adapter = new AddressAdapter(data);
        mRecyclerView.setAdapter(adapter);
    }
}
