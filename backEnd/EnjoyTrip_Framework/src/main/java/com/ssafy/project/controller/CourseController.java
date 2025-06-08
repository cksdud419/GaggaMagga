package com.ssafy.project.controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.project.model.dto.Course;
import com.ssafy.project.model.dto.CourseDetail;
import com.ssafy.project.model.dto.ResultDTO;
import com.ssafy.project.model.dto.User;
import com.ssafy.project.model.service.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
	@Value("${kakaoMobility.api.key}")
	private String api;
	private final CourseService courseService;

	// 특정 코스 조회 (코스 + 코스 경로)
	@GetMapping("/{id}")
	public ResponseEntity<ResultDTO<Course>> selectCourse(@PathVariable Integer id) {
		System.out.println(id);
		Course data = courseService.selectCourse(id);
		if (data == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO<>("코스 정보가 없습니다.", null));
		}
		List<CourseDetail> details = data.getCourse_details();
		System.out.println("details");
		if(details.size() == 1 && details.get(0).getCourse_id() == null) {
			details.clear();
		}
		if(details.size() < 1) {
			return ResponseEntity.ok(new ResultDTO<>("코스를 정상적으로 불러왔습니다. (경로 없음)", data));			
		}

		
		List<Double> vertexes = new ArrayList<>();
		try {
			// 1. origin, destination 설정
			CourseDetail origin = details.get(0);
			CourseDetail destination = details.get(details.size() - 1);

			// 2. waypoints 설정 (중간 경유지)
			List<Map<String, Double>> waypoints = new ArrayList<>();
			for (int i = 1; i < details.size() - 1; i++) {
				CourseDetail wp = details.get(i);
				Map<String, Double> point = new HashMap<>();
				point.put("x", wp.getLongitude().doubleValue());
				point.put("y", wp.getLatitude().doubleValue());
				waypoints.add(point);
			}

			// 3. 카카오 API 요청 JSON 만들기
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> body = new HashMap<>();
			body.put("origin",
					Map.of("x", origin.getLongitude().doubleValue(), "y", origin.getLatitude().doubleValue()));
			body.put("destination", Map.of("x", destination.getLongitude().doubleValue(), "y",
					destination.getLatitude().doubleValue()));
			if (!waypoints.isEmpty())
				body.put("waypoints", waypoints);
			String jsonBody = mapper.writeValueAsString(body);
//			System.out.println(jsonBody.toString());

			// 4. HTTP 요청 보내기
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create("https://apis-navi.kakaomobility.com/v1/waypoints/directions"))
					.header("Authorization", "KakaoAK " + api) // <-- 여기에 본인 키
					.header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonBody))
					.build();
			

			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

			// 5. 응답 파싱해서 vertexes 리스트 만들기
			JsonNode root = mapper.readTree(response.body());

			for (JsonNode section : root.path("routes").get(0).path("sections")) {
				for (JsonNode road : section.path("roads")) {
					for (JsonNode point : road.path("vertexes")) {
						// vertexes 배열은 [lng, lat, lng, lat, ...] 형태
						vertexes.add(Double.parseDouble(point.toString()));
					}
				}
			}

			// 6. 결과 반영
			data.setVertexes(vertexes);
			return ResponseEntity.ok(new ResultDTO<>("코스를 정상적으로 불러왔습니다.", data));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResultDTO<>("경로 요청 중 오류 발생", null));
		}
	}
	
	@GetMapping("/rand")
	public ResponseEntity<ResultDTO<Course>> selectCourseRand() {
		Course data = courseService.selectRandomCourse();
		
		if(data == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResultDTO<>("코스를 불러오지 못했습니다.", null));
		}
		return ResponseEntity.ok(new ResultDTO<>("코스를 정상적으로 불러왔습니다", data));
	}

	// 특정 유저의 모든 코스 조회
	@GetMapping("/all/{author_id}")
	public ResponseEntity<ResultDTO<List<Course>>> selectCourseAll(@PathVariable("author_id") String authorId) {
		List<Course> data = courseService.selectCourseAll(authorId);
		if (data == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO<>("코스를 불러오지 못했습니다.", null));
		}
		return ResponseEntity.ok(new ResultDTO<>("코스를 정상적으로 불러왔습니다.", data));
	}
	
	// 로그인 한 유저의 모든 코스 조회
