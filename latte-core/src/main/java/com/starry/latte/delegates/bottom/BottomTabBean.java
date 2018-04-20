package com.starry.latte.delegates.bottom;

/**
 * Created by wangsen on 2018/4/19.
 */

public final class BottomTabBean {
    private final CharSequence ICON;
    private final CharSequence TITLE;

    public BottomTabBean(CharSequence icon, CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
    }

    public CharSequence getIcon(){
        return ICON;
    }

    public CharSequence getTitle(){
        return TITLE;
    }
}
