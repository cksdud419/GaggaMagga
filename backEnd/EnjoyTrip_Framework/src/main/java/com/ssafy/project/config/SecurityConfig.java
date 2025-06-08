package com.ssafy.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ssafy.project.model.dto.User;
import com.ssafy.project.model.service.UserService;
import com.ssafy.project.util.CustomUserDetails;

import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;

//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
public class SecurityConfig {
//	private final CustomUserDetailsService userDetailsService;
//	private final UserService userService;
//	
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//		http
//		.csrf().disable()
//			.formLogin(form -> form
//					.loginPage("/user/login")
//		            .usernameParameter("id")        // 사용자의 name 속성이 id일 경우
//		            .passwordParameter("password")  // 이건 기본값이라 안 써도 되지만 명시하면 명확함
//					.successHandler((request, response, authentication) -> {
//						CustomUserDetails loginUserDetail = (CustomUserDetails) authentication.getPrincipal();
//						User loginUser = loginUserDetail.getUser();
//						String remember = (String) request.getParameter("remember");
//							if (remember != null) {
//								// 쿠키에 현재 들어온 id를 저장
//								System.out.println("쿠키 새로 저장");
//								Cookie cookie = new Cookie("remember", loginUser.getId());
//								cookie.setPath("/");
//								cookie.setMaxAge(60 * 60 * 24 * 30);
//								response.addCookie(cookie);
//							} else {
//								// remember 쿠기 제거
//								Cookie cookie = new Cookie("remember", "");
//								cookie.setPath("/");
//								cookie.setMaxAge(0);
//								response.addCookie(cookie);
//							}
//						response.sendRedirect("/");
//					})
//			)
//			.logout(logout -> logout
//					.logoutUrl("/user/logout")
//					.logoutSuccessUrl("/user/login")
//			)
//			.authorizeHttpRequests((auth) -> auth
//					.requestMatchers("/user/login").anonymous()
//					.requestMatchers(HttpMethod.POST, "/user/regist").permitAll()
//					.requestMatchers("/",
//							"/css/**", 
//							"/js/**", 
//							"/img/**", 
//							"/WEB-INF/**", 
//							"/user/regist"
//							).permitAll()
//					.anyRequest().authenticated()
//			)
//	        .exceptionHandling(exception -> exception
//	                .accessDeniedPage("/"))  // 접근이 거부된 경우 리디렉션 (예: 로그인 상태에서 로그인 페이지로 접근)
//			.userDetailsService(userDetailsService)
//			.sessionManagement()
//	        .invalidSessionUrl("/user/login"); // 세션 만료 시 로그인 페이지로 리다이렉트
//		return http.build();
//	}
//	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		// 비밀번호 암호화
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        userService.setPasswordEncoder(encoder); // 수동 주입
//        return encoder;
//	}
}
