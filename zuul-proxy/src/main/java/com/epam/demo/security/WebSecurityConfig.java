package com.epam.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * Created by zmazula on 08/11/16.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MidAuthenticationProvider midAuthenticationProvider;

    @Autowired
    CookieCsrfTokenRepository cookieCsrfTokenRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(midAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        cookieCsrfTokenRepository.setCookieHttpOnly(false);
        http.csrf().csrfTokenRepository(cookieCsrfTokenRepository);
        http.authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/mid_service/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login.jsp").permitAll();
    }

}
