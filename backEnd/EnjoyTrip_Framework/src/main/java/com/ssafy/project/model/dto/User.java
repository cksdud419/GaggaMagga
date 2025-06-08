package com.ssafy.project.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class User {
	String id;
	String nickName;
	String email;
	String password;
	String refresh;

	// 마이페이지용 유저가 작성한 게시글 모음
	List<Notice> notices;
	List<BookMark> bookmarks;
	List<Course> courses;

	public UserAi toAi() {
		UserAi ai = new UserAi();
		ai.setId(id);
		if (bookmarks != null)
			ai.setBookmarks(bookmarks.stream().map(i -> i.toAi()).toList());
		if (courses != null)
			ai.setCourses(courses.stream().map(i -> i.toAi()).toList());

		ai.setEmail(email);
		ai.setNickName(nickName);

		if (notices != null)
			ai.setNotices(notices.stream().map(i -> i.toAi()).toList());

		System.out.println("변환 성공");
		return ai;
	}
}
