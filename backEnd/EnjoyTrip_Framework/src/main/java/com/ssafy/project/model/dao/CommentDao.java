package com.ssafy.project.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.project.model.dto.Comment;

@Mapper
public interface CommentDao {
	// 댓글 조회는 게시글이 알아서 할거다
	// 1. 댓글 제거 (id)
	int deleteComment(int id);
	// 2. 댓글 입력 (comment)
	int insertComment(Comment comment);
	// 3. 댓글 수정 (comment)
	int updateComment(Comment comment);
}
