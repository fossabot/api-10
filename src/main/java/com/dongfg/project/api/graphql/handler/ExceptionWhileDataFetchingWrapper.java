package com.dongfg.project.api.graphql.handler;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.ErrorType;
import graphql.ExceptionWhileDataFetching;
import graphql.execution.ExecutionPath;
import graphql.language.SourceLocation;

import java.util.Map;

@SuppressWarnings("EmptyMethod")
class ExceptionWhileDataFetchingWrapper extends ExceptionWhileDataFetching {
    ExceptionWhileDataFetchingWrapper(ExecutionPath path, Throwable exception, SourceLocation sourceLocation) {
        super(path, exception, sourceLocation);
    }

    @JsonIgnore
    @Override
    public Throwable getException() {
        return super.getException();
    }

    @JsonIgnore
    @Override
    public Map<String, Object> getExtensions() {
        return super.getExtensions();
    }

    @JsonIgnore
    @Override
    public ErrorType getErrorType() {
        return super.getErrorType();
    }
}
