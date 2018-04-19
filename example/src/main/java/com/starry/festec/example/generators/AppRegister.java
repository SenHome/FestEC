package com.starry.festec.example.generators;

import com.starry.latte.wechat.templates.AppRegisterTemplate;
import com.starry.latte_annotation.annotations.AppRegisterGenerator;

@SuppressWarnings("unused")
@AppRegisterGenerator(
        packageName = "com.starry.festec.example",
        registerTemplate = AppRegisterTemplate.class
)
public interface AppRegister {
}
