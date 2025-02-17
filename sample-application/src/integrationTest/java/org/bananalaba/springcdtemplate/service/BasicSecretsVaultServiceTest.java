package org.bananalaba.springcdtemplate.service;

import org.bananalaba.springcdtemplate.test.TestLocalSecretsManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;

@ActiveProfiles("basic")
@TestExecutionListeners(
    value = TestLocalSecretsManager.class,
    mergeMode = MergeMode.MERGE_WITH_DEFAULTS
)
public class BasicSecretsVaultServiceTest extends SecretsVaultServiceTest {

    public BasicSecretsVaultServiceTest() {
        super("test-user", "test-password");
    }

}
