package com.starry.festec.example.event;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mob.MobSDK;
import com.starry.latte.delegates.web.event.Event;
import com.starry.latte.util.log.LatteLogger;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by wangsen on 2018/4/30.
 */

public class ShareEvent extends Event {
    @Override
    public String excutes(String params) {


        LatteLogger.json("ShareEvent", params);

        final JSONObject object = JSON.parseObject(params).getJSONObject("params");
        final String title = object.getString("title");
        final String url = object.getString("url");
        final String imageUrl = object.getString("imageUrl");
        final String text = object.getString("text");

//        ShareSDK.initSDK(getContext());
        MobSDK.init(getContext(), "25854635e7cd8", "62bb1f2b61020d9b6f77ef33bcad20b7");
        final OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(title);
        oks.setText(text);
        oks.setImageUrl(imageUrl);
        oks.setUrl(url);
        oks.show(getContext());
        return null;
    }
}
