package org.bananalaba.springcdtemplate.config;

import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.persistence.repository.Repositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.mapping.DefaultNamingStrategy;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;

@Configuration
@Slf4j
@EnableJdbcRepositories(basePackageClasses = Repositories.class)
public class DatabaseConfig extends AbstractJdbcConfiguration {

    @Bean
    public NamingStrategy namingStrategy() {
        return new DefaultNamingStrategy() {

            @Override
            public String getReverseColumnName(final RelationalPersistentEntity<?> parent) {
                return super.getReverseColumnName(parent) + "_id";
            }


        };
    }

}
