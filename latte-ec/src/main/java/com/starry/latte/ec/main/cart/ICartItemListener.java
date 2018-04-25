package com.starry.latte.ec.main.cart;

/**
 * Created by wangsen on 2018/4/25.
 * 接口方式返回总价
 */

public interface ICartItemListener {
    void onItemClick(double itemTotalPrice);
}
