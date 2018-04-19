package com.starry.festec.example.generators;

import com.starry.latte.wechat.templates.WXEntryTemplate;
import com.starry.latte_annotation.annotations.EntryGenerator;



@SuppressWarnings("unused")
@EntryGenerator(
        packageName = "com.starry.festec.example",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
