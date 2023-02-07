package com.example.javaex.config;

import com.example.javaex.user.UserModelDetailsService;
import com.example.javaex.user.workout.WorkoutModelDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("!https")
public class AppSecurityConfig {

    private final UserModelDetailsService userModelDetailsService;
    private final WorkoutModelDetailsService workoutModelDetailsService;
    private final AppPasswordConfig appPasswordConfig;

    public AppSecurityConfig(UserModelDetailsService userModelDetailsService, WorkoutModelDetailsService workoutModelDetailsService, AppPasswordConfig appPasswordConfig) {
        this.userModelDetailsService = userModelDetailsService;
        this.workoutModelDetailsService = workoutModelDetailsService;
        this.appPasswordConfig = appPasswordConfig;
    }


    @Bean
    public SecurityFilterChain securityFilterchain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        HttpMethod.GET,
                        "/index*", "/static/**", "/*.js", "/*.json", "/*.ico")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/index.html")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/homepage.html",true)
                .failureUrl("/index.html?error=true");
        return http.build();
    }
}
