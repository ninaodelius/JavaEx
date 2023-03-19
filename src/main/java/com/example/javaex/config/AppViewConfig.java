package com.example.javaex.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableWebMvc this gave error
@Configuration
public class AppViewConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        registry.addResourceHandler("/static/**")
                .addResourceLocations("/../../JavaScriptEx/frontend/build/static/");
        registry.addResourceHandler("/*.js")
                .addResourceLocations("/../../JavaScriptEx/frontend/build/");
        registry.addResourceHandler("/*.json")//
                .addResourceLocations("/../../JavaScriptEx/frontend/build/");
        registry.addResourceHandler("/*.ico")
                .addResourceLocations("/../../JavaScriptEx/frontend/build/");
        registry.addResourceHandler("/index.html")//
                .addResourceLocations("/../../JavaScriptEx/frontend/build/index.html");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**");
    }
}
