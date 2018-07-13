package com.dongfg.project.api.spider

import com.dongfg.project.api.component.ZiMuZu
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


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

    @Test
    fun detail() {
        println(ziMuZu.detail("31892"))
    }

    @Test
    fun episodes() {
        ziMuZu.episodes("31892").forEach {
            println(it)
        }
    }

    @Test
    fun s01e01() {
        val name = "地球百子.The.100.S01E01.中英字幕.WEB-HR.AC3.1024X576.x264.mkv"
        val regex = """(S\d{1,3})(E\d{1,3})""".toRegex()

        println(regex.find(name)!!.groupValues[1])
        println(regex.find(name)!!.groupValues[2])
    }

    @Test
    fun parse() {
        val timeStr = "Mon, 29 May 2017 21:50:32 +0800"

        val formatter = DateTimeFormatter.ofPattern("EEE, d MMM y HH:mm:ss Z", Locale.ENGLISH)
        println(LocalDateTime.parse(timeStr, formatter))
    }
}