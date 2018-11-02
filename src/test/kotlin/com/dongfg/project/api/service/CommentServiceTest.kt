package com.dongfg.project.api.service

import com.dongfg.project.api.model.Comment
import com.dongfg.project.api.repository.CommentRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime


class CommentServiceTest {

    @Mock
    private lateinit var commentRepository: CommentRepository

    @InjectMocks
    private lateinit var commentService: CommentService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun comments() {
        val createTime = LocalDateTime.now()

        given(commentRepository.findAll())
            .willReturn(arrayListOf(Comment(name = "junit4", comment = "hello", createTime = createTime)))

        val comments = commentService.comments()
        Assert.assertEquals(1, comments.size)
        Assert.assertEquals("junit4", comments[0].name)
        Assert.assertEquals("hello", comments[0].comment)
        Assert.assertEquals(createTime, comments[0].createTime)
    }

    @Test
    fun createComment() {
        given(commentRepository.save(Comment(name = "junit4", comment = "hello")))
            .willReturn(
                Comment(
                    id = "GenerateId",
                    name = "junit4",
                    comment = "hello",
                    createTime = LocalDateTime.now()
                )
            )

        val payload = commentService.createComment(Comment(name = "junit4", comment = "hello"))
        Assert.assertTrue(payload.success)
    }
}