package org.bananalaba.springcdtemplate.service;

import org.bananalaba.springcdtemplate.SampleApplication;
import org.bananalaba.springcdtemplate.data.MessageUpdate;
import org.bananalaba.springcdtemplate.service.MessageServiceTest.TestConfiguration;
import org.bananalaba.springcdtemplate.stereotype.WebComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class MessageServiceTest {

    @Autowired
    private MessageService service;

    @BeforeEach
    void reset() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void shouldSaveNewMessage() {
        var message = new MessageUpdate("test");
        service.save("123", message);
    }

    @Configuration
    @ComponentScan(
        basePackageClasses = SampleApplication.class,
        excludeFilters = {
            @Filter(type = FilterType.ASSIGNABLE_TYPE, value = SampleApplication.class),
            @Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebComponent.class)
        }
    )
    public static class TestConfiguration {
    }

}
