package com.dongfg.project.api.web.graphql.handler

import graphql.ExceptionWhileDataFetching
import graphql.GraphQLError
import graphql.execution.ExecutionPath
import graphql.language.SourceLocation
import graphql.servlet.DefaultGraphQLErrorHandler
import org.springframework.stereotype.Component
import org.springframework.util.CollectionUtils
import java.util.stream.Collectors


@Component
class CustomGraphqlErrorHandler : DefaultGraphQLErrorHandler() {
    override fun filterGraphQLErrors(errors: List<GraphQLError>): List<GraphQLError> {
        return errors.stream()
                .filter { e -> e is ExceptionWhileDataFetching || super.isClientError(e) }
                .map { e -> if (e is ExceptionWhileDataFetching) wrapError(e) else e }
                .collect(Collectors.toList())
    }

    private fun wrapError(error: GraphQLError): ExceptionWhileDataFetchingWrapper {
        val locations = error.locations

        var sourceLocation: SourceLocation? = null
        if (!CollectionUtils.isEmpty(locations)) {
            sourceLocation = locations[0]
        }

        var path = ExecutionPath.rootPath()
        if (!CollectionUtils.isEmpty(error.path)) {
            path = path.segment(error.path[0].toString())
        }

        return ExceptionWhileDataFetchingWrapper(path, (error as ExceptionWhileDataFetching).exception, sourceLocation)
    }
}