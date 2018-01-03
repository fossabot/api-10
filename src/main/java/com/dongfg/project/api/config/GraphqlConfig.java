package com.dongfg.project.api.config;

import com.coxautodev.graphql.tools.SchemaParserOptions;
import com.dongfg.project.api.graphql.scalar.GraphqlDate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dongfg
 * @date 17-12-21
 */
@Configuration
public class GraphqlConfig {

    @Bean
    public SchemaParserOptions schemaParserOptions() {
        return SchemaParserOptions.newOptions()
                .build();
    }

    @Bean
    public GraphqlDate graphqlDate() {
        return new GraphqlDate();
    }
}
