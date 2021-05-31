package com.example.bq.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:index.html");
        registry.addViewController("/index").setViewName("forward:index.html");
        registry.addViewController("/accueil").setViewName("forward:accueil.html");
        registry.addViewController("/login").setViewName("forward:login.html");
    }

}
