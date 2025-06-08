package com.ssafy.project.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.project.model.dao.CommentDao;
import com.ssafy.project.model.dto.Comment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentDao commentDao;

	public int deleteComment(int id) {
		try {
			return commentDao.deleteComment(id);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int insertComment(Comment comment) {
		try {
			return commentDao.insertComment(comment);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int updateComment(Comment comment) {
		try {
			return commentDao.updateComment(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return -1;
	}
}
