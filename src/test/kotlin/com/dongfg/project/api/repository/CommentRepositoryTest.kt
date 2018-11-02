package com.dongfg.project.api.repository

import com.dongfg.project.api.MongoTestConfig
import com.dongfg.project.api.model.Comment
import graphql.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@DataMongoTest
@Import(MongoTestConfig::class)
@ActiveProfiles("test")
class CommentRepositoryTest {
    @Autowired
    private lateinit var commentRepository: CommentRepository

    @Test
    fun save() {
        val comment = Comment(name = "junit4", comment = "hello world")
        commentRepository.save(comment)

        Assert.assertNotNull(comment.id)
    }
}