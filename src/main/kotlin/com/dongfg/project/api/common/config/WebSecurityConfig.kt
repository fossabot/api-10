package com.dongfg.project.api.common.config

import com.dongfg.project.api.common.property.ApiProperty
import com.dongfg.project.api.service.WeChatService
import com.dongfg.project.api.web.filter.AdminAuthFilter
import com.dongfg.project.api.web.filter.WeChatFilter
import com.dongfg.project.api.web.payload.GenericPayload
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.DefaultClaims
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.*
import javax.servlet.http.HttpServletResponse


@EnableWebSecurity
@Configuration
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    companion object : KLogging()

    @Value("\${spring.security.user.name}")
    private lateinit var username: String

    @Value("\${spring.security.user.password}")
    private lateinit var password: String

    @Autowired
    private lateinit var apiProperty: ApiProperty

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var weChatService: WeChatService

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication().passwordEncoder(BCryptPasswordEncoder())
                .withUser(username).password(password)
                .authorities("USER", "ACTUATOR")
    }

    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers("/admin/**").authenticated()
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .anyRequest().permitAll()
                .and()
                .addFilterBefore(AdminAuthFilter(apiProperty, objectMapper, userDetailsService), FilterSecurityInterceptor::class.java)
                .formLogin()
                    .loginPage("/admin/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler { _, response, authentication ->
                        val claims = DefaultClaims()
                        claims.subject = authentication.name
                        claims.issuedAt = Date()
                        claims.expiration = Date(System.currentTimeMillis() + apiProperty.jwt.timeout.toMillis())
                        val token = Jwts.builder()
                                .setClaims(claims)
                                .signWith(SignatureAlgorithm.HS512, apiProperty.jwt.secret)
                                .compact()
                        sendResponse(response, GenericPayload(true, data = token)) }
                    .failureHandler { _, response, e ->
                        sendResponse(response, GenericPayload(false, msg = e.localizedMessage)) }
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/admin/logout")
                    .invalidateHttpSession(true)
                    .logoutSuccessHandler { _, response, _ ->
                        sendResponse(response, GenericPayload(true)) }
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint { _, response, e ->
                        sendResponse(response, GenericPayload(false, msg = e.localizedMessage), status = HttpServletResponse.SC_FORBIDDEN) }
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
    fun weChatFilter(): FilterRegistrationBean<WeChatFilter> {
        val registrationBean = FilterRegistrationBean<WeChatFilter>()
        registrationBean.filter = WeChatFilter(weChatService, objectMapper)
        registrationBean.addUrlPatterns("/wechat/authy")
        registrationBean.addUrlPatterns("/wechat/formId/*")
        registrationBean.addUrlPatterns("/wechat/userInfo")
        return registrationBean
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowCredentials = true
        configuration.allowedMethods = arrayListOf("*")
        configuration.allowedHeaders = arrayListOf("*")
        configuration.allowedOrigins = arrayListOf(apiProperty.admin.url)

        val configurationSource = UrlBasedCorsConfigurationSource()
        configurationSource.registerCorsConfiguration("/admin/**", configuration)
        return configurationSource
    }

    private fun sendResponse(response: HttpServletResponse, payload: GenericPayload, status: Int = HttpServletResponse.SC_OK) {
        response.status = status
        response.addHeader("Content-type", MediaType.APPLICATION_JSON_UTF8.toString())
        objectMapper.writeValue(response.writer, payload)
    }
}