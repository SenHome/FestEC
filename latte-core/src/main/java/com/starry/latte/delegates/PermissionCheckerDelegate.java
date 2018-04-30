package com.starry.latte.delegates;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.starry.latte.ui.camera.CameraImageBean;
import com.starry.latte.ui.camera.LatteCamera;
import com.starry.latte.ui.camera.RequestCode;
import com.starry.latte.ui.scanner.ScannerDelegate;
import com.starry.latte.util.callback.CallBackManager;
import com.starry.latte.util.callback.CallbackType;
import com.starry.latte.util.callback.IGlobalCallback;
import com.yalantis.ucrop.UCrop;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


/**
 * Created by wangsen on 2018/4/5.
 * 中间层，权限检查
 */
@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegates {

    //不是直接调用方法，生成代码使用
    @NeedsPermission(Manifest.permission.CAMERA)
    void startCamera() {
        LatteCamera.start(this);
    }

    //这个是真正调用的方法
    public void startCameraWithCheck() {

        PermissionCheckerDelegatePermissionsDispatcher.startCameraWithPermissionCheck(this);
    }

    //扫描二维码
    @NeedsPermission(Manifest.permission.CAMERA)
    void starScan(BaseDelegates delegates){
        delegates.getSupportDelegate().startForResult(new ScannerDelegate(),RequestCode.SCAN);
    }

    public void startScanWithCheck(BaseDelegates delegate) {

        PermissionCheckerDelegatePermissionsDispatcher.starScanWithPermissionCheck(this,delegate);
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied() {
        Toast.makeText(getContext(), "不允许拍照", Toast.LENGTH_LONG).show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNever() {
        Toast.makeText(getContext(), "永久拒绝权限", Toast.LENGTH_LONG).show();
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void onCameraRationale(PermissionRequest request) {
        showRationaleDialog(request);
    }

    private void showRationaleDialog(final PermissionRequest request) {
        if (getContext() != null) {
            new AlertDialog.Builder(getContext())
                    .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            request.proceed();
                        }
                    })
                    .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            request.cancel();
                        }
                    })
                    .setCancelable(false)
                    .setMessage("权限管理")
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheckerDelegatePermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    //添加照相机回掉
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCode.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    //原始路径，剪裁过后的图片，这里传入两个一样的值，覆盖掉
                    UCrop.of(resultUri,resultUri)
                            .withMaxResultSize(400,400)
                            .start(getContext(),this);
                    break;
                case RequestCode.PICK_PHOTO:
                    if(data!=null){
                        final Uri pickPath = data.getData();
                        //从相册中剪裁图片，需要有个路径存放建材后的图片
                        final String pickCropPath = LatteCamera.createCropFile().getPath();
                        UCrop.of(pickPath,Uri.parse(pickCropPath))
                                .withMaxResultSize(400,400)
                                .start(getContext(),this);
                    }
                    break;
                case RequestCode.CROP_PHOTO:
                    //剪裁回掉，对上两部的剪裁图片进行处理
                    final Uri cropUri = UCrop.getOutput(data);
                    //拿到建材数据进行处理
                    @SuppressWarnings("unchecked") 
                    final IGlobalCallback<Uri> callback = CallBackManager
                            .getInstance()
                            .getCallback(CallbackType.ON_CROP);
                    if(callback!=null){
                        callback.executeCallback(cropUri);
                    }
                    break;
                case RequestCode.CROP_ERROR:
                    Toast.makeText(getContext(), "剪裁出错", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

    }


}
