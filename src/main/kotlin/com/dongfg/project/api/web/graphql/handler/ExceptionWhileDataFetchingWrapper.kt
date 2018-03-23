package com.dongfg.project.api.web.graphql.handler

import com.fasterxml.jackson.annotation.JsonIgnore
import graphql.ErrorType
import graphql.ExceptionWhileDataFetching
import graphql.execution.ExecutionPath
import graphql.language.SourceLocation

/**
 * @author dongfg
 * @date 2018/3/23
 */
class ExceptionWhileDataFetchingWrapper(path: ExecutionPath?, exception: Throwable?, sourceLocation: SourceLocation?)
    : ExceptionWhileDataFetching(path, exception, sourceLocation) {

    @JsonIgnore
    override fun getErrorType(): ErrorType {
        return super.getErrorType()
    }

    @JsonIgnore
    override fun getExtensions(): MutableMap<String, Any> {
        return super.getExtensions()
    }

    @JsonIgnore
    override fun getException(): Throwable {
        return super.getException()
    }
}