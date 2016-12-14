package com.epam.demo.security;

import org.apache.commons.collections.map.MultiValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

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

    public class UserDetailsContextMapperImpl extends LdapUserDetailsMapper {

        @Override
        public UserDetails mapUserFromContext(DirContextOperations ctx, String username,Collection<? extends GrantedAuthority> authorities) {

            UserDetails userDetails= super.mapUserFromContext(ctx,username,authorities);
            ExtendedUserDetailsAdapter userDetailsAdapter = new ExtendedUserDetailsAdapter(userDetails);
            userDetailsAdapter.setAttribute("title", ctx.getStringAttribute("title"));
            userDetailsAdapter.setAttribute("mail", ctx.getStringAttribute("mail"));
            userDetailsAdapter.setAttribute("displayName", ctx.getStringAttribute("displayName"));
            userDetailsAdapter.setAttributes("memberOf", ctx.getStringAttributes("memberOf"));
            userDetailsAdapter.setAttribute("physicalDeliveryOfficeName", ctx.getStringAttribute("physicalDeliveryOfficeName"));

            return userDetailsAdapter;
        }
    }

    public class ExtendedUserDetailsAdapter implements UserDetails {

        UserDetails userDetails;
        Map<String, String> ldapAttributes = new MultiValueMap();

        public ExtendedUserDetailsAdapter(UserDetails userDetails) {
            this.userDetails = userDetails;
        }

        public void setAttribute(String key, String value) {
            ldapAttributes.put(key, value);
        }

        public void setAttributes(String key, String[] values) {
            Stream.of(values).forEach(s -> ldapAttributes.put(key, s));
        }

        public String getAttribute(String key) {
            return ldapAttributes.get(key);
        }

        public Map<String, String> getAttributes() {
            return ldapAttributes;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return userDetails.getAuthorities();
        }

        @Override
        public String getPassword() {
            return userDetails.getPassword();
        }

        @Override
        public String getUsername() {
            return userDetails.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return userDetails.isAccountNonExpired();
        }

        @Override
        public boolean isAccountNonLocked() {
            return userDetails.isAccountNonLocked();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return userDetails.isCredentialsNonExpired();
        }

        @Override
        public boolean isEnabled() {
            return userDetails.isEnabled();
        }
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        switch (authenticationProvider) {
            case "ldap":
                auth
                    .ldapAuthentication()
                    .userSearchFilter("("+ldapUserSearchField+"={0})")
                    .userSearchBase(ldapUserSearchBase)
                    .groupSearchBase(ldapGroupSearchBase)
                    .userDetailsContextMapper(new UserDetailsContextMapperImpl())
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

