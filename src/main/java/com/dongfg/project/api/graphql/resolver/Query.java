package com.dongfg.project.api.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Query implements GraphQLQueryResolver {
    public String systemTime() {
        return LocalDateTime.now().toString();
    }
}
