package com.starry.latte.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.ContentFrameLayout;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.starry.latte.R;
import com.starry.latte.util.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by wangsen on 2018/4/6.
 */

public class LatteLoader {

    //设置loader的缩放比例
    private static final int LOADER_SIZE_SCALE = 8;

    //偏移量
    private static final int LOADER_OFFSET_SCALE = 10;

    //集合存储所有的loader,方便管理
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    //默认的style名字
    private static final String DEFAULT_LOADER = LoaderStyle.BallSpinFadeLoaderIndicator.name();

    //传入美剧类型
    public static void showLoading(Context cnotext,Enum<LoaderStyle> type){
        showLoading(cnotext,type.name());
    }
    public static void showLoading(Context context, String type){

        //需要一个dialog承载动画，弹出来
        final AppCompatDialog dialog = new AppCompatDialog(context,R.style.dialog);
        //建立style文件，达到灰色，透明效果
        final AVLoadingIndicatorView avLoadingIndicatorView = LoaderCreater.create(type,context);
        dialog.setContentView(avLoadingIndicatorView);


        //控制dialog宽高
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();
        if(dialogWindow != null){
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth/LOADER_SIZE_SCALE;
            lp.height = deviceHeight/LOADER_SIZE_SCALE;

            //偏移量
            lp.height = lp.height + deviceHeight/LOADER_OFFSET_SCALE;
            //剧中
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();


    }

    //默认Loading样式
    public static void showLoading(Context context){
        showLoading(context,DEFAULT_LOADER);
    }

    public static void stopLoading(){
        for (AppCompatDialog dialog:LOADERS) {
            if(dialog != null){
                if(dialog.isShowing()){
                    //关闭后执行回掉
                    dialog.cancel();
                    //直接关闭
//                    dialog.dismiss();
                }
            }
        }
    }
}
