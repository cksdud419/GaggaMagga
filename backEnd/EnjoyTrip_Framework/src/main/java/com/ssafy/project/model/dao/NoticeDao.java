package com.ssafy.project.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.project.model.dto.Notice;

@Mapper
public interface NoticeDao {
	// 1. 모든 게시글 조회
	List<Notice> selectNoticeAll(Integer page);
	// 2. 유저 id로 게시글 조회
	List<Notice> selectNoticeByUserId(String userId, Integer page);
	// 3. 제목으로 게시글 조회
	List<Notice> selectNoticeByTitle(String title, Integer page);	
	// 4. 게시글 id 조회
	Notice selectNoticeById(int id);
	// 5. 게시글 입력
	int insertNotice(Notice notice);	
	// 6. 게시글 수정
	int updateNotice(Notice notice);	
	// 7. 게시글 삭제
	int deleteNotice(int id);
}
