package com.example.javaex.config;

import com.example.javaex.user.UserModelDetailsService;
import com.example.javaex.user.workout.WorkoutModelDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
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
        http
                .csrf().disable()
                .authorizeHttpRequests( requests -> {
                    requests.requestMatchers("/index*", "/static/**", "/*.js", "/*.json", "/*.ico").permitAll()
                            .anyRequest()
                            .authenticated()
                            ;
                }
                )
                .formLogin( formlogin -> {
                    formlogin.loginPage("/index.html")
                            .loginProcessingUrl("/perform_login")
                            .defaultSuccessUrl("/homepage.html",true)
                            .failureUrl("/index.html?error=true")
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
                .authenticationProvider(authenticationOverride());;
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
