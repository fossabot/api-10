package com.dongfg.project.api.common.config

import com.dongfg.project.api.common.property.ApiProperty
import com.dongfg.project.api.web.filter.TotpAuthFilter
import com.dongfg.project.api.web.payload.GenericPayload
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import javax.servlet.http.HttpServletResponse


@EnableWebSecurity
@Configuration
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    companion object : KLogging() {
        val AUTH_URL_LIST = arrayOf(
            "/message/**"
        )
    }

    @Autowired
    private lateinit var apiProperty: ApiProperty

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                    .authorizeRequests()
                    .antMatchers(*AUTH_URL_LIST).authenticated()
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .anyRequest().permitAll()
                .and()
                    .addFilterBefore(TotpAuthFilter(objectMapper), FilterSecurityInterceptor::class.java)
                    .exceptionHandling()
                    .authenticationEntryPoint { _, response, _ ->
                        sendResponse(response, GenericPayload(false, msg = "未授权的访问"), status = HttpServletResponse.SC_FORBIDDEN) }
                .and()
                    .cors()
                .and()
                    .csrf().disable()
        // @formatter:on
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowCredentials = true
        configuration.allowedMethods = arrayListOf("*")
        configuration.allowedHeaders = arrayListOf("*")
        configuration.allowedOrigins = arrayListOf(apiProperty.admin.url)

        val configurationSource = UrlBasedCorsConfigurationSource()
        configurationSource.registerCorsConfiguration("/**", configuration)
        return configurationSource
    }

    private fun sendResponse(
        response: HttpServletResponse,
        payload: GenericPayload,
        status: Int = HttpServletResponse.SC_OK
    ) {
        response.status = status
        response.addHeader("Content-type", MediaType.APPLICATION_JSON_UTF8.toString())
        objectMapper.writeValue(response.writer, payload)
    }
}