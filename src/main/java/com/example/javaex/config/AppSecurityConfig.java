package com.example.javaex.config;

import com.example.javaex.user.UserModelDetailsService;
import com.example.javaex.user.workout.WorkoutModelDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
//@Profile("!https")
public class AppSecurityConfig {

    private final UserModelDetailsService userModelDetailsService;
    private final WorkoutModelDetailsService workoutModelDetailsService;
    private final AppPasswordConfig appPasswordConfig;

    @Autowired
    public AppSecurityConfig(UserModelDetailsService userModelDetailsService, WorkoutModelDetailsService workoutModelDetailsService, AppPasswordConfig appPasswordConfig) {
        this.userModelDetailsService = userModelDetailsService;
        this.workoutModelDetailsService = workoutModelDetailsService;
        this.appPasswordConfig = appPasswordConfig;
    }

    //här lösa cors-problem som kommer från frontend


    @Bean
    public SecurityFilterChain securityFilterchain(HttpSecurity http) throws Exception{
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedOriginPatterns(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        http
                .csrf().disable()
                .authorizeHttpRequests( requests -> {
                            try {
                                requests.requestMatchers("/index*", "/static/**", "/*.js", "/*.json", "/*.ico", "/", "/api", "/save", "/test", "/signin", "/api**", "/**").permitAll()
                                        .requestMatchers("/home","/perform_signin").hasRole("USER")
                                        .anyRequest()
                                        .authenticated()
                                        .and().csrf().disable().cors().configurationSource(request -> corsConfiguration);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                )
                .formLogin( formlogin -> {
                    formlogin.loginPage("/signin")
                            .loginProcessingUrl("/perform_signin")
                            .defaultSuccessUrl("/home",true)
                            .failureUrl("/signin?error=true")
                    ;
                }
                )
                .rememberMe( rememberMe -> {
                            rememberMe.rememberMeParameter("remember-me-token")
                                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) // 3 weeks
                                    .key("SomeSecureKey")
                                    .userDetailsService(userModelDetailsService);
                        }
                )
                .logout( logout ->{
                            logout.logoutUrl("/logout")
                                    .logoutSuccessUrl("/")
                                    .invalidateHttpSession(true)
                                    .clearAuthentication(true)
                                    .deleteCookies("remember-me-token", "JSESSIONID");
                        }
                )
                .authenticationProvider(authenticationOverride());
        return http.build();
    }

    @Autowired
    public DaoAuthenticationProvider authenticationOverride(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userModelDetailsService);
        provider.setPasswordEncoder(appPasswordConfig.bCryptPassword());
        return provider;
    }
}
