package com.ssafy.project.util;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.project.model.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {
// key는 랜덤으로 설정할 것을 권장함
private final SecretKey key;
	public JWTUtil() {
		key = Jwts.SIG.HS256.key().build();
	}
	@Value("${ssafy.jwt.access-expmin}") private long accessExpMin;
	@Value("${ssafy.jwt.refresh-expmin}") private long refreshExpMin;

	public String createAccessToken(User user) {
		return create("accessToken", accessExpMin,
			Map.of("email", user.getEmail(), "id", user.getId()));
	}

	public String createRefreshToken(User member) {
		return create("refreshToken", refreshExpMin, Map.of("id", member.getId()));
	}

	public String create(String subject, long expireMin, Map<String, Object> claims) {
		Date expireDate = new Date(System.currentTimeMillis() + 1000 * 60 * expireMin);
		String jwt = Jwts.builder()
				.subject(subject)		// 제목 설정
				.claims(claims)		// claim(key-value 쌍)추가
				.expiration(expireDate)	// 만료일 설정
				.signWith(key)			//서명 알고리즘 설정
				.compact();			// 직렬화 처리
		return jwt;
	}


	// 발생 가능한 예외
	// ExpireJwtException
		// 형식은 적합하나, 토큰의 유효기간이 지난 경우
	// MalformedJwtException
		// 형식이 잘못된 토큰을 이용하려는 경우
	// SignatureException
		// 훼손된 토큰을 이용하려는 경우
	public Map<String, Object> getClaims(String jwt) {
		// 다시 한번 서명하기
		JwtParser parser = Jwts.parser()
					.verifyWith(key)
					.build();
		Jws<Claims> jws = parser.parseSignedClaims(jwt);

		return jws.getPayload();
	}
}