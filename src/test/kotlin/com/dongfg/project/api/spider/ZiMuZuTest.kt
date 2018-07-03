package com.dongfg.project.api.spider

import com.dongfg.project.api.component.ZiMuZu
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("dev")
class ZiMuZuTest {

    @Autowired
    private lateinit var ziMuZu: ZiMuZu

    @Test
    fun search() {
        ziMuZu.search("西部世界").forEach {
            println(it)
        }
    }
}