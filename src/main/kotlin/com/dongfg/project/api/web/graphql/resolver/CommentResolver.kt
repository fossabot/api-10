package com.dongfg.project.api.web.graphql.resolver

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.dongfg.project.api.repository.CommentRepository
import com.dongfg.project.api.web.graphql.type.Comment
import graphql.schema.DataFetchingEnvironment
import graphql.servlet.GraphQLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * @author dongfg
 * @date 2018/3/19
 */
@Component
class CommentResolver : GraphQLQueryResolver, GraphQLMutationResolver {
    @Autowired
    private lateinit var commentRepository: CommentRepository

    fun comments(): List<Comment> {
        return commentRepository.findAll()
    }

    fun createComment(input: Comment, env: DataFetchingEnvironment): Comment {
        val context = env.getContext<GraphQLContext>()
        input.clientIp = context.request.get().remoteAddr
        commentRepository.save(input)
        return input
    }
}