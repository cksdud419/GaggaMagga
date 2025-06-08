package com.ssafy.project.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.dao.NoticeDao;
import com.ssafy.project.model.dao.UserDao;
import com.ssafy.project.model.dto.User;
import com.ssafy.project.model.service.UserService;
import com.ssafy.project.util.ChosungSearch;
import com.ssafy.project.util.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loadUserByUesrName");
		User user = null;
		if (username.contains("@"))
			user = userService.selectByEmail(username);
		else
			user = userService.selectById(username);
		
		if (user == null)
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다!");
		
		return new CustomUserDetails(user);
	}
}