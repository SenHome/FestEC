package com.starry.latte.ui.camera;

import android.net.Uri;

import com.starry.latte.delegates.PermissionCheckerDelegate;
import com.starry.latte.util.file.FileUtil;

/**
 * Created by wangsen on 2018/4/27.
 * 照相机调用类
 */

public class LatteCamera {

    //剪裁的文件地址
    public static Uri createCropFile(){
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate){
        new CameraHandler(delegate).beginCameraDialog();
    }
}
