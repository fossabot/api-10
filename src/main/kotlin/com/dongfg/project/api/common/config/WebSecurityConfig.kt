package com.dongfg.project.api.common.config

import com.dongfg.project.api.common.property.ApiProperty
import com.dongfg.project.api.web.payload.GenericPayload
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.jwt.JwtHelper
import org.springframework.security.jwt.crypto.sign.MacSigner
import javax.servlet.http.HttpServletResponse


/**
 * @author dongfg
 * @date 2018/3/17
 */
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

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication().passwordEncoder(BCryptPasswordEncoder())
                .withUser(username).password(password)
                .authorities("USER", "ACTUATOR")
    }

    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http.authorizeRequests()
                .antMatchers("/admin/**").authenticated()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/admin/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler { _, response, authentication ->
                        val token = JwtHelper.encode(authentication.name, MacSigner(apiProperty.jwt.secret)).encoded
                        sendResponse(response, GenericPayload(true, data = token)) }
                    .failureHandler { _, response, e ->
                        sendResponse(response, GenericPayload(false, e.localizedMessage)) }
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/admin/logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessHandler { _, response, _ ->
                        sendResponse(response, GenericPayload(true)) }
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint { _, response, e ->
                        sendResponse(response, GenericPayload(false, e.localizedMessage), status = HttpServletResponse.SC_FORBIDDEN) }
                .and()
                .csrf().disable()
        // @formatter:on
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
    }

    private fun sendResponse(response: HttpServletResponse, payload: GenericPayload, status: Int = HttpServletResponse.SC_OK) {
        response.status = status
        response.addHeader("Content-type", MediaType.APPLICATION_JSON_UTF8.toString())
        response.addHeader("Access-Control-Allow-Origin", apiProperty.admin.url)
        response.addHeader("Access-Control-Allow-Credentials", "true")
        objectMapper.writeValue(response.writer, payload)
    }
}