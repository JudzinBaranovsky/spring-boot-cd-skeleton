package org.bananalaba.springcdtemplate.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.bananalaba.springcdtemplate.internal.MessageCacheManager;
import org.bananalaba.springcdtemplate.service.InMemoryMessageStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public abstract class MessageApiEvictionTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private InMemoryMessageStore messageStore;
    @Autowired
    private MessageCacheManager cacheManager;

    @BeforeEach
    void reset() {
        messageStore.clear();
        cacheManager.cleanAll();
    }

    @Test
    public void shouldGetLatestValueAfterCacheExpiry() throws Exception {
        var post1 = post("/api/v1/messages/direct/some-key")
            .contentType("text/plain")
            .content("value");
        mvc.perform(post1).andReturn();

        mvc.perform(get("/api/v1/messages/lazyCache/some-key")).andReturn().getResponse();

        var post2 = post("/api/v1/messages/direct/some-key")
            .contentType("text/plain")
            .content("another-value");
        mvc.perform(post2).andReturn();

        Thread.sleep(1000);

        var actual = mvc.perform(get("/api/v1/messages/lazyCache/some-key")).andReturn().getResponse();

        assertThat(actual.getStatus()).isEqualTo(200);
        assertThat(actual.getContentType()).isEqualTo("text/plain");
        assertThat(actual.getContentAsString()).isEqualTo("another-value");
    }

}
