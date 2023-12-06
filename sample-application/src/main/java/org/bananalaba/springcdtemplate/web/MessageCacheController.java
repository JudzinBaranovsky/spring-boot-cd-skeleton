package org.bananalaba.springcdtemplate.web;

import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.internal.MessageCacheManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/cache/messages")
@RequiredArgsConstructor
public class MessageCacheController {

    private final MessageCacheManager cacheManager;

    @PostMapping("/clear")
    public void clear() {
        cacheManager.cleanAll();
    }

}
