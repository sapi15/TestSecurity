package com.example.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .authorizeHttpRequests((auth) -> auth                                   // spring security 6.x 부터는 람다식으로 써야 한다.
                        .requestMatchers("/", "/login").permitAll()                 // 접근권한 체크는 여기 상단부터 시작되기때문에 순서 유의해야 함.
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()                                   // 위에 설정한 url 이외의 나머지는 로그인 후에 접근가능하도록.
                );


        return httpSecurity.build();
    }





}
