package com.ssafy.project.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.project.model.dto.BookMark;

@Mapper
public interface BookMarkDao {
	// 유저 id의 모든 북마크 가져오기
	List<BookMark> selectAllBookMark(String author_id);
	// 북마크 중복체크
	int exists(BookMark bookMark);
	// 북마크 추가
	int insertBookMark(BookMark bookMark);
	// 북마크 제거
	int deleteBookMark(BookMark bookMark);
}
