package com.dongfg.project.api.web.filter

import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.common.util.WeChatUserHolder
import com.dongfg.project.api.service.WeChatService
import com.dongfg.project.api.web.payload.GenericPayload
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.lang3.StringUtils
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author dongfg
 * @date 2018/3/29
 */
class WeChatFilter constructor(
        private val weChatService: WeChatService,
        private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authHeader = request.getHeader(Constants.AuthHeader.WECHAT)
        if (StringUtils.isNotEmpty(authHeader)) {
            val user = weChatService.findUser(authHeader)
            if (user.isPresent) {
                WeChatUserHolder.set(user.get())
            } else {
                writeUnauthorizedResponse(response)
            }
        } else {
            writeUnauthorizedResponse(response)
        }

        filterChain.doFilter(request, response)
    }

    private fun writeUnauthorizedResponse(response: HttpServletResponse) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.addHeader("Content-type", MediaType.APPLICATION_JSON_UTF8.toString())
        response.addHeader("Access-Control-Allow-Origin", "*")
        response.addHeader("Access-Control-Allow-Credentials", "true")
        objectMapper.writeValue(response.writer, GenericPayload(false, msg = "Unauthorized"))
    }
}