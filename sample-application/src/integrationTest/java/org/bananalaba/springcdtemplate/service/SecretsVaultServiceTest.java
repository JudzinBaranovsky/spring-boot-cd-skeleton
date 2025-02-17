package org.bananalaba.springcdtemplate.service;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.RequiredArgsConstructor;
import org.bananalaba.springcdtemplate.SampleApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    classes = SampleApplication.class,
    properties = "node.ip=127.0.0.1"
)
@RequiredArgsConstructor
public abstract class SecretsVaultServiceTest {

    @Autowired
    private SecretsVaultService service;

    private final String expectedUserName;
    private final String expectedPassword;

    @Test
    public void shouldReadDbSecrets() {
        var actualUserName = service.getDbUserName();
        var actualPassword = service.getDbPassword();
        assertThat(actualUserName).isEqualTo(expectedUserName);
        assertThat(actualPassword).isEqualTo(expectedPassword);
    }

}
