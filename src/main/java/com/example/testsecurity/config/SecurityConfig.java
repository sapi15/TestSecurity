package com.example.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity                              // Security 에서 접근 가능하도록 해주는 역할(?)
public class SecurityConfig {

    /**
     * 암호화.
     * spring security는 사용자 인증(로그인)시에 비밀번호에 단방향(해독불가) 해시 암호화를 진행하여 비밀번호 대조를 한다.
     * 따라서 아래 설정으로 회원가입시 비밀번호 암호화가 진행된다.
     *
     * spring security에서 권장하는 암호화는 BCryptPasswordEncoder
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Security 설정.
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .authorizeHttpRequests((auth) -> auth                                                            // spring boot 3.1.x / spring security 6.x 부터는 람다식 필수.
                        .requestMatchers("/", "/login", "/join", "/joinProc").permitAll()                 // 접근권한 체크는 여기 상단부터 시작되기때문에 순서 유의해야 함.
                        .requestMatchers("/admin").hasRole("ADMIN")                                             // admin url은 ADMIN 권한자만 접근가능.
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()                                   // 위에 설정한 url 이외의 나머지는 로그인 후에 접근가능하도록.
                );

        httpSecurity
                .formLogin((auth) -> auth.loginPage("/login")							// 로그인 페이지 경로. 로그인 페이지를 지정해야, .requestMatchers().permitAll() 에 허용되지 않은 url로 접근 시도 할때, 403에러가 발생하지 않는다.
                        .loginProcessingUrl("/loginProc")							    // view에서 로그인 form action에 설정된 url 입력. (security에서 사용할 data를 전달해주는 역할?)
                        .permitAll()												    // 로그인 페이지에 모든 url 접근 허용.
                );

//        httpSecurity
//                .csrf((auth) -> auth.disable());										// security에는 기본적으로 csrf필터가 작동되어져 있다.(post요청을 할때 csrf 토큰을 필요로 한다.) 개발때는 편리성을 위해 disable() 처리.


        httpSecurity
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)                                             // 하나의 아이디에 대한 다중 로그인 허용 개수
                        .maxSessionsPreventsLogin(true)                                 //  다중 로그인 개수를 초과하였을 경우 처리 방법. true : 초과시 새로운 로그인 차단, false : 초과시 기존 세션 하나 삭제
                );

        /**
         * - sessionManagement().sessionFixation().none() : 로그인 시 세션 정보 변경 안함
         * - sessionManagement().sessionFixation().newSession() : 로그인 시 세션 새로 생성
         * - sessionManagement().sessionFixation().changeSessionId() : 로그인 시 동일한 세션에 대한 id 변경
         */
//        httpSecurity
//                .sessionManagement((session) -> session
//                        .sessionFixation((sessionFixation) -> sessionFixation
//                                .newSession()
//                        )
//                );

        httpSecurity
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId()
                );


        httpSecurity
                .logout((auth) -> auth.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );


        return httpSecurity.build();
    }





}
