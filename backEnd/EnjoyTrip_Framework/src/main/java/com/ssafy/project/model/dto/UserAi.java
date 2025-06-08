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
public class UserAi {
	String id;
	String nickName;
	String email;
		
	// 마이페이지용 유저가 작성한 게시글 모음
	List<NoticeAi> notices;
	List<BookMarkAi> bookmarks;
	List<CourseAi> courses;
}
