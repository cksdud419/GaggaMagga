package com.ssafy.project.model.dto;


import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class NoticeAi {
	String authorId;
	String title;
	String content;
	Date createdAt;
	
	Integer commentsCount;
	List<CommentAi> comments;
}
