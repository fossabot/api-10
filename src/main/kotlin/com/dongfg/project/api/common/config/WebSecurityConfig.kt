package com.dongfg.project.api.common.config

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

/**
 * @author dongfg
 * @date 2018/3/17
 */
@EnableWebSecurity
@Configuration
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/admin/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
                .antMatchers("/webhooks/**")
                .antMatchers("/rest")
                .antMatchers("/graphql")
                .antMatchers("/graphiql")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
    }
}