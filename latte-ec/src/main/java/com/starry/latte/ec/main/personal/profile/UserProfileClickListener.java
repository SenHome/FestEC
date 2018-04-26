package com.starry.latte.ec.main.personal.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.ec.R;
import com.starry.latte.ec.main.personal.list.ListBean;
import com.starry.latte.ui.data.DialogUtils;

/**
 * Created by wangsen on 2018/4/26.
 */

public class UserProfileClickListener extends SimpleClickListener {

    //有跳转，传入一些东西
    private final LatteDelegate DELEGATE;

    private String[] mGenders = new String[]{"男","女","保密"};

    public UserProfileClickListener(LatteDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, final View view, int position) {
        //取到数据
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        //根据id判断点击那个
        final int id = bean.getId();
        switch (id) {
            case 1:
                //开始照相机
                break;
            case 2:
                final LatteDelegate nameDelegate = bean.getDelegate();
                DELEGATE.getSupportDelegate().start(nameDelegate);
                break;
            case 3:
                getGenderDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(mGenders[which]);
                        dialog.cancel();
                    }
                });
                break;
            case 4:
                final DialogUtils dialogUtils = new DialogUtils();
                dialogUtils.setDataListener(new DialogUtils.IDataListener() {
                    @Override
                    public void onDataChange(String data) {
                        final TextView textView = view.findViewById(R.id.tv_arrow_value);
                        textView.setText(data);
                    }
                });
                dialogUtils.showDialog(DELEGATE.getContext());
                break;
            default:
                break;

        }
    }

    //呈现性别信息
    private void getGenderDialog(DialogInterface.OnClickListener listener){
        final AlertDialog.Builder builder = new AlertDialog.Builder(DELEGATE.getContext());
        builder.setSingleChoiceItems(mGenders,0,listener);
        builder.show();
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
