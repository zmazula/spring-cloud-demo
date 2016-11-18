package com.epam.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
public class ZuulProxyApplication extends SpringBootServletInitializer {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public CookieCsrfTokenRepository cookieCsrfTokenRepository(){
		return new CookieCsrfTokenRepository();
	}

	public static void main(String[] args) {
		SpringApplication.run(ZuulProxyApplication.class, args);
	}
}
