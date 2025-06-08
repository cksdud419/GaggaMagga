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
public class Notice {
	Integer id;
	String authorId;
	String title;
	String content;
	Date createdAt;

	Integer commentsCount;
	List<Comment> comments;

	public NoticeAi toAi() {
		NoticeAi ai = new NoticeAi();

		ai.setAuthorId(authorId);
		if (comments != null)
			ai.setComments(comments.stream().map(i -> i.toAi()).toList());
		ai.setCommentsCount(commentsCount);
		ai.setContent(content);
		ai.setCreatedAt(createdAt);
		ai.setTitle(title);

		return ai;
	}
}
