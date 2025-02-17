package org.bananalaba.springcdtemplate.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.bananalaba.springcdtemplate.SampleApplication;
import org.bananalaba.springcdtemplate.test.TestLocalStackManager;
import org.bananalaba.springcdtemplate.test.TestSecretsManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;

@SpringBootTest(
    classes = SampleApplication.class,
    properties = "node.ip=127.0.0.1"
)
@TestExecutionListeners(
    value = {TestSecretsManager.class, TestLocalStackManager.class},
    mergeMode = MergeMode.MERGE_WITH_DEFAULTS
)
public class SecretsVaultServiceTest {

    @Autowired
    private SecretsVaultService service;

    @Test
    public void shouldReadDbSecrets() {
        var actualUserName = service.getDbUserName();
        var actualPassword = service.getDbPassword();
        assertThat(actualUserName).isEqualTo("test-user");
        assertThat(actualPassword).isEqualTo("test-password");
    }

}
