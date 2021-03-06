package com.dongfg.project.api.component

import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.common.property.ApiProperty
import com.dongfg.project.api.common.util.Json
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import org.aspectj.lang.annotation.Aspect
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate


@Aspect
@Component
class TinyTinyRss {

    companion object : KLogging()

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
    fun login(): String {
        val responseJson = getJsonResponse(Json {
            "op" to "login"
            "user" to apiProperty.rss.user
            "password" to apiProperty.rss.password
        })

        val sessionId = responseJson!!.getJSONObject("content").getString("session_id")
        stringRedisTemplate.opsForValue().set(Constants.RedisKey.RSS_SESSION_ID, sessionId)
        return sessionId
    }

    fun getCategories(): List<Category> {
        val responseJson = getJsonResponse(Json {
            "op" to "getCategories"
            "sid" to getSessionId()
        })

        val categories = objectMapper.convertValue<List<Category>>(responseJson?.getJSONArray("content"),
            object : TypeReference<List<Category>>() {})

        return categories.filter { it.id != "-1" }
    }

    fun getFeeds(categoryId: String): List<Feed> {
        val responseJson = getJsonResponse(Json {
            "op" to "getFeeds"
            "sid" to getSessionId()
            "cat_id" to categoryId
        })

        return objectMapper.convertValue<List<Feed>>(responseJson?.getJSONArray("content"),
            object : TypeReference<List<Feed>>() {})
    }

    fun subscribeToFeed(url: String, categoryId: String): JSONObject? {
        return getJsonResponse(Json {
            "op" to "subscribeToFeed"
            "sid" to getSessionId()
            "feed_url" to url
            "category_id" to categoryId
        })
    }

    fun unsubscribeFeed(feedId: String): JSONObject? {
        return getJsonResponse(Json {
            "op" to "unsubscribeFeed"
            "sid" to getSessionId()
            "feed_id" to feedId
        })
    }

    private fun getSessionId(): String {
        val sessionId = stringRedisTemplate.opsForValue().get(Constants.RedisKey.RSS_SESSION_ID)
        val requestJson = Json {
            "op" to "isLoggedIn"
            "sid" to sessionId
        }

        val responseJson =
            restTemplate.postForObject(apiProperty.rss.apiUrl, requestJson.toString(), JSONObject::class.java)
        val isLoggedIn = responseJson!!.getJSONObject("content").getBoolean("status")

        if (!isLoggedIn) {
            return login()
        }

        return sessionId!!
    }

    private fun getJsonResponse(requestJson: Json): JSONObject? {
        val responseJson =
            restTemplate.postForObject(apiProperty.rss.apiUrl, requestJson.toString(), JSONObject::class.java)
        logger.info(responseJson.toString())
        return responseJson
    }

    data class Category(val id: String, var title: String, val unread: Int, var feeds: List<Feed>? = null)
    data class Feed(val id: String, val title: String, val unread: Int, @JsonProperty("feed_url") val url: String)
}