package com.dongfg.project.api.web.filter

import com.dongfg.project.api.common.Constants
import com.dongfg.project.api.common.Constants.PayloadCode
import com.dongfg.project.api.common.property.ApiProperty
import com.dongfg.project.api.model.AdminUserToken
import com.dongfg.project.api.web.payload.GenericPayload
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.apache.commons.lang3.StringUtils
import org.springframework.http.MediaType
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AdminAuthFilter constructor(
        private val apiProperty: ApiProperty,
        private val objectMapper: ObjectMapper,
        private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authHeader = request.getHeader(Constants.AuthHeader.ADMIN)
        if (StringUtils.isNotEmpty(authHeader)) {
            try {
                val claims = Jwts.parser().setSigningKey(apiProperty.jwt.secret).parseClaimsJws(authHeader).body
                val user = userDetailsService.loadUserByUsername(claims.subject)
                SecurityContextHolder.getContext().authentication = AdminUserToken(user.username, user.authorities)
            } catch (e: JwtException) {
                if (e is ExpiredJwtException) {
                    writeUnauthorizedResponse(response, code = PayloadCode.NOT_LOGIN)
                }
                writeUnauthorizedResponse(response)
                return
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun writeUnauthorizedResponse(response: HttpServletResponse, code: PayloadCode = PayloadCode.FAILURE) {
        response.status = HttpServletResponse.SC_OK
        response.addHeader("Content-type", MediaType.APPLICATION_JSON_UTF8.toString())
        objectMapper.writeValue(response.writer, GenericPayload(false, code = code.code, msg = code.msg))
    }
}