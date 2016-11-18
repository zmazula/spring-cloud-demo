//package com.epam.demo.mvc;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
///**
// * Created by zmazula on 11/11/16.
// */
//@Configuration
//@EnableWebMvc
//public class ExternalStaticFilesWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter{
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**")
//                .addResourceLocations("file:///Users/zmazula/work/intelij_projects/demo/zuul-proxy/src/main/web/").setCachePeriod(0);
//    }
//
//}
//
