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
public class CommentAi {
	Integer notice_id;
	String author_id;
	String content;
	Date create_at;
}
