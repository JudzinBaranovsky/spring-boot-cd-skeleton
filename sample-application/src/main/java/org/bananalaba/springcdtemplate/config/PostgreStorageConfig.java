package org.bananalaba.springcdtemplate.config;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.persistence.repository.OrderRepository;
import org.bananalaba.springcdtemplate.persistence.repository.Repositories;
import org.bananalaba.springcdtemplate.service.OrderMapper;
import org.bananalaba.springcdtemplate.service.OrderService;
import org.bananalaba.springcdtemplate.service.SqlOrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.relational.core.mapping.DefaultNamingStrategy;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity;

@Configuration
@Slf4j
@EnableJdbcRepositories(basePackageClasses = Repositories.class)
@Profile("postgresql")
public class PostgreStorageConfig extends AbstractJdbcConfiguration {

    @Bean
    public NamingStrategy namingStrategy() {
        return new DefaultNamingStrategy() {

            @Override
            public String getReverseColumnName(final RelationalPersistentEntity<?> parent) {
                return super.getReverseColumnName(parent) + "_id";
            }


        };
    }

    @Bean
    public OrderService orderService(final @NonNull OrderRepository repository, final @NonNull OrderMapper mapper) {
        return new SqlOrderService(repository, mapper);
    }

}
