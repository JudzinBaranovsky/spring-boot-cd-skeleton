package org.bananalaba.springcdtemplate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:${security.secrets.filePath}")
public class FileSystemSecretsConfig {
}
