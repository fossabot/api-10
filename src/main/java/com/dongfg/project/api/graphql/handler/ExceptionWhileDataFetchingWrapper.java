package com.dongfg.project.api.graphql.handler;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.ExceptionWhileDataFetching;
import graphql.execution.ExecutionPath;
import graphql.language.SourceLocation;

public class ExceptionWhileDataFetchingWrapper extends ExceptionWhileDataFetching {
    ExceptionWhileDataFetchingWrapper(ExecutionPath path, Throwable exception, SourceLocation sourceLocation) {
        super(path, exception, sourceLocation);
    }

    @JsonIgnore
    @Override
    public Throwable getException() {
        return super.getException();
    }
}
