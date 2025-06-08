package com.ssafy.project.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.dao.BookMarkDao;
import com.ssafy.project.model.dto.BookMark;
import com.ssafy.project.model.dto.BookMarkAi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookMarkService {
	private final BookMarkDao bookMarkDao;
	
	// 로그인한 아이디의 북마크 확인
	public List<BookMark> getAllBookMark(String author_id) {
		return bookMarkDao.selectAllBookMark(author_id);
	}
	
	@Tool(description = "로그인한 사용자의 모든 북마크 정보를 조회합니다. 북마크에는 관광지의 위치, 제목, 이미지, 주소, 전화번호 등의 정보가 포함됩니다.")
	private List<BookMarkAi> getAllBookMarkAi(String author_id){
		List<BookMark> bookmarks = getAllBookMark(author_id);
		List<BookMarkAi> result = new ArrayList<>(bookmarks.size());
		
		for(BookMark b:bookmarks) {
			result.add(b.toAi());
		}
		return result;
	}

	// 북마크 중복 체크
	public boolean exists(BookMark bookMark) {
		return bookMarkDao.exists(bookMark) > 0;
	}

	// 북마크 추가
	public int addBookMark(BookMark bookMark) {
		return bookMarkDao.insertBookMark(bookMark);
	}
	
	// 북마크 제거
	public int deleteBookMark(BookMark bookMark) {
		return bookMarkDao.deleteBookMark(bookMark);
	}
}
