package com.dongfg.project.api.service

import com.dongfg.project.api.repository.CommentRepository
import com.dongfg.project.api.model.Comment
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author dongfg
 * @date 2018/3/22
 */
@Service
class CommentService {
    companion object : KLogging()

    @Autowired
    private lateinit var commentRepository: CommentRepository

    fun comments(): List<Comment> {
        return commentRepository.findAll()
    }

    fun createComment(input: Comment): Comment {
        commentRepository.save(input)
        return input
    }
}