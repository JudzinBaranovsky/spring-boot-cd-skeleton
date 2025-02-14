package org.bananalaba.springcdtemplate.config;

import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.ext.mongodb.database.MongoLiquibaseDatabase;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.bananalaba.springcdtemplate.service.DocumentOrderService;
import org.bananalaba.springcdtemplate.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoOperations;

@Configuration
@Profile("mongodb")
@Slf4j
@Import(LiquibaseAutoConfiguration.class)
public class MongoDbStorageConfig {

    @Bean
    public OrderService orderService() {
        return new DocumentOrderService();
    }

    @Autowired
    public void applySchemaMigrations(final MongoConnectionDetails connectionDetails, final MongoOperations dbOps) throws Exception {
        MongoLiquibaseDatabase database = (MongoLiquibaseDatabase) DatabaseFactory.getInstance().openDatabase(
            connectionDetails.getConnectionString().getConnectionString(),
            null,
            null,
            null,
            null
        );
        Liquibase liquibase = new Liquibase("migrations/mongodb/liquibase/changelog-master.yaml", new ClassLoaderResourceAccessor(), database);
        liquibase.update("");

        var result = dbOps.collectionExists("orders");
        log.info("test MongoDB collection created: {}", result);
    }

}
