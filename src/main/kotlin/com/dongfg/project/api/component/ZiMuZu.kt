package com.dongfg.project.api.component

import com.dongfg.project.api.model.ResourceInfo
import org.json.JSONObject
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.time.Instant
import java.time.ZoneId
import java.util.stream.Collectors

@Component
class ZiMuZu {

    private val host = "http://www.zimuzu.tv"

    @Autowired
    private lateinit var restTemplate: RestTemplate

    fun search(keyword: String): List<ResourceInfo> {
        val url = "http://www.zimuzu.tv/search/api?keyword=$keyword&type=resource"
        val responseJson = restTemplate.getForObject(url, JSONObject::class.java)
                ?: return emptyList()

        val dataList = responseJson.optJSONArray("data") ?: return emptyList()

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
}