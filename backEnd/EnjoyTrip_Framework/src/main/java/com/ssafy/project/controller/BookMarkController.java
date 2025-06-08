package com.ssafy.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.project.model.dto.Attractions;
import com.ssafy.project.model.dto.BookMark;
import com.ssafy.project.model.dto.User;
import com.ssafy.project.model.service.AttractionsService;
import com.ssafy.project.model.service.BookMarkService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/bookMark")
@RequiredArgsConstructor
public class BookMarkController {
	private static final long serialVersionUID = 1L;
	private final BookMarkService bookMarkService;
	
	// 1. 북마크 조회
	@GetMapping("/getBookMark")
	@ResponseBody
	public List<BookMark> getBookMark(@ModelAttribute("loginUser") User loginUser) {
	    String author_id = loginUser.getId();
	    
	    // 북마크 목록 조회
		List<BookMark> bookmarks = bookMarkService.getAllBookMark(author_id);
	    if (bookmarks.isEmpty()) {		// 북마크가 없을 경우 빈 리스트 반환
	        return new ArrayList<>();  // 빈 리스트 반환
	    }
		
		// 관광지 목록 반환
		return bookmarks;
	}
	
	// 2. 북마크 추가/삭제 = 없으면 추가, 이미 있으면 삭제
    @PostMapping("/addOrRemoveBookMark")
    @ResponseBody
    public String addOrRemoveBookMark(@RequestParam Integer attractions_id,
    		Model model,
    		@ModelAttribute("loginUser") User loginUser) {
	    String author_id = loginUser.getId();
    	
        // BookMark 객체 생성
        BookMark bookMark = new BookMark();
        bookMark.setAuthor_id(author_id);
        bookMark.setAttractions_id(attractions_id);
        
        // 중복 체크
        boolean exists = bookMarkService.exists(bookMark);
        
        if (exists) {
            // 중복이면 북마크 삭제
            int result = bookMarkService.deleteBookMark(bookMark);
            return (result > 0) ? "delete" : "fail";
        } else {
            // 없으면 북마크 추가
            int result = bookMarkService.addBookMark(bookMark);
            return (result > 0) ? "success" : "fail";
        }
    }
}
