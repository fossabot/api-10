package com.dongfg.project.api

import com.dongfg.project.api.web.controller.CommentControllerTest
import com.dongfg.project.api.web.controller.MessageControllerTest
import com.dongfg.project.api.web.controller.ResourceControllerTest
import com.dongfg.project.api.web.controller.RssControllerTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    CommentControllerTest::class, MessageControllerTest::class, ResourceControllerTest::class,
    RssControllerTest::class
)
class WebTests {
}