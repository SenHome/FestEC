package com.starry.festec.example.generators;

import com.starry.latte.wechat.templates.WPayXEntryTemplate;
import com.starry.latte_annotation.annotations.PayEntryGenerator;


@SuppressWarnings("unused")
@PayEntryGenerator(
        packageName = "com.starry.festec.example",
        payEntryTemplate = WPayXEntryTemplate.class
)
public interface WeChatPayEntry {
}
