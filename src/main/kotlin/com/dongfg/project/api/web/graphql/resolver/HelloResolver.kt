package com.dongfg.project.api.web.graphql.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

/**
 * @author dongfg
 * @date 2018/3/17
 */
@Component
class HelloResolver : GraphQLQueryResolver, GraphQLMutationResolver {

    fun hello(): String {
        return "Hello World"
    }
}