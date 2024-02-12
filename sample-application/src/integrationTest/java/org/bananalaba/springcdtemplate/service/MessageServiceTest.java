package org.bananalaba.springcdtemplate.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.bananalaba.springcdtemplate.SampleApplication;
import org.bananalaba.springcdtemplate.data.Message;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.TransientSecurityContext;
import org.springframework.security.core.userdetails.User;
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
    public void ownerShouldReadMessage() {
        authoriseAs("owner");

        var message = new MessageUpdate("test");
        service.save("123", message);

        var saved = service.get("123");

        var expected = Message.builder()
            .ownerId("owner")
            .text("test")
            .build();
        assertThat(saved).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void ownerShouldUpdateMessage() {
        authoriseAs("owner");

        var message = new MessageUpdate("test");
        service.save("123", message);

        var updatedMessage = new MessageUpdate("new text");
        service.save("123", updatedMessage);

        var saved = service.get("123");

        var expected = Message.builder()
            .ownerId("owner")
            .text("new text")
            .build();
        assertThat(saved).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void ownerShouldDeleteMessage() {
        authoriseAs("owner");

        var message = new MessageUpdate("test");
        service.save("123", message);

        service.delete("123");

        assertThatThrownBy(() -> service.get("123"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("123");
    }

    @Test
    public void authorisedUserOtherThanOwnerShouldReadMessage() {
        authoriseAs("owner");

        var message = new MessageUpdate("test");
        service.save("123", message);

        authoriseAs("reader");
        var saved = service.get("123");

        var expected = Message.builder()
            .ownerId("owner")
            .text("test")
            .build();
        assertThat(saved).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void nonOwnerShouldNotUpdateMessage() {
        authoriseAs("owner");

        var message = new MessageUpdate("test");
        service.save("123", message);

        authoriseAs("stranger");

        var updatedMessage = new MessageUpdate("new text");
        assertThatThrownBy(() -> service.save("123", updatedMessage))
            .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    public void nonOwnerShouldNotDeleteMessage() {
        authoriseAs("owner");

        var message = new MessageUpdate("test");
        service.save("123", message);

        authoriseAs("stranger");

        assertThatThrownBy(() -> service.delete("123"))
            .isInstanceOf(AccessDeniedException.class);
    }

    private void authoriseAs(final String principalId) {
        var userDetails = new User(principalId, "some password", List.of());
        var credentials = new Object();
        var authentication = new TestingAuthenticationToken(userDetails, credentials);

        var context = new TransientSecurityContext();
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
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
