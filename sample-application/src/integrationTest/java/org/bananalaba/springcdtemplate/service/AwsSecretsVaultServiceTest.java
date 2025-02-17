package org.bananalaba.springcdtemplate.service;

import org.bananalaba.springcdtemplate.test.TestLocalStackManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;

@TestExecutionListeners(
    value = TestLocalStackManager.class,
    mergeMode = MergeMode.MERGE_WITH_DEFAULTS
)
@ActiveProfiles("aws")
public class AwsSecretsVaultServiceTest extends SecretsVaultServiceTest {

    public AwsSecretsVaultServiceTest() {
        super("aws-test-user", "aws-test-password");
    }

}
