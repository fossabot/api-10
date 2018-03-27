package com.dongfg.project.api.web.controller

import com.dongfg.project.api.common.util.EnableSwaggerDoc
import com.dongfg.project.api.model.Comment
import com.dongfg.project.api.service.CommentService
import com.dongfg.project.api.web.payload.GenericPayload
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * @author dongfg
 * @date 2018/3/22
 */
@EnableSwaggerDoc
@RequestMapping("/comment")
@RestController
class CommentController {
    companion object : KLogging()

    @Autowired
    private lateinit var commentService: CommentService

    @GetMapping
    fun comments(): List<Comment> {
        return commentService.comments()
    }

    @PostMapping
    fun createComment(@RequestBody input: Comment, request: HttpServletRequest): GenericPayload {
        input.clientIp = request.remoteAddr
        return commentService.createComment(input)
    }
}