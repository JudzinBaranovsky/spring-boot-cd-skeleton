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
public abstract class MessageApiTest {

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
    public void shouldGetEmptyResult() throws Exception {
        var actual = mvc.perform(get("/api/v1/messages/direct/some-key")).andReturn().getResponse();

        assertThat(actual.getStatus()).isEqualTo(200);
        assertThat(actual.getContentType()).isEqualTo("text/plain");
        assertThat(actual.getContentAsString()).isEmpty();
    }

    @Test
    public void shouldGetValue() throws Exception {
        var post = post("/api/v1/messages/direct/some-key")
            .contentType("text/plain")
            .content("value");
        mvc.perform(post).andReturn();

        var actual = mvc.perform(get("/api/v1/messages/direct/some-key")).andReturn().getResponse();

        assertThat(actual.getStatus()).isEqualTo(200);
        assertThat(actual.getContentType()).isEqualTo("text/plain");
        assertThat(actual.getContentAsString()).isEqualTo("value");
    }

    @Test
    public void shouldGetCachedValue() throws Exception {
        var post1 = post("/api/v1/messages/direct/some-key")
            .contentType("text/plain")
            .content("value");
        mvc.perform(post1).andReturn();

        mvc.perform(get("/api/v1/messages/lazyCache/some-key")).andReturn().getResponse();

        var post2 = post("/api/v1/messages/direct/some-key")
            .contentType("text/plain")
            .content("another-value");
        mvc.perform(post2).andReturn();

        var actual = mvc.perform(get("/api/v1/messages/lazyCache/some-key")).andReturn().getResponse();

        assertThat(actual.getStatus()).isEqualTo(200);
        assertThat(actual.getContentType()).isEqualTo("text/plain");
        assertThat(actual.getContentAsString()).isEqualTo("value");
    }

    @Test
    public void shouldGetLatestValueAfterClearingCache() throws Exception {
        var post1 = post("/api/v1/messages/direct/some-key")
            .contentType("text/plain")
            .content("value");
        mvc.perform(post1).andReturn();

        mvc.perform(get("/api/v1/messages/lazyCache/some-key")).andReturn().getResponse();

        var post2 = post("/api/v1/messages/direct/some-key")
            .contentType("text/plain")
            .content("another-value");
        mvc.perform(post2).andReturn();

        mvc.perform(post("/api/v1/cache/messages/clear")).andReturn().getResponse();

        var actual = mvc.perform(get("/api/v1/messages/lazyCache/some-key")).andReturn().getResponse();

        assertThat(actual.getStatus()).isEqualTo(200);
        assertThat(actual.getContentType()).isEqualTo("text/plain");
        assertThat(actual.getContentAsString()).isEqualTo("another-value");
    }

    @Test
    public void shouldGetLatestValueAfterEagerAccess() throws Exception {
        var post1 = post("/api/v1/messages/direct/some-key")
            .contentType("text/plain")
            .content("value");
        mvc.perform(post1).andReturn();

        mvc.perform(get("/api/v1/messages/lazyCache/some-key")).andReturn().getResponse();

        var post2 = post("/api/v1/messages/eagerCache/some-key")
            .contentType("text/plain")
            .content("another-value");
        mvc.perform(post2).andReturn();

        var eagerReadResult = mvc.perform(get("/api/v1/messages/eagerCache/some-key")).andReturn().getResponse();

        assertThat(eagerReadResult.getStatus()).isEqualTo(200);
        assertThat(eagerReadResult.getContentType()).isEqualTo("text/plain");
        assertThat(eagerReadResult.getContentAsString()).isEqualTo("another-value");

        var lazyReadResult = mvc.perform(get("/api/v1/messages/lazyCache/some-key")).andReturn().getResponse();

        assertThat(lazyReadResult.getStatus()).isEqualTo(200);
        assertThat(lazyReadResult.getContentType()).isEqualTo("text/plain");
        assertThat(lazyReadResult.getContentAsString()).isEqualTo("another-value");
    }

}
