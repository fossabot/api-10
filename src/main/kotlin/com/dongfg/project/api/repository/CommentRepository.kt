package com.dongfg.project.api.repository

import com.dongfg.project.api.model.Comment
import org.springframework.data.mongodb.repository.MongoRepository

interface CommentRepository : MongoRepository<Comment, String>