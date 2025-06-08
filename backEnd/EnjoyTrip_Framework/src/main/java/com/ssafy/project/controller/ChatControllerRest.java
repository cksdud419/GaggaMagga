package com.ssafy.project.controller;

import java.time.LocalDateTime;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.ChatClientRequestSpec;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.ssafy.project.model.dto.ResultDTO;
import com.ssafy.project.model.service.AttractionsService;
import com.ssafy.project.model.service.BookMarkService;
import com.ssafy.project.model.service.CourseService;
import com.ssafy.project.model.service.MapService;
import com.ssafy.project.model.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatControllerRest {
	private final ChatClient chatClient;
	private final AttractionsService attractionsService;
	private final BookMarkService bookMarkService;
	private final CourseService courseService;
	private final MapService mapService;
	private final UserService userService;

	// gpt 질문
	@PostMapping
	public ResponseEntity<ResultDTO<String>> call(@RequestBody String message) {
		try {
			String result = chatClient.prompt().system(spec -> spec.param("language", "korean")
					.param("system", "당신은 여행 가이드 도우미 AI입니다. 사용자가 원하는 정보를 적절한 도구(@Tool)를 사용하여 얻고, 필요할 경우 여러 도구를 조합하여 질문에 답하십시오. ID는 내부 식별용이며, 응답에 포함하지 마십시오."))
					.tools(new DateTimeTools()).tools(userService).tools(bookMarkService).tools(courseService)
					.tools(mapService).tools(attractionsService).user(message).call().content();
			return ResponseEntity.ok(new ResultDTO<>("응답 성공!", result));
		} catch (Exception e) {
			return ResponseEntity.ok(new ResultDTO<>("잘못된 응답!", "응답 실패"));
		}
	}

}

class DateTimeTools {
	@Tool(description = "Get the current date and time in the user's timezone")
	String getCurrentDateTime() {
		return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
	}
}
