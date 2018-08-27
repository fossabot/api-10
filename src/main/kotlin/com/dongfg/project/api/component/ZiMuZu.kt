package com.dongfg.project.api.component

import com.dongfg.project.api.model.ResourceDetail
import com.dongfg.project.api.model.ResourceEpisode
import com.dongfg.project.api.model.ResourceInfo
import mu.KLogging
import org.apache.commons.lang3.StringUtils
import org.dom4j.Element
import org.dom4j.io.SAXReader
import org.json.JSONObject
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import java.net.URL
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

@Component
class ZiMuZu {

    companion object : KLogging()

    private val host = "http://www.zimuzu.io"

    @Autowired
    private lateinit var restTemplate: RestTemplate

    fun search(keyword: String): List<ResourceInfo> {
        val url = "http://www.zimuzu.io/search/api?keyword=$keyword&type=resource"
        val responseJson = restTemplate.getForObject(url, JSONObject::class.java)

        val dataList = responseJson!!.optJSONArray("data") ?: return emptyList()

        return dataList.toList().stream().map {
            ResourceInfo().apply {
                val item = it as HashMap<*, *>
                poster = item["poster"].toString()
                name = item["title"].toString()
                id = item["itemid"].toString()
                link = "$host/resource/$id"
                val publishTimeStr = item["pubtime"].toString()
                publishTime = Instant.ofEpochMilli(publishTimeStr.toLong() * 1000).atZone(ZoneId.systemDefault()).toLocalDateTime()
                val updateTimeStr = item["uptime"].toString()
                updateTime = Instant.ofEpochMilli(updateTimeStr.toLong() * 1000).atZone(ZoneId.systemDefault()).toLocalDateTime()
            }
        }.collect(Collectors.toList())
    }

    fun searchByParser(keyword: String): List<ResourceInfo> {
        val doc = Jsoup.connect("$host/search?keyword=$keyword&type=resource").get()

        return doc.select(".search-item").stream().map {
            ResourceInfo().apply {
                poster = it.select("img").attr("src")
                name = it.select(".list_title").text()
                link = it.select(".fl-info .f14 a").attr("href")
                // /resource/33701 -> 33701
                id = link!!.substring(9)
                val publishTimeStr = it.select(".fl-info .f4").text()
                publishTime = Instant.ofEpochMilli(publishTimeStr.toLong() * 1000).atZone(ZoneId.systemDefault()).toLocalDateTime()
                val updateTimeStr = it.select(".fl-info .f1").text()
                updateTime = Instant.ofEpochMilli(updateTimeStr.toLong() * 1000).atZone(ZoneId.systemDefault()).toLocalDateTime()
            }
        }.collect(Collectors.toList())
    }

    fun detail(resourceId: String): ResourceDetail {
        val detailDataFuture = CompletableFuture.supplyAsync {
            val url = "http://www.zimuzu.io/resource/index_json/rid/$resourceId/channel/tv"
            val responseData = restTemplate.getForObject(url, String::class.java)

            // "var index_info={..." => "{..."
            JSONObject(responseData!!.replace("var index_info=", ""))
        }

        val scoreDataFuture = CompletableFuture.supplyAsync {
            val url = "http://www.zimuzu.io/resource/getScore"
            val formMap = LinkedMultiValueMap<String, String>()
            formMap.add("rid", resourceId)
            restTemplate.postForObject(url, HttpEntity(formMap, HttpHeaders()), JSONObject::class.java)
        }

        val doc = Jsoup.connect("http://www.zimuzu.io/resource/$resourceId").get()
        return ResourceDetail(resourceId).apply {
            rssLink = doc.select(".resource-tit h2 a").attr("href")
            cnName = StringUtils.substringBefore(doc.select(".resource-tit h2").html(), "<")
            enName = doc.select(".resource-con .fl-info li")[0].select("strong").text()
            area = doc.select(".resource-con .fl-info li")[1].select("strong").text()
            category = doc.select(".resource-con .fl-info li")[5].select("strong").text()
            poster = doc.select(".resource-con .fl-img img").attr("src")

            val detailData = detailDataFuture.get()
            playStatus = detailData.getString("play_status")


            val scoreData = scoreDataFuture.get()
            scoreCount = scoreData!!.getInt("score_counts")
            scoreDetail = scoreData.getJSONObject("score_detail").toMap().toSortedMap(reverseOrder()).values.map { it as Int }.toList()
        }
    }

    fun episodes(resourceId: String): List<ResourceEpisode> {
        val url = "http://diaodiaode.me/rss/feed/$resourceId"
        val doc = SAXReader().read(URL(url))

        val episodes = ArrayList<ResourceEpisode>()

        doc.rootElement.element("channel").elementIterator("item").forEach {
            val episodeElement = it as Element

            val id = episodeElement.elementTextTrim("guid")
            val name = episodeElement.elementTextTrim("title")

            episodes.add(ResourceEpisode(id, resourceId, name).apply {
                season = 1
                episode = 1
                try {
                    val meta = """[Ss](\d{1,3})[Ee](\d{1,3})""".toRegex().find(name)!!.groupValues
                    season = meta[1].toInt()
                    episode = meta[2].toInt()
                } catch (ex: KotlinNullPointerException) {
                    logger.error("can not determine 'season' and 'episode' from $name")
                }

                // eg. Mon, 29 May 2017 21:50:32 +0800
                val formatter = DateTimeFormatter.ofPattern("EEE, d MMM y HH:mm:ss Z", Locale.ENGLISH)
                publishTime = LocalDateTime.parse(episodeElement.elementTextTrim("pubDate"), formatter)

                ed2k = episodeElement.elementTextTrim("ed2k")
                magnet = episodeElement.elementTextTrim("magnet")
            })
        }

        return episodes
    }
}