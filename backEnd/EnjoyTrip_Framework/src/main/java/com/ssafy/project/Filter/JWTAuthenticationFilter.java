package com.ssafy.project.Filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.project.model.dto.User;
import com.ssafy.project.model.service.UserService;
import com.ssafy.project.util.CustomUserDetails;
import com.ssafy.project.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final UserService userService;
	private final JWTUtil jwtUtil;

	// 조상의 생성자를 호출하기 위해 @RequestArgsConstructor를 사용하지 않고 Autowired를 사용
	public JWTAuthenticationFilter(AuthenticationManager authManager, UserService userService, JWTUtil jwtUtil) {
		super(authManager);
		this.userService = userService;
		this.jwtUtil = jwtUtil;
		this.setFilterProcessesUrl("/api/user/login");
		this.setUsernameParameter("id");
	}

	@Override
	// 로그인 성공 시 실행하는 메소드: JWT 토큰 발행 → response를 통해 json으로 토큰 전달
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) {
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		String accessToken = jwtUtil.createAccessToken(userDetails.getUser());
		
		// refresh token 생성 및 member정보 수정
		String refreshToken = jwtUtil.createRefreshToken(userDetails.getUser());
		User user = userDetails.getUser();
		user.setRefresh(refreshToken);
		userService.updateUser(user);

		Map<String, String> result = new HashMap<>();
		result.put("status", "SUCCESS");
		result.put("accessToken", accessToken);

		// 추가 전달
		result.put("refreshToken", refreshToken);
		
		handleResult(response, result, HttpStatus.OK);
	}

	@Override
	// 로그인 실패 시 실행하는 메소드: response를 통해 json으로 실패 내용 전달
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) {
		System.out.println("UnsuccessFulAuthentication: 실패");
		throw failed; // 예외처리는 한 곳에서 처리하도록 전달
	}

	private void handleResult(HttpServletResponse response, Map<String, String> data, HttpStatus status) {
		response.setContentType("application/json;charset=UTF-8");
		try {
			String jsonResponse = new ObjectMapper().writeValueAsString(data);
			response.setStatus(status.value());
			response.getWriter().write(jsonResponse);
		} catch (IOException e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}
}