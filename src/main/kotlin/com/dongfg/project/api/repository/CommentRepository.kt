package com.dongfg.project.api.repository

import com.dongfg.project.api.web.graphql.type.Comment
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * @author dongfg
 * @date 2018/3/19
 */
interface CommentRepository : MongoRepository<Comment, String> {
}