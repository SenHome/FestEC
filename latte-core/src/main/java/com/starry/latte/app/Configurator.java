package com.starry.latte.app;

import android.app.AlertDialog;
import android.app.Dialog;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * Created by wangsen on 2018/4/3.
 * 初始化配置
 *
 */

public class Configurator {
    //存储配置信息信息数据结构
    private static final HashMap<String,Object> LATTE_CONFIGS= new HashMap<>();
    //定义一个存储空间,存储字体图标
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();


    private Configurator(){

        initIcons();
        //false，配置开始，未结束
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
    }

    //线程安全的单利模式
    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    //单利模式初始化，静态内部类
    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();

    }

    //配置信息
    final HashMap<String,Object> getLatteConfigs(){
        return LATTE_CONFIGS;
    }




    //builer模式，配置API_HOST
    public final Configurator withApiHost(String host){
     LATTE_CONFIGS.put(ConfigType.API_HOST.name(),host);
     return this;
    }

    //builder配置icon
    public final Configurator withIcon(IconFontDescriptor descriptor){
        //加入自己的字体图标
        ICONS.add(descriptor);
        return this;
    }

    //配置结束,相当于build,create
    public final void configure(){
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(),true);
    }

//=======================================================================
    private void initIcons(){
        if(ICONS.size() > 0){
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }



    //检查配置项目有没有完成，获取配置时候调用
    private void checkConfiguration(){
        //获取配置是否开始
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if(!isReady){
            throw new RuntimeException("Configuration is not ready ,call configure");
        }

    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigType> key){
        checkConfiguration();
        return (T) LATTE_CONFIGS.get(key.name());

        //注解：告诉编译器，这个类型没有检测过，但并不抛出警告，编译器忽略
    }

}
