package com.starry.latte.delegates;

/**
 * Created by wangsen on 2018/4/5.
 * 以后正式使用的
 */

public abstract class LatteDelegate  extends PermissionCheckerDelegate{

    //fragment和Bottombar都隐藏掉功能添加的代码
    @SuppressWarnings("unused")
    public <T extends LatteDelegate> T getParentDelegate(){
        return (T) getParentFragment();
    }
}
