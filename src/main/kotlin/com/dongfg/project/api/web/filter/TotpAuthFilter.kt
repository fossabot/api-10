package com.dongfg.project.api.web.filter

import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.component.Totp
import com.dongfg.project.api.model.TotpUserToken
import com.dongfg.project.api.web.payload.GenericPayload
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TotpAuthFilter constructor(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(Constants.AuthHeader.TOTP)
        if (!authHeader.isNullOrEmpty()) {
            if (Totp.validate(authHeader)) {
                SecurityContextHolder.getContext().authentication = TotpUserToken()
            } else {
                response.status = HttpServletResponse.SC_OK
                response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString())
                objectMapper.writeValue(response.writer, GenericPayload(false, msg = "totp 错误"))
                return
            }
        }

        filterChain.doFilter(request, response)
    }
}