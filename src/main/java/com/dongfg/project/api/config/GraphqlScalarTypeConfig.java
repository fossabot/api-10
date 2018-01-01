package com.dongfg.project.api.config;

import com.dongfg.project.api.graphql.scalar.GraphqlDate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author dongfg
 * @date 17-12-21
 */
@Configuration
public class GraphqlScalarTypeConfig {

    @Bean
    public GraphqlDate graphqlDate() {
        return new GraphqlDate();
    }
}
