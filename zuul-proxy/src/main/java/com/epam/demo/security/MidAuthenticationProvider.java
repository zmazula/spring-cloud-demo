package com.epam.demo.security;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zmazula on 09/11/16.
 */
@Component
public class MidAuthenticationProvider implements AuthenticationProvider {

    private final ParameterizedTypeReference<String> STRING_RESPONSE = new ParameterizedTypeReference<String>() {};

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${auth-service-name}")
    String authServiceName;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (isAuthWithRestSuccess(username, password)) {
            return createAuthObject(username, password);
        }
        return null;
    }

    private Authentication createAuthObject(String username, String password) {
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
        return auth;
    }

    private boolean isAuthWithRestSuccess(String username, String password) {
        URI authUri = discoveryClient.getInstances(authServiceName).get(0).getUri();
        URIBuilder uriBuilder = new URIBuilder(authUri);
        uriBuilder.setPath("/authenticate");
        uriBuilder.addParameter("username", username);
        uriBuilder.addParameter("password", password);

        try {
            RequestEntity<Void> requestEntityGet = RequestEntity.get(uriBuilder.build()).build();
            ResponseEntity<String> responseEntity = this.restTemplate.exchange(requestEntityGet, STRING_RESPONSE);

            return Boolean.valueOf(responseEntity.getBody());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
