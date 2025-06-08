package com.ssafy.project.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ssafy.project.Filter.JWTAuthenticationFilter;
import com.ssafy.project.Filter.JWTVerificationFilter;
import com.ssafy.project.Filter.SecurityExceptionHandlingFilter;
import com.ssafy.project.model.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class APISecurityConfig {
	@Bean
	// Spring Security 5.4 이상에서 AuthenticationManager를 Bean으로 사용하려면 명시적 등록 필요
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {

		return authConfig.getAuthenticationManager();
	}

	@Bean
	@Order(1) // Order 낮을수록 우선순위 높고, 생략하면 가장 낮음
	public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http, CustomUserDetailsService userDetailsService,
			JWTAuthenticationFilter authFilter, JWTVerificationFilter jwtVerifyFilter,
			SecurityExceptionHandlingFilter exceptionFilter,
			@Qualifier("corsConfigurationSource") CorsConfigurationSource corsConfig) throws Exception {
		http.securityMatcher("/api/**") // SecurityFilterChain의 동작범위 설정
				.cors(cors -> cors.configurationSource(corsConfig)).csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/etc/**", "/api/auth/**", "/api/user/**")
				.permitAll().anyRequest().authenticated());

		http.addFilterBefore(jwtVerifyFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterAt(authFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(exceptionFilter, JWTVerificationFilter.class);

		return http.build();
	}

	// Spring Security는 filter에서 동작하므로 filter에서 CORS처리 권장
	// WebMvcConfigurer에서의 설정은 @Controller에서의 처리
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		
		configuration.setAllowCredentials(false); // 명시적으로 false로 해줘야 * 이 허용됨

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/api/**", configuration);
		// source.registerCorsConfiguration("/user/checkId", configuration);
		return source;
	}
}
