package org.bananalaba.springcdtemplate.test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import org.apache.commons.io.FileUtils;
import org.springframework.core.Ordered;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

public class TestSecretsManager implements TestExecutionListener, Ordered {

    private File tmpDirectory;

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        tmpDirectory = Files.createTempDirectory("secrets-vault").toFile();

        var dbSecretsFilePath = Path.of(tmpDirectory.getPath(), "db-secrets.properties");
        var dbSecretsFile = Files.createFile(dbSecretsFilePath);
        var secretsContent = """
            db.connection.userName=test-user
            db.connection.password=test-password
            """;
        FileUtils.writeStringToFile(dbSecretsFile.toFile(), secretsContent, Charset.forName("UTF-8"));

        System.setProperty("security.secrets.filePath", dbSecretsFilePath.toAbsolutePath().toString());
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        Files.walk(tmpDirectory.toPath())
            .sorted(Comparator.<Path>naturalOrder().reversed())
            .forEach(file -> {
                try {
                    Files.deleteIfExists(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }

    @Override
    public int getOrder() {
        return -1000;
    }

}
