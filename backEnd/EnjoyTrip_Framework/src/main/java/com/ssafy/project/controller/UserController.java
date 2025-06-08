package com.ssafy.project.controller;

import com.ssafy.project.model.dto.ResultDTO;
import com.ssafy.project.model.dto.User;
import com.ssafy.project.model.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	// 로그인 페이지
	@GetMapping("/login")
	public String loginPage() {
		return "user/login";
	}

	// 로그인 처리
	// Spring Security로 위임
//	@PostMapping("/login")
//	public String login(@RequestParam String id, @RequestParam String password, @RequestParam(required = false) String remember,
//			RedirectAttributes redirectAttributes, HttpServletResponse res, HttpSession session) {
//		User user = userService.login(id, password);
//		if (remember != null) {
//			// 쿠키에 현재 들어온 id를 저장
//			Cookie cookie = new Cookie("remember", id);
//			cookie.setMaxAge(60 * 60 * 24 * 30);
//			res.addCookie(cookie);
//		} else {
//			// remember 쿠기 제거
//			Cookie cookie = new Cookie("remember", id);
//			cookie.setMaxAge(0);
//			res.addCookie(cookie);
//		}
//		if (user != null) {
//			session.setAttribute("loginUser", user);
//			return "redirect:/notice/list"; // 로그인 성공 후 게시글 목록으로 리다이렉트
//		} else {
//			redirectAttributes.addFlashAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
//			return "redirect:/user/login"; // 로그인 실패 시 로그인 페이지로 돌아가기
//		}
//	}

	// Spring Security에 위임
//	@GetMapping("/logout")
//	public String logout(HttpSession session) {
//		session.invalidate();
//		return "redirect:/";
//	}

	// 회원가입 페이지
	@GetMapping("/regist")
	public String registPage() {
		return "user/regist";
	}

	// 회원가입 처리
	@PostMapping("/regist")
	public String regist(@ModelAttribute User user) {
		System.out.println("컨트롤러 regist 수행");
		int result = userService.insertUser(user);
		if (result > 0) {
			return "redirect:/user/login"; // 회원가입 성공 후 로그인 페이지로 리다이렉트
		} else {
			return "user/regist"; // 회원가입 실패 시 회원가입 페이지로 돌아가기
		}
	}

	// 유저 정보 페이지
	@GetMapping("/info")
	public String infoPage(@RequestParam String id, Model model) {
		User user = null;

		user = userService.selectById(id);

		model.addAttribute("user", user);
		return "user/info"; // 로그인된 사용자 정보 페이지로 리다이렉트
	}

	// 유저 정보 데이터 Rest
	@GetMapping("/{id}")
	public ResponseEntity<ResultDTO<User>> info(@PathVariable String id) {
		User user = null;
		if (id.contains("@")) {
			user = userService.selectByEmail(id);
		} else {
			user = userService.selectById(id);
		}

		if (user == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO<>(id + " 유저를 찾을 수 없습니다!", null));
		}
		user.setPassword("");
		return ResponseEntity.ok(new ResultDTO<User>("유저를 정상적으로 불러왔습니다.", user));
	}

	// 유저 삭제 처리
	@PostMapping("/delete")
	public String deleteUser(@RequestParam String id) {
		int result = userService.deleteUser(id);
		if (result > 0) {
			return "redirect:/user/logout"; // 삭제 후 로그아웃
		} else {
			return "redirect:/user/info"; // 삭제 실패 시 유저 정보 페이지로 돌아가기
		}
	}
}