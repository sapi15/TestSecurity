package com.example.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity                              // Security 에서 접근 가능하도록 해주는 역할(?)
public class SecurityConfig {
	
	
	
	
	// 인증 실패 시 처리를 담당하는 핸들러를 생성하는 메서드
		@Bean
		public AuthenticationFailureHandler authenticationFailureHandler() {
			return new SimpleUrlAuthenticationFailureHandler("/login?error"); // 실패 시 /login 페이지로 리다이렉트하며 실패 메시지 전달
		}

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

//        httpSecurity
//                .authorizeHttpRequests((auth) -> auth                                                            // spring boot 3.1.x / spring security 6.x 부터는 람다식 필수.
//                        .requestMatchers("/", "/login/**", "/join", "/joinProc").permitAll()                 		// 접근권한 체크는 여기 상단부터 시작되기때문에 순서 유의해야 함.
//                        .requestMatchers("/admin").hasRole("ADMIN")                                             // admin url은 ADMIN 권한자만 접근가능.
//                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
//                        .anyRequest().authenticated()                                   						// 위에 설정한 url 이외의 나머지는 로그인 후에 접근가능하도록.
//                );

        httpSecurity
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login/**", "/join", "/joinProc", "/api/message").permitAll()
                        .requestMatchers("/user/**").hasAnyRole("USER")
                        .requestMatchers("/manager/**").hasAnyRole("MANAGER")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                );

        httpSecurity
                .formLogin((auth) -> auth.loginPage("/login/page.do")							// 로그인 페이지 경로. 로그인 페이지를 지정해야, .requestMatchers().permitAll() 에 허용되지 않은 url로 접근 시도 할때, 403에러가 발생하지 않는다.
                		.usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)									// 로그인 성공 시 기본 URL 설정
                		.loginProcessingUrl("/loginProc")							    // view에서 로그인 form action에 설정된 url 입력. (security에서 사용할 data를 전달해주는 역할?)
                        .permitAll()												    // 로그인 페이지에 모든 url 접근 허용.
                        .failureHandler(authenticationFailureHandler())
                );

//        httpSecurity
//                .httpBasic(Customizer.withDefaults());                                  // HTTP 인증 헤더에 부착하여 서버측으로 요청을 보내는 방식.
        

        httpSecurity
                .csrf((auth) -> auth.disable());										// security에는 기본적으로 csrf필터가 작동되어져 있다.(post요청을 할때 csrf 토큰을 필요로 한다.) 개발때는 편리성을 위해 disable() 처리.


//        httpSecurity
//                .sessionManagement((auth) -> auth
//                        .maximumSessions(1)                                             // 하나의 아이디에 대한 다중 로그인 허용 개수
//                        .maxSessionsPreventsLogin(true)                                 //  다중 로그인 개수를 초과하였을 경우 처리 방법. true : 초과시 새로운 로그인 차단, false : 초과시 기존 세션 하나 삭제
//                );

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

//        httpSecurity
//                .sessionManagement((auth) -> auth
//                        .sessionFixation().changeSessionId()
//                );


//        httpSecurity
//                .logout((auth) -> auth.logoutUrl("/logout")
//                        .logoutSuccessUrl("/")
//                );


        return httpSecurity.build();
    }


    /**
     * InMemory 유저 정보 저장
     *
     * 토이 프로젝트를 진행하는 경우 또는
     * 시큐리티 로그인 환경이 필요하지만 소수의 회원 정보만 가지며 데이터베이스라는 자원을 투자하기 힘든 경우는
     * 회원가입 없는 InMemory 방식으로 유저를 저장하면 된다.
     *
     * DB연결 안되어져 있고, 로그인 페이지 정도와, BCrypt 암호화 정도까지만 구현 되어져 있어야 작동되는 듯 하다.
     * @return
     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user1 = User.builder()
//                .username("user1")
//                .password(bCryptPasswordEncoder().encode("1234"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user2 = User.builder()
//                .username("user2")
//                .password(bCryptPasswordEncoder().encode("1234"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }


    /**
     * 계층 권한 설정.
     *
     * 권한을 추가할땐, 앞 권한에 \n도 붙여 준다.
     * @return
     */
    @Bean
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGER\n" +
                                "ROLE_MANAGER > ROLE_USER");

        return hierarchy;
    }




}
