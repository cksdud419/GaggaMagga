package com.ssafy.project.controller;

import com.ssafy.project.model.dto.Notice;
import com.ssafy.project.model.dto.User;
import com.ssafy.project.model.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

	private final UserService noticeService;

	// 게시글 목록 페이지
	@GetMapping("/list")
	public String listNotices(@RequestParam(required = false) String search, Model model) {
		if (search == null || search.isEmpty()) {
			model.addAttribute("noticeList", noticeService.selectNoticeAll(0)); // 전체 게시글 리스트
		} else {
			model.addAttribute("noticeList", noticeService.selectNoticeByTitle(search, 0)); // 제목 검색
		}
		return "notice/list";
	}

	// 게시글 상세 페이지
	@GetMapping("/detail")
	public String noticeDetail(@RequestParam int id, Model model) {
		Notice notice = noticeService.selectNoticeById(id);
		model.addAttribute("notice", notice);
		return "notice/detail";
	}

	// 게시글 등록 및 수정 페이지
	@GetMapping("/regist")
	public String registNotice(@RequestParam(required = false) Integer id, Model model) {
		if (id != null) {
			Notice notice = noticeService.selectNoticeById(id);
			model.addAttribute("notice", notice);
		}
		return "notice/regist"; // 등록이거나 수정일 때 동일 페이지
	}

	// 게시글 등록 및 수정 처리
	@PostMapping({"/regist", "/update"})
	public String registNotice(@ModelAttribute Notice notice) {
		if (notice.getId() == null) {
			noticeService.insertNotice(notice); // 새로운 게시글 등록
		} else {
			noticeService.updateNotice(notice); // 기존 게시글 수정
		}
		return "redirect:/notice/list"; // 게시글 목록으로 리다이렉트
	}

	// 게시글 삭제 처리
	@PostMapping("/delete")
	public String deleteNotice(@RequestParam int id) {
		noticeService.deleteNotice(id);
		return "redirect:/notice/list"; // 삭제 후 게시글 목록으로 리다이렉트
	}
}
