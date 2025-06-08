package com.ssafy.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.project.model.dto.Attractions;
import com.ssafy.project.model.dto.Guguns;
import com.ssafy.project.model.dto.ResultDTO;
import com.ssafy.project.model.dto.Sidos;
import com.ssafy.project.model.service.MapService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class MapControllerRest {
	@Value("${sgis.api.key}")
	private String sgisApiKey;	// sgis 키
	
	@Value("${sgis.api.secret}")
	private String sgisApiSecret;	// sgis 보안 키
	
	@Value("${kakaoMap.js.key}")
	private String kakaoMapJsKey;	// 카카오맵 API js 키
	
	private final MapService mapService;
	
	// API에서 지역(시도) 정보 얻기
	@GetMapping("/sidos")
	public ResponseEntity<ResultDTO<List<Sidos>>> getSidos() {
		List<Sidos> sidoList = mapService.getSidos();
		if(sidoList == null) {
			return ResponseEntity.badRequest().body(new ResultDTO<>("시도 정보 불러오기 실패!!!", null));
		}
		
		ResultDTO<List<Sidos>> resultDTO = new ResultDTO<>("시도 정보 불러오기 성공", sidoList);
		return ResponseEntity.ok(resultDTO);
	}
	
	// API에서 상세지역(시군구) 정보 얻기
	@GetMapping("/guguns")
	public ResponseEntity<ResultDTO<List<Guguns>>> getGuguns(@RequestParam(required = false) Integer sido_code) {
		ResultDTO<List<Guguns>> resultDTO;
		if(sido_code == null) {
			resultDTO = new ResultDTO<>("선택된 시도코드 없음", new ArrayList<>());
		} else {
			List<Guguns> gugunList = mapService.getGuguns(sido_code);
			if(gugunList == null) {
				return ResponseEntity.badRequest().body(new ResultDTO<>("시군구 정보 불러오기 실패!!!", null));
			}
			resultDTO = new ResultDTO<>("시군구 정보 불러오기 성공", gugunList);
		}
		return ResponseEntity.ok(resultDTO);
	}
	
	// 관광지 조회
	@GetMapping("/attractions")	// 원래 getAttractions이었음, 옮길 때 참고
	public ResponseEntity<ResultDTO<List<Attractions>>> getAttractions(
			@RequestParam(required = false, defaultValue = "") String keyword,
			@RequestParam(required = false, defaultValue = "-1") Integer area_code,
			@RequestParam(required = false, defaultValue = "-1") Integer si_gun_gu_code,
			@RequestParam(required = false, defaultValue = "-1") Integer content_type_id) {
		List<Attractions> AttractionList = mapService.getAttractions(keyword, area_code, si_gun_gu_code, content_type_id);
		
		ResultDTO<List<Attractions>> resultDTO;
		if(AttractionList == null) {
			resultDTO = new ResultDTO<>("검색된 관광지 정보가 없음", AttractionList);
		} else {
			resultDTO = new ResultDTO<>("관광지 정보 목록 불러오기 성공", AttractionList);
		}
		
		return ResponseEntity.ok(resultDTO);
	}
}
