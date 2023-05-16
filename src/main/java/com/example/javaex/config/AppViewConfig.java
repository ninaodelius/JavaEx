package com.example.javaex.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@EnableWebMvc /** this gave error when using react frontend, comment out this line if using react frontend **/
@Configuration
public class AppViewConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){

        /** thymeleaf **/
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");


        /** react **/
        /*registry.addResourceHandler("/static/**")
                .addResourceLocations("/../../JavaScriptEx/frontend/build/static/");
        registry.addResourceHandler("/*.js")
                .addResourceLocations("/../../JavaScriptEx/frontend/build/");
        registry.addResourceHandler("/*.json")//
                .addResourceLocations("/../../JavaScriptEx/frontend/build/");
        registry.addResourceHandler("/*.ico")
                .addResourceLocations("/../../JavaScriptEx/frontend/build/");
        registry.addResourceHandler("/index.html")//
                .addResourceLocations("/../../JavaScriptEx/frontend/build/index.html");*/

    }

    /** react **/
    /*@Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
*/
    /** thymeleaf **/
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("startpage");
        registry.addViewController("/signup").setViewName("signup");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/user").setViewName("userpage");
        registry.addViewController("/admin").setViewName("adminpage");
        registry.addViewController("/logout").setViewName("logout");


    }

}
