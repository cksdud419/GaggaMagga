package com.ssafy.project.model.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.project.model.dto.User;

@Mapper
public interface UserDao {
	// 1. 로그인 체크
	User loginById(String id, String password);
	User loginByEmail(String email, String password);
	
	User selectById(String id);
	User selectByEmail(String email);
	
	// 2. 회원가입
	int insertUser(User user);
	
	// 3. 회원탈퇴
	int deleteUser(String id);
	
	// 4. 유저 정보 수정
	int updateUser(User user);
}
