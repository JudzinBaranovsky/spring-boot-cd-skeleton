package org.bananalaba.springcdtemplate.web.data;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.bananalaba.springcdtemplate.data.InMemoryMessageRepository;
import org.bananalaba.springcdtemplate.data.Message;
import org.bananalaba.springcdtemplate.data.MessageRepository;
import org.junit.jupiter.api.Test;

public class InMemoryMessageRepositoryTest {

    private MessageRepository repository = new InMemoryMessageRepository();

    @Test
    public void shouldNotAllowChangingMessageOwner() {
        final var key = "key";

        final var originalPayload = Message.builder()
            .ownerId("ownerA")
            .text("test")
            .build();
        repository.save(key, originalPayload);

        final var maliciousUpdate = Message.builder()
            .ownerId("fraudster")
            .text("test")
            .build();
        assertThatThrownBy(() -> repository.save(key, maliciousUpdate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("owner")
            .hasMessageContaining(key)
            .hasMessageContaining(originalPayload.getOwnerId())
            .hasMessageContaining(maliciousUpdate.getOwnerId());
    }

}
