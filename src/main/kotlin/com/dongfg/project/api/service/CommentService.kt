package com.dongfg.project.api.service

import com.dongfg.project.api.model.Comment
import com.dongfg.project.api.repository.CommentRepository
import com.dongfg.project.api.web.payload.GenericPayload
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentService {
    companion object : KLogging()

    @Autowired
    private lateinit var commentRepository: CommentRepository

    fun comments(): List<Comment> {
        return commentRepository.findAll()
    }

    fun createComment(input: Comment): GenericPayload {
        commentRepository.save(input)
        return GenericPayload(true)
    }
}