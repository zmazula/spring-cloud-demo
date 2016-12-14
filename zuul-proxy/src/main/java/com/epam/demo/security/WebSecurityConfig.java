package com.epam.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * Created by zmazula on 08/11/16.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${authentication-provider}")
    private String authenticationProvider;

    @Value("${ldap-user-search-field}")
    private String ldapUserSearchField;

    @Value("${ldap-user-search-base}")
    private String ldapUserSearchBase;

    @Value("${ldap-group-search-base}")
    private String ldapGroupSearchBase;

    @Value("${ldap-url}")
    private String ldapUrl;

    @Value("${ldap-port}")
    private int ldapPort;

    @Value("${ldap-manager-dn}")
    private String ldapManagerDn;

    @Value("${ldap-manager-password}")
    private String ldapManagerPassword;

    @Autowired
    MidAuthenticationProvider midAuthenticationProvider;

    @Autowired
    CookieCsrfTokenRepository cookieCsrfTokenRepository;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        switch (authenticationProvider) {
            case "ldap":
                auth
                    .ldapAuthentication()
                    .userSearchFilter("("+ldapUserSearchField+"={0})")
                    .userSearchBase(ldapUserSearchBase)
                    .groupSearchBase(ldapGroupSearchBase)
                    .contextSource()
                    .url(ldapUrl)
                    .port(ldapPort)
                    .managerDn(ldapManagerDn)
                    .managerPassword(ldapManagerPassword);
                break;
            default:
                auth.authenticationProvider(midAuthenticationProvider);
                break;
        }
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

