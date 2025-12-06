package org.bananalaba.springcdtemplate;

import org.bananalaba.teamsports.DataEngineConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DataEngineConfig.class)
public class DataAggregationApplication {

    public static void main(String[] arguments) {
        SpringApplication.run(DataAggregationApplication.class, arguments);
    }

}
