package com.starry.latte.ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by wangsen on 2018/4/4.
 * 自定义字体Modul返回的枚举类型
 */

public enum  EcIcons implements Icon {
    icon_scan('\ue633'),
    icon_ali_pay('\ue609');


    //返回类型，char
    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
