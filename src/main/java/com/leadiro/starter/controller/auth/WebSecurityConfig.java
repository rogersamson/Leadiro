package com.leadiro.starter.controller.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired private JwtBearerTokenAuthenticationFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.debug("Configuring web security");
        http
            //Disable CSRF
            .csrf().disable()
            //Disable sessions
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            //Add the custom JWT filter
            .addFilterBefore(jwtFilter, BasicAuthenticationFilter.class)
            //Configure URLs
            .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll() //Allow all CORS OPTIONS request
                .antMatchers("/public/**").permitAll() //Allow access to URLS marked as public
                .antMatchers("/ping", "/health", "/starter/health").permitAll() //Allow access to the health pings
                .antMatchers("/index.html", "/img/*.png", "/js/*.js", "/css/*.css").permitAll() //Allow access static Vue resources
                .anyRequest().authenticated(); //Force auth for everything else
    }

}