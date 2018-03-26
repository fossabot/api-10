package com.dongfg.project.api.common.config

import com.dongfg.project.api.web.payload.GenericPayload
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import javax.servlet.http.HttpServletResponse

/**
 * @author dongfg
 * @date 2018/3/17
 */
@EnableWebSecurity
@Configuration
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.inMemoryAuthentication()
                .withUser("").password("")
                .authorities("", "")
    }

    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/admin/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/admin/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler { _, response, _ ->
                        sendResponse(response, GenericPayload(true)) }
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
                        sendResponse(response, GenericPayload(false, e.localizedMessage)) }
                .and()
                .csrf().disable()
        // @formatter:on
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
                .antMatchers("/webhooks/**")
                .antMatchers("/rest")
                .antMatchers("/graphql")
                .antMatchers("/graphiql")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
    }

    private fun sendResponse(response: HttpServletResponse, payload: GenericPayload) {
        response.status = HttpServletResponse.SC_OK
        response.addHeader("Content-type", MediaType.APPLICATION_JSON_UTF8.toString())
        response.addHeader("Access-Control-Allow-Origin", "*")
        response.addHeader("Access-Control-Allow-Credentials", "true")
        objectMapper.writeValue(response.writer, payload)
    }
}