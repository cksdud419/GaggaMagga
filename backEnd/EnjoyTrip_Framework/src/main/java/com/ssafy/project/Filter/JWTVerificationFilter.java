package com.ssafy.project.Filter;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.project.config.CustomUserDetailsService;
import com.ssafy.project.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTVerificationFilter extends OncePerRequestFilter {
	private final JWTUtil jwtUtil;
	private final CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 1. 토큰 검증
		// 2. valid한 경우 신뢰 → claim으로 사용자 정보 조회
		// 3. 사용자 정보를 SecurityContextHolder에 저장

		// 토큰에서 claim 정보 획득 - 별 탈 없다면 신뢰할만한 사용자고, 토큰이 없다면 인증하지 않는다.
		String token = extractToken(request);
		if (token == null) {
			filterChain.doFilter(request, response);
			return;
		}

		Map<String, Object> claims = jwtUtil.getClaims(token);
		// 실제 사용자 정보 조회
		UserDetails userDetails = userDetailsService.loadUserByUsername(claims.get("id").toString());
		// Authentication 생성 및 SecurityContextHolder에 저장 - Controller 등에서 사용하기 위함

		var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// 다음 filter로
		filterChain.doFilter(request, response);
	}

	private String extractToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		System.out.println("extractToken: " + bearerToken);
		// 0Auth 2.0에서 Basic, Digest등 다른 인증 방식과 구분하기 위한 값
		// 문자열 공백도 의도된 것이라 빼면 안됨 (헐)
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		} else {
			return null;
		}
	}
}