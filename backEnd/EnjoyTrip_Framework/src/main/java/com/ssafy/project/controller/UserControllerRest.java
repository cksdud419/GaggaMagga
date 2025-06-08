package com.ssafy.project.controller;

import com.ssafy.project.model.dto.ResultDTO;
import com.ssafy.project.model.dto.User;
import com.ssafy.project.model.service.UserService;
import com.ssafy.project.util.CustomUserDetails;
import com.ssafy.project.util.JWTUtil;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserControllerRest {

	private final JWTUtil jwtUtil;
	private final UserService userService;
    
	// 회원가입 처리
	@PostMapping
	public ResponseEntity<ResultDTO<Boolean>> regist(@RequestBody User user) {
		System.out.println("회원가입 시도: "+ user);
		int result = userService.insertUser(user);
		if (result > 0) {
			System.out.println("회원가입 성공");
			return ResponseEntity.ok(new ResultDTO<Boolean>("정상 회원가입 완료!", true));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO<>("동일한 id혹은 email이 있습니다.", false));
		}
	}

	// 유저 정보 데이터 Rest
	@GetMapping("/{id}")
	public ResponseEntity<ResultDTO<User>> info(@PathVariable String id){
		User user = null;
		if(id.contains("@")) {
			user = userService.selectByEmail(id);
		}else {
			user = userService.selectById(id);
		}
		
		if(user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO<>(id+" 유저를 찾을 수 없습니다!", null));
		}
		// 보안을 위해 비밀번호는 가져오지 않음
		user.setPassword("");
		return ResponseEntity.ok(new ResultDTO<User>("유저를 정상적으로 불러왔습니다.", user));
	}
	
    @GetMapping("/me")
    public ResponseEntity<ResultDTO<User>> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResultDTO<>("유저를 찾을 수 없습니다!", null));
        }

        User dto = userDetails.getUser();
        return ResponseEntity.ok(new ResultDTO<User>("유저를 정상적으로 불러왔습니다.", dto));
    }

	// 유저 삭제 처리
	@DeleteMapping("/{id}")
	public ResponseEntity<ResultDTO<Boolean>> deleteUser(@PathVariable String id) {
		int result = userService.deleteUser(id);
		if (result > 0) {
			return ResponseEntity.ok(new ResultDTO<Boolean>("정상 탈퇴 완료!", true));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO<>(id+" 유저를 찾을 수 없습니다!", false));
		}
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<ResultDTO<Map<String, String>>> refreshAccessToken(@RequestHeader("Refresh-Token") String refreshToken) {
		System.out.println("refresh Api 실행");
		if(refreshToken == null || refreshToken.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO<>("refresh토큰이 없습니다!", null));
		}

		Map<String, Object> claims = jwtUtil.getClaims(refreshToken);
		String id = (String) claims.get("id");
		if (id == null) {
			throw new JwtException("Invalid refresh token: email claim missing");
		}

		User user = userService.selectById(id);
		if (user == null || user.getRefresh() == null || !user.getRefresh().equals(refreshToken)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResultDTO<>("refresh토큰이 만료되었습니다!", null));
		}

		String newAccessToken = jwtUtil.createAccessToken(user);
		String newRefreshToken = jwtUtil.createRefreshToken(user);
		user.setRefresh(newRefreshToken);
		userService.updateUser(user);
		
		System.out.println("refresh api 성공");
		return ResponseEntity.ok(new ResultDTO<>("토큰 갱신 성공!", Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken)));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestHeader("Refresh-Token") String refreshToken) {
		System.out.println("logout: "+refreshToken);
		if (refreshToken == null || refreshToken.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Refresh token is required"));
		}

		Map<String, Object> claims = jwtUtil.getClaims(refreshToken);
		String id = (String) claims.get("id"); // Refresh Token 생성 시 넣었던 클레임 키
		if (id == null) {
			throw new JwtException("Invalid refresh token: id claim missing");
		}
		
		System.out.println(id);
		User user = userService.selectById(id);
		user.setRefresh(null);
		userService.updateUser(user);
		return ResponseEntity.ok(new ResultDTO<>("로그아웃 성공!", Map.of("accessToken", "", "refreshToken", "")));
	}
}