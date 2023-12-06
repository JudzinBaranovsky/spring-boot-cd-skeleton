package org.bananalaba.springcdtemplate.web;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.service.MessageStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private static final String DIRECT_FLOW = "direct";
    private static final String LAZY_CACHE_FLOW = "lazyCache";
    private static final String EAGER_CACHE_FLOW = "eagerCache";

    @Qualifier("inMemoryMessageStore")
    @NonNull
    private final MessageStore inMemoryMessageStore;

    @Qualifier("lazyCachingMessageStore")
    @NonNull
    private final MessageStore lazyCachingMessageStore;

    @Qualifier("eagerCachingMessageStore")
    @NonNull
    private final MessageStore eagerCachingMessageStore;

    @PostMapping(path = "/{flow}/{key}", consumes = "text/plain")
    public void put(@PathVariable("flow") String flow, @PathVariable("key") String key, @RequestBody String value) {
        getStore(flow).put(key, value);
    }

    @GetMapping(path = "/{flow}/{key}", produces = "text/plain")
    public ResponseEntity<String> get(@PathVariable("flow") String flow, @PathVariable("key") String key) {
        var value = getStore(flow).get(key);
        return ResponseEntity.status(200).contentType(MediaType.TEXT_PLAIN).body(value);
    }

    private MessageStore getStore(String flow) {
        return switch (flow) {
            case DIRECT_FLOW -> inMemoryMessageStore;
            case LAZY_CACHE_FLOW -> lazyCachingMessageStore;
            case EAGER_CACHE_FLOW -> eagerCachingMessageStore;
            default -> throw new IllegalArgumentException("unsupported flow " + flow);
        };
    }

}
