package com.dongfg.project.api.graphql.handler;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.execution.ExecutionPath;
import graphql.language.SourceLocation;
import graphql.servlet.DefaultGraphQLErrorHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dongfg
 * @date 2017/10/15
 */
@Component
public class CustomGraphqlErrorHandler extends DefaultGraphQLErrorHandler {
    @Override
    protected List<GraphQLError> filterGraphQLErrors(List<GraphQLError> errors) {
        return errors.stream()
                .filter(e -> e instanceof ExceptionWhileDataFetching || super.isClientError(e))
                .map(e -> e instanceof ExceptionWhileDataFetching ? wrapError(e) : e)
                .collect(Collectors.toList());
    }

    private ExceptionWhileDataFetchingWrapper wrapError(GraphQLError error) {
        List<SourceLocation> locations = error.getLocations();

        SourceLocation sourceLocation = null;
        if (!CollectionUtils.isEmpty(locations)) {
            sourceLocation = locations.get(0);
        }

        ExecutionPath path = ExecutionPath.rootPath();
        if (!CollectionUtils.isEmpty(error.getPath())) {
            path = path.segment(String.valueOf(error.getPath().get(0)));
        }

        return new ExceptionWhileDataFetchingWrapper(path
                , ((ExceptionWhileDataFetching) error).getException(), sourceLocation);
    }
}
