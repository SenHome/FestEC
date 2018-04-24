package com.starry.latte.delegates.web.event;

import com.starry.latte.util.log.LatteLogger;

/**
 * Created by wangsen on 2018/4/24.
 */

public class UndefindEvent extends Event {

    @Override
    public String excutes(String params) {
        LatteLogger.e("UndefindEvent",params);
        return null;
    }
}
