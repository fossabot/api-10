package com.dongfg.project.api.graphql.resolver.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class CommonQueryResolver implements GraphQLQueryResolver {

    public String version() {
        return "1.0.0";
    }

}
