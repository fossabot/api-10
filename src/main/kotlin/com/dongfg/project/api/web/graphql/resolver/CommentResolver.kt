package com.dongfg.project.api.web.graphql.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.dongfg.project.api.model.Comment
import com.dongfg.project.api.service.CommentService
import com.dongfg.project.api.web.payload.GenericPayload
import graphql.schema.DataFetchingEnvironment
import graphql.servlet.GraphQLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CommentResolver : GraphQLQueryResolver, GraphQLMutationResolver {
    @Autowired
    private lateinit var commentService: CommentService

    fun comments(): List<Comment> {
        return commentService.comments()
    }

    fun commentCreate(input: Comment, env: DataFetchingEnvironment): GenericPayload {
        val context = env.getContext<GraphQLContext>()
        input.clientIp = context.httpServletRequest.get().remoteAddr
        return commentService.createComment(input)
    }
}