//	@GetMapping("/all")
//	public ResponseEntity<ResultDTO<List<Course>>> selectCourseAllLoginUser(@ModelAttribute("loginUser") User loginUser) {
//		return selectCourseAll(loginUser.getId());
//	}
//	@GetMapping("/all/{userId}")
//	public ResponseEntity<ResultDTO<List<Course>>> selectCourseAllLoginUser(@PathVariable String userId) {
//		return selectCourseAll(userId);
//	}

	// 코스 생성
//	@PostMapping
//	public ResponseEntity<ResultDTO<Boolean>> insertCourse(@RequestBody Course data, @ModelAttribute("loginUser") User loginUser) {
//		if(data.getAuthor_id() == null) {
//			data.setAuthor_id(loginUser.getId());
//		}
//
//		Integer result = courseService.insertCourse(data);
//		System.out.println("data: " + data);
//		if (result == null) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO<>("코스 삽입 실패했습니다", false));
//		}
//		return ResponseEntity.ok(new ResultDTO<>("코스 삽입 성공", true));
//	}
	@PostMapping
	public ResponseEntity<ResultDTO<Boolean>> insertCourse(@RequestBody Course data) {
	    if (data.getAuthor_id() == null || data.getAuthor_id().isBlank()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	            .body(new ResultDTO<>("작성자 정보가 필요합니다.", false));
	    }

	    Integer result = courseService.insertCourse(data);
	    System.out.println("data: " + data);
	    
	    if (result == null) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	            .body(new ResultDTO<>("코스 삽입 실패했습니다", false));
	    }

	    return ResponseEntity.ok(new ResultDTO<>("코스 삽입 성공", true));
	}

	// 코스 디테일 하나 추가
	@PostMapping("/{id}/detail")
	public ResponseEntity<ResultDTO<Boolean>> insertCourseDetail(@PathVariable Integer id,
			@RequestBody CourseDetail detail) {
		Integer result = courseService.insertCourseDetail(id, detail);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO<>("코스 상세 삽입 실패", false));
		}
		return ResponseEntity.ok(new ResultDTO<>("코스 상세 삽입 성공", true));
	}

	// 코스 디테일 여러 개 추가
	@PostMapping("/{id}/details")
	public ResponseEntity<ResultDTO<Boolean>> insertCourseDetailAll(@PathVariable Integer id,
			@RequestBody List<CourseDetail> details) {
		Integer result = courseService.insertCourseDetailAll(id, details);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO<>("코스 상세 여러 개 삽입 실패", false));
		}
		return ResponseEntity.ok(new ResultDTO<>("코스 상세 여러 개 삽입 성공", true));
	}

	// 코스 디테일 수정 (예: order 변경)
	@PutMapping("/{id}/detail")
	public ResponseEntity<ResultDTO<Boolean>> updateCourseDetail(@PathVariable Integer id,
			@RequestBody CourseDetail detail) {
		Integer result = courseService.updateCourseDetail(id, detail);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO<>("코스 상세 수정 실패", false));
		}
		return ResponseEntity.ok(new ResultDTO<>("코스 상세 수정 성공", true));
	}

	// 코스 디테일 삭제
	@DeleteMapping("/{id}/detail/{detailOrder}")
	public ResponseEntity<ResultDTO<Boolean>> deleteCourseDetail(@PathVariable Integer id,
			@PathVariable Integer detailOrder) {
		Integer result = courseService.deleteCourseDetail(id, detailOrder);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO<>("코스 상세 삭제 실패", false));
		}
		return ResponseEntity.ok(new ResultDTO<>("코스 상세 삭제 성공", true));
	}

	// 코스 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<ResultDTO<Boolean>> deleteCourse(@PathVariable Integer id) {
		Integer result = courseService.deleteCourse(id);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResultDTO<>("코스 삭제 실패", false));
		}
		return ResponseEntity.ok(new ResultDTO<>("코스 삭제 성공", true));
	}

}