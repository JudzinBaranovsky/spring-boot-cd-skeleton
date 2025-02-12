package org.bananalaba.springcdtemplate.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.jdbc.core.JdbcOperations;

@Configuration
@Slf4j
public class DatabaseConfig extends AbstractJdbcConfiguration {

    @Autowired
    public void test(final JdbcOperations jdbc) {
        var result = jdbc.queryForObject("select 1", Integer.class);
        log.info("database result: {}", result);
    }

}
