package com.dongfg.project.api

import com.dongfg.project.api.repository.CommentRepositoryTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    CommentRepositoryTest::class
)
class RepositoryTests {
}