package com.starry.latte.ec.main.sort.content;

/**
 * Created by wangsen on 2018/4/23.
 * Goods的Bean类
 */

public class SectionContentItemEntity {

    private int mGoodsId = 0;
    private String mGoodsname = null;
    private String mGoodsthumb = null;

    public int getGoodsId() {
        return mGoodsId;
    }

    public void setGoodsId(int goodsId) {
        mGoodsId = goodsId;
    }

    public String getGoodsname() {
        return mGoodsname;
    }

    public void setGoodsname(String goodsname) {
        mGoodsname = goodsname;
    }

    public String getGoodsthumb() {
        return mGoodsthumb;
    }

    public void setGoodsthumb(String goodsthumb) {
        mGoodsthumb = goodsthumb;
    }
}
