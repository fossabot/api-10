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

    private val host = "http://www.zimuzu.tv"

    @Autowired
    private lateinit var restTemplate: RestTemplate

    fun search(keyword: String): List<ResourceInfo> {
        val url = "http://www.zimuzu.tv/search/api?keyword=$keyword&type=resource"
        val responseJson = restTemplate.getForObject(url, JSONObject::class.java)

        val dataList = responseJson!!.optJSONArray("data") ?: return emptyList()

        return dataList.toList().stream().map {
            val item = it as HashMap<*, *>
            val poster = item["poster"].toString()
            val name = item["title"].toString()
            val id = item["itemid"].toString()
            val link = "$host/resource/$id"
            val publishTimeStr = item["pubtime"].toString()
            val publishTime = Instant.ofEpochMilli(publishTimeStr.toLong() * 1000).atZone(ZoneId.systemDefault()).toLocalDateTime()
            val updateTimeStr = item["uptime"].toString()
            val updateTime = Instant.ofEpochMilli(updateTimeStr.toLong() * 1000).atZone(ZoneId.systemDefault()).toLocalDateTime()

            ResourceInfo(id, name, link, poster, publishTime, updateTime)
        }.collect(Collectors.toList())
    }

    fun searchByParser(keyword: String): List<ResourceInfo> {
        val doc = Jsoup.connect("$host/search?keyword=$keyword&type=resource").get()

        return doc.select(".search-item").stream().map {
            val poster = it.select("img").attr("src")
            val name = it.select(".list_title").text()
            val link = it.select(".fl-info .f14 a").attr("href")
            // /resource/33701 -> 33701
            val id = link.substring(9)
            val publishTimeStr = it.select(".fl-info .f4").text()
            val publishTime = Instant.ofEpochMilli(publishTimeStr.toLong() * 1000).atZone(ZoneId.systemDefault()).toLocalDateTime()
            val updateTimeStr = it.select(".fl-info .f1").text()
            val updateTime = Instant.ofEpochMilli(updateTimeStr.toLong() * 1000).atZone(ZoneId.systemDefault()).toLocalDateTime()

            ResourceInfo(id, name, link, poster, publishTime, updateTime)
        }.collect(Collectors.toList())
    }

    fun detail(resourceId: String): ResourceDetail {
        val detailDataFuture = CompletableFuture.supplyAsync {
            val url = "http://www.zimuzu.tv/resource/index_json/rid/$resourceId/channel/tv"
            val responseData = restTemplate.getForObject(url, String::class.java)

            // "var index_info={..." => "{..."
            JSONObject(responseData!!.replace("var index_info=", ""))
        }

        val scoreDataFuture = CompletableFuture.supplyAsync {
            val url = "http://www.zimuzu.tv/resource/getScore"
            val formMap = LinkedMultiValueMap<String, String>()
            formMap.add("rid", resourceId)
            restTemplate.postForObject(url, HttpEntity(formMap, HttpHeaders()), JSONObject::class.java)
        }

        val doc = Jsoup.connect("http://www.zimuzu.tv/resource/$resourceId").get()
        val rssLink = doc.select(".resource-tit h2 a").attr("href")
        val cnName = StringUtils.substringBefore(doc.select(".resource-tit h2").html(), "<")
        val enName = doc.select(".resource-con .fl-info li")[0].select("strong").text()
        val area = doc.select(".resource-con .fl-info li")[1].select("strong").text()
        val category = doc.select(".resource-con .fl-info li")[5].select("strong").text()

        val poster = doc.select(".resource-con .fl-img img").attr("src")

        val detailData = detailDataFuture.get()
        val playStatus = detailData.getString("play_status")


        val scoreData = scoreDataFuture.get()
        val scoreCount = scoreData!!.getInt("score_counts")
        val scoreDetail = scoreData.getJSONObject("score_detail").toMap().toSortedMap(reverseOrder()).values.map { it as Int }.toList()

        return ResourceDetail(resourceId, poster, rssLink, cnName, enName, playStatus, area, category, scoreCount, scoreDetail)
    }

    fun episodes(resourceId: String): List<ResourceEpisode> {
        val url = "http://diaodiaode.me/rss/feed/$resourceId"
        val doc = SAXReader().read(URL(url))

        val episodes = ArrayList<ResourceEpisode>()

        val iterator = doc.rootElement.element("channel").elementIterator("item")
        iterator.forEach {
            val episodeElement = it as Element

            val id = episodeElement.elementTextTrim("guid")
            val name = episodeElement.elementTextTrim("title")

            var season = 1
            var episode = 1
            try {
                val meta = """[Ss](\d{1,3})[Ee](\d{1,3})""".toRegex().find(name)!!.groupValues
                season = meta[1].toInt()
                episode = meta[2].toInt()
            } catch (ex: KotlinNullPointerException) {
                logger.error("can not determine 'season' and 'episode' from $name")
            }

            // eg. Mon, 29 May 2017 21:50:32 +0800
            val formatter = DateTimeFormatter.ofPattern("EEE, d MMM y HH:mm:ss Z", Locale.ENGLISH)
            val publishTime = LocalDateTime.parse(episodeElement.elementTextTrim("pubDate"), formatter)

            val ed2k = episodeElement.elementTextTrim("ed2k")
            val magnet = episodeElement.elementTextTrim("magnet")

            episodes.add(ResourceEpisode(id, resourceId, name, season, episode, ed2k, magnet, publishTime))
        }

        return episodes
    }
}