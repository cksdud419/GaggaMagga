package com.ssafy.project.controller;

import com.ssafy.project.model.dto.Comment;
import com.ssafy.project.model.dto.Notice;
import com.ssafy.project.model.dto.ResultDTO;
import com.ssafy.project.model.service.CommentService;
import com.ssafy.project.model.service.UserService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notice")
@RequiredArgsConstructor
public class NoticeControllerRest {

	private final UserService noticeService;
	private final CommentService commentService;

	// 게시글 목록 페이지
	@GetMapping({"/list","/list/{search}"})
	public ResponseEntity<ResultDTO<List<Notice>>> listNotices(@PathVariable(required = false) String search, @RequestParam int page) {
		List<Notice> list = null;
		if (search == null || search.isEmpty()) {
			list = noticeService.selectNoticeAll(page); // 전체 게시글 리스트
		} else {
			list = noticeService.selectNoticeByTitle(search, page); // 제목 검색
		}
		
		if(list == null) {
			return ResponseEntity.badRequest().body(new ResultDTO<>("게시글 불러오기 실패!", null));
		}
		return ResponseEntity.ok(new ResultDTO<>(list.size()+"개 게시글 가져오기 완료", list));
	}

	// 게시글 상세 페이지
	@GetMapping("/{id}")
	public ResponseEntity<ResultDTO<Notice>> noticeDetail(@PathVariable int id) {
		Notice notice = noticeService.selectNoticeById(id);
		if(notice == null) {
			return ResponseEntity.badRequest().body(new ResultDTO<>("없는 게시글입니다!", null));
		}
		return ResponseEntity.ok(new ResultDTO<>("게시글 가져오기 완료", notice));
	}

	// 게시글 등록 및 수정 처리
	@PostMapping
	public ResponseEntity<ResultDTO<Boolean>> registNotice(@RequestBody Notice notice) {
		if(notice == null)
			return ResponseEntity.badRequest().body(new ResultDTO<>("잘못된 게시글 형태입니다!", false));
		
		int result = 0;
		if (notice.getId() == null) {
			result = noticeService.insertNotice(notice); // 새로운 게시글 등록
		} else {
			result = noticeService.updateNotice(notice); // 기존 게시글 수정
		}
		
		if(result > 0) {
			return ResponseEntity.ok(new ResultDTO<>("게시글 등록 성공!", true));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO<>("게시글 등록 실패!", false));
	}

	// 게시글 삭제 처리
	@PostMapping("/delete/{id}")
	public ResponseEntity<ResultDTO<Boolean>> deleteNotice(@PathVariable int id) {
		int result = noticeService.deleteNotice(id);
		
		if(result > 0) {
			return ResponseEntity.ok(new ResultDTO<>("게시글 삭제 성공!", true));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO<>("게시글 삭제 실패!", false));
	}
	
	// 댓글 삭제 처리
	@PostMapping("/comment/delete/{id}")
	public ResponseEntity<ResultDTO<Boolean>> deleteComment(@PathVariable int id){
		int result = commentService.deleteComment(id);
		if(result > 0) {
			return ResponseEntity.ok(new ResultDTO<>("댓글 삭제 성공", true));
		}
		return ResponseEntity.badRequest().body(new ResultDTO<>("댓글 삭제 실패", false));
	}
	// 댓글 입력 처리
	@PostMapping("/comment/insert")
	public ResponseEntity<ResultDTO<Boolean>> insertComment(@RequestBody Comment comment){
		int result = commentService.insertComment(comment);
		if(result > 0) {
			return ResponseEntity.ok(new ResultDTO<>("댓글 등록 성공", true));
		}
		return ResponseEntity.badRequest().body(new ResultDTO<>("댓글 등록 실패", false));
	}
	// 댓글 수정 처리
	@PostMapping("/comment/update")
	public ResponseEntity<ResultDTO<Boolean>> updateComment(@RequestBody Comment comment){
		int result = commentService.updateComment(comment);
		if(result > 0) {
			return ResponseEntity.ok(new ResultDTO<>("댓글 수정 성공", true));
		}
		return ResponseEntity.badRequest().body(new ResultDTO<>("댓글 수정 실패", false));
	}
}
