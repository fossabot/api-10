package com.dongfg.project.api.config;

import com.alibaba.fastjson.JSON;
import com.dongfg.project.api.config.property.ApiProperty;
import com.dongfg.project.api.dto.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dongfg
 * @date 18-1-24
 */
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${security.user.name}")
    private String name;

    @Value("${security.user.password}")
    private String password;

    @Autowired
    private ApiProperty apiProperty;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(name).password(password)
                .authorities("ADMIN", "ACTUATOR");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/management/health").permitAll()
                .antMatchers("/management/**", "/admin/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/admin/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler((request, response, authentication) -> {
                        response.setStatus(HttpServletResponse.SC_OK);
                        sendJson(response,JSON.toJSONString(CommonResponse.builder().success(true).build()));
                    })
                    .failureHandler((request, response,e)-> {
                        response.setStatus(HttpServletResponse.SC_OK);
                        sendJson(response,JSON.toJSONString(CommonResponse.builder()
                                    .success(false).msg(e.getLocalizedMessage()).build()));
                    })
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/admin/logout")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessHandler((request,response,authentication)-> {
                        response.setStatus(HttpServletResponse.SC_OK);
                        sendJson(response,JSON.toJSONString(CommonResponse.builder().success(true).build()));
                    })
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint((request,response,e)-> {
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        sendJson(response,JSON.toJSONString(CommonResponse.builder()
                                .success(false).msg(e.getLocalizedMessage()).build()));
                    })
                .and()
                .csrf().disable();
        // @formatter:on
    }

    private void sendJson(HttpServletResponse response, String json) throws IOException {
        response.getWriter().write(json);
        response.addHeader("Content-type", MediaType.APPLICATION_JSON_UTF8.toString());
        response.addHeader("Access-Control-Allow-Origin", apiProperty.getAdminUrl());
        response.addHeader("Access-Control-Allow-Credentials", "true");
    }
}
