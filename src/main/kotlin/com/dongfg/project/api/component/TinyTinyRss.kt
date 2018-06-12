package com.dongfg.project.api.component

import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.common.property.ApiProperty
import com.dongfg.project.api.common.util.Json
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.aspectj.lang.annotation.Aspect
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate


@Aspect
@Component
class TinyTinyRss {

    @Autowired
    private lateinit var apiProperty: ApiProperty

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var stringRedisTemplate: StringRedisTemplate

    @Autowired
    private lateinit var restTemplate: RestTemplate

    /**
     * login for sessionId
     */
    fun login() {
        val requestJson = Json {
            "op" to "login"
            "user" to apiProperty.rss.user
            "password" to apiProperty.rss.password
        }

        val responseStr = restTemplate.postForObject(apiProperty.rss.apiUrl, requestJson.toString(), String::class.java)
        val responseJson = JSONObject(responseStr)

        val sessionId = responseJson.getJSONObject("content").getString("session_id")
        stringRedisTemplate.opsForValue().set(Constants.RedisKey.RSS_SESSION_ID, sessionId)
    }

    fun getCategories(): List<Category> {
        val sessionId = stringRedisTemplate.opsForValue().get(Constants.RedisKey.RSS_SESSION_ID)
        val requestJson = Json {
            "op" to "getCategories"
            "sid" to sessionId
        }

        val responseJson = restTemplate.postForObject(apiProperty.rss.apiUrl, requestJson.toString(), JSONObject::class.java)
                ?: return emptyList()

        return objectMapper.convertValue<List<Category>>(responseJson.getJSONArray("content"),
                object : TypeReference<List<Category>>() {})!!.filter { it.id != "-1" }
    }

    fun getFeeds(categoryId: String): List<Feed> {
        val sessionId = stringRedisTemplate.opsForValue().get(Constants.RedisKey.RSS_SESSION_ID)
        val requestJson = Json {
            "op" to "getFeeds"
            "sid" to sessionId
            "cat_id" to categoryId
        }

        val responseJson = restTemplate.postForObject(apiProperty.rss.apiUrl, requestJson.toString(), JSONObject::class.java)
                ?: return emptyList()

        return objectMapper.convertValue<List<Feed>>(responseJson.getJSONArray("content"), object : TypeReference<List<Feed>>() {})!!
    }

    fun subscribeToFeed(url: String, categoryId: String) {
        val sessionId = stringRedisTemplate.opsForValue().get(Constants.RedisKey.RSS_SESSION_ID)
        val requestJson = Json {
            "op" to "subscribeToFeed"
            "sid" to sessionId
            "feed_url" to url
            "category_id" to categoryId
        }

        val responseJson = restTemplate.postForObject(apiProperty.rss.apiUrl, requestJson.toString(), JSONObject::class.java)

        println(responseJson.toString())
    }

    fun unsubscribeFeed(feedId: String) {
        val sessionId = stringRedisTemplate.opsForValue().get(Constants.RedisKey.RSS_SESSION_ID)
        val requestJson = Json {
            "op" to "unsubscribeFeed"
            "sid" to sessionId
            "feed_id" to feedId
        }

        val responseJson = restTemplate.postForObject(apiProperty.rss.apiUrl, requestJson.toString(), JSONObject::class.java)

        println(responseJson.toString())
    }

    data class Category(val id: String, var title: String, val unread: Int, var feeds: List<Feed>? = null)
    data class Feed(val id: String, val title: String, val unread: Int, @JsonProperty("feed_url") val url: String)
}