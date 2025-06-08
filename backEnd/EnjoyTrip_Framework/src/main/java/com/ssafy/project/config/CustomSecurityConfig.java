package com.ssafy.project.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import com.ssafy.project.model.service.UserService;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class CustomSecurityConfig {
	private final CustomUserDetailsService userDetailsService;
	private final UserService userService;

    @Bean
    @Order(2) // APISecurityConfig를 먼저 적용하기 위해서 우선순위 조정
    SecurityFilterChain securityFilterChain(HttpSecurity http, @Qualifier("corsConfigurationSource") CorsConfigurationSource corsConfig) throws Exception {
        http.cors(css -> css.configurationSource(corsConfig))
                .authorizeHttpRequests(authorize -> authorize
                		.requestMatchers("/api/user/login", "/api/user/logout", "/api/user/regist", "/api/user/refresh").permitAll()   // 로그인은 인증 필요 없음
                        .requestMatchers("/api/**").authenticated() // api는 인증만 필요
                        .anyRequest().permitAll()); // 이외의 경로는 그냥 통과

        http.csrf(customizer -> customizer.disable()); // csrf는 비활성
        // http.formLogin(Customizer.withDefaults()); // 기본 form login 활용
//        http.formLogin(customizer -> customizer
//        		.loginPage("/user/login-form")
//                .loginProcessingUrl("/api/user/login")
//	            .usernameParameter("id")        // 사용자의 name 속성이 id일 경우
//	            .passwordParameter("password")  // 이건 기본값이라 안 써도 되지만 명시하면 명확함
//                .successHandler((req, res, auth) -> {
//                	System.out.println("?????");
//                    String rememberMe = req.getParameter("remember-me");
//                    Cookie loginIdCookie = new Cookie("loginId", "bye");
//                    if (rememberMe != null) {
//                        loginIdCookie.setValue(auth.getName());
//                        loginIdCookie.setMaxAge(60 * 60 * 24 * 365);
//                    } else {
//                        loginIdCookie.setMaxAge(0);
//                    }
//                    res.addCookie(loginIdCookie);
//                    res.sendRedirect(req.getContextPath() + "/");
//                })
//                .usernameParameter("id")
//                .failureUrl("/user/login-form?error")
//                .permitAll());
        //http.logout(Customizer.withDefaults());    // 기본 logout 활용
        http.logout(customizer -> customizer.logoutUrl("/api/user/logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID", "remember-me"));
        //http.rememberMe(Customizer.withDefaults());
        http.rememberMe((customizer) -> customizer.tokenValiditySeconds(60 * 1));
        return http.build();
    }
    
	@Bean
	public PasswordEncoder passwordEncoder() {
		// 비밀번호 암호화
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		userService.setPasswordEncoder(encoder); // 수동 주입
		return encoder;
	}
}
