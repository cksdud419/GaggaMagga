package com.ssafy.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.model.dto.BookMark;
import com.ssafy.project.model.dto.ResultDTO;
import com.ssafy.project.model.dto.Sidos;
import com.ssafy.project.model.dto.User;
import com.ssafy.project.model.service.BookMarkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookmark")
@RequiredArgsConstructor
public class BookMarkControllerRest {
	private final BookMarkService bookMarkService;
	
	// 북마크 조회
//	@GetMapping("/bookMarks")		// 원래 getBookMark였음, 옮길 때 참고
//	public ResponseEntity<ResultDTO<List<BookMark>>> getBookMark(@ModelAttribute("loginUser") User loginUser) {
//		String author_id = loginUser.getId();
//		
//		// 북마크 목록 조회
//		List<BookMark> bookmarks = bookMarkService.getAllBookMark(author_id);
//		
//		ResultDTO<List<BookMark>> resultDTO;
//		if (bookmarks.isEmpty()) {		// 북마크가 없을 경우 빈 리스트 반환
//			resultDTO = new ResultDTO<>("북마크가 없습니다.", new ArrayList<>());
//	    } else {
//	    	resultDTO = new ResultDTO<>("북마크 정보 불러오기 성공", bookmarks);
//	    }
//		return ResponseEntity.ok(resultDTO);
//	}
	@GetMapping("/{userId}")
    public ResponseEntity<ResultDTO<List<BookMark>>> getBookmarks(@PathVariable String userId) {
        List<BookMark> bookmarks = bookMarkService.getAllBookMark(userId);

        ResultDTO<List<BookMark>> result = bookmarks.isEmpty()
            ? new ResultDTO<>("북마크가 없습니다.", new ArrayList<>())
            : new ResultDTO<>("북마크 정보 불러오기 성공", bookmarks);

        return ResponseEntity.ok(result);
    }
	
	// 북마크 추가/삭제 = 없으면 추가, 이미 있으면 삭제
//	@PostMapping("/bookMarks")	// 원래 addOrRemoveBookMark였음, 옮길 때 참고
//	public ResponseEntity<ResultDTO<String>> addOrRemoveBookMark(
//	        @RequestParam Integer attractions_id,
//	        @ModelAttribute("loginUser") User loginUser) {
//
//	    String author_id = loginUser.getId();
//
//	    // BookMark 객체 생성
//	    BookMark bookMark = new BookMark();
//	    bookMark.setAuthor_id(author_id);
//	    bookMark.setAttractions_id(attractions_id);
//
//	    // 중복 여부 확인
//	    boolean exists = bookMarkService.exists(bookMark);
//
//	    if (exists) {
//	        // 이미 있으면 삭제
//	        int result = bookMarkService.deleteBookMark(bookMark);
//	        if (result > 0) {
//	            return ResponseEntity.ok(new ResultDTO<>("북마크 삭제 성공", "delete"));
//	        } else {
//	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                    .body(new ResultDTO<>("북마크 삭제 실패", "fail"));
//	        }
//	    } else {
//	        // 없으면 추가
//	        int result = bookMarkService.addBookMark(bookMark);
//	        if (result > 0) {
//	            return ResponseEntity.ok(new ResultDTO<>("북마크 추가 성공", "success"));
//	        } else {
//	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//	                    .body(new ResultDTO<>("북마크 추가 실패", "fail"));
//	        }
//	    }
//	}
	
	@PostMapping
    public ResponseEntity<ResultDTO<String>> toggleBookmark(@RequestBody BookMark bookMark) {
        boolean exists = bookMarkService.exists(bookMark);

        if (exists) {
            int result = bookMarkService.deleteBookMark(bookMark);
            return result > 0
                ? ResponseEntity.ok(new ResultDTO<>("북마크 삭제 성공", "delete"))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDTO<>("북마크 삭제 실패", "fail"));
        } else {
            int result = bookMarkService.addBookMark(bookMark);
            return result > 0
                ? ResponseEntity.ok(new ResultDTO<>("북마크 추가 성공", "success"))
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDTO<>("북마크 추가 실패", "fail"));
        }
    }
}
