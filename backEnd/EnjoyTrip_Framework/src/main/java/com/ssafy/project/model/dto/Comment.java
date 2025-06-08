package com.ssafy.project.model.dto;

import java.util.Date;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Comment {
	Integer id;
	Integer notice_id;
	String author_id;
	String content;
	Date create_at;
	
	public CommentAi toAi() {
		CommentAi ai = new CommentAi();
		ai.setAuthor_id(author_id);
		ai.setContent(content);
		ai.setCreate_at(create_at);
		ai.setNotice_id(notice_id);
		
		return ai;
	}
}
