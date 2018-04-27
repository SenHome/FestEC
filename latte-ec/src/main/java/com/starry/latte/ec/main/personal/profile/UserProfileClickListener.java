package com.starry.latte.ec.main.personal.profile;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.starry.latte.delegates.LatteDelegate;
import com.starry.latte.ec.R;
import com.starry.latte.ec.main.personal.list.ListBean;
import com.starry.latte.net.RestClient;
import com.starry.latte.net.callback.ISuccess;
import com.starry.latte.ui.data.DialogUtils;
import com.starry.latte.ui.loader.LatteLoader;
import com.starry.latte.util.callback.CallBackManager;
import com.starry.latte.util.callback.CallbackType;
import com.starry.latte.util.callback.IGlobalCallback;
import com.starry.latte.util.log.LatteLogger;

/**
 * Created by wangsen on 2018/4/26.
 */

public class UserProfileClickListener extends SimpleClickListener {

    //有跳转，传入一些东西
    private final LatteDelegate DELEGATE;

    private String[] mGenders = new String[]{"男", "女", "保密"};

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
                CallBackManager.getInstance()
                        .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                            @Override
                            public void executeCallback(Uri args) {
                                LatteLogger.d("ON_CROP", args);
                                //头像图片设置
                                final ImageView avatar = view.findViewById(R.id.img_arrow_avatar);
                                Glide.with(DELEGATE)
                                        .load(args)
                                        .into(avatar);
                                //上传头像
                                RestClient.builder()
                                        .url(UpLoadConfig.UPLOAD_IMG)
                                        .loader(DELEGATE.getContext())
                                        .file(args.getPath())
                                        .success(new ISuccess() {
                                            @Override
                                            public void onSucess(String response) {
                                                //上传成功返回信息
                                                LatteLogger.d("ON_CROP_UPLOAD", response);

                                                final String path = JSON.parseObject(response).getJSONObject("result")
                                                        .getString("path");
                                                //通知服务器更新信息，姓名，性别，逻辑一样，
                                                RestClient.builder()
                                                        .url("user_profile.php")
                                                        .params("avatar",path)
                                                        .loader(DELEGATE.getContext())
                                                        .success(new ISuccess() {
                                                            @Override
                                                            public void onSucess(String response) {
                                                                //获取更新后的用户信息，然后更新本地数据库
                                                                //还有的方式，没有本地数据库的app,每次打开APP都会请求API，获取信息

                                                            }
                                                        })
                                                        .build()
                                                        .post();

                                            }
                                        })
                                        .build()
                                        .upload();

                            }
                        });
                DELEGATE.startCameraWithCheck();

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
    private void getGenderDialog(DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(DELEGATE.getContext());
        builder.setSingleChoiceItems(mGenders, 0, listener);
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
