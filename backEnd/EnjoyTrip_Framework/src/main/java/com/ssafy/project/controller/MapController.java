package com.ssafy.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.project.model.dto.Attractions;
import com.ssafy.project.model.dto.Guguns;
import com.ssafy.project.model.dto.Sidos;
import com.ssafy.project.model.service.MapService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {
	@Value("${sgis.api.key}")
	private String sgisApiKey;	// sgis 키
	
	@Value("${sgis.api.secret}")
	private String sgisApiSecret;	// sgis 보안 키
	
	@Value("${kakaoMap.js.key}")
	private String kakaoMapJsKey;	// 카카오맵 API js 키
	
	private static final long serialVersionUID = 1L;
	private final MapService mapService;
	
	// 1. 맵 화면으로 이동
	@GetMapping("/mapMain")
	public String map(Model model) {
		model.addAttribute("sgisApiKey", sgisApiKey);
	    model.addAttribute("sgisApiSecret", sgisApiSecret);
	    model.addAttribute("kakaoMapJsKey", kakaoMapJsKey);
		return "map/mapMain";
	}
	
	// 2. API에서 지역(시도) 정보 얻기
	@GetMapping("/sidos")
	@ResponseBody	// Json 반환 위함
	public List<Sidos> getSidos() {
		return mapService.getSidos();
	}
	
	// 3. API에서 상세지역(시군구) 정보 얻기
	@GetMapping("/guguns")
	@ResponseBody
	public List<Guguns> getGuguns(@RequestParam(required = false) Integer sido_code) {
		if (sido_code == null) {
	        return new ArrayList<>(); // sidoCode 없으면 빈 리스트 리턴
	    }
		return mapService.getGuguns(sido_code);
	}
	
	// 4. 관광지 조회
	@GetMapping("/getAttractions")
	@ResponseBody
	public List<Attractions> getAttractions(
			@RequestParam(required = false, defaultValue = "") String keyword,
			@RequestParam(required = false, defaultValue = "-1") Integer area_code,
			@RequestParam(required = false, defaultValue = "-1") Integer si_gun_gu_code,
			@RequestParam(required = false, defaultValue = "-1") Integer content_type_id) {

        	return mapService.getAttractions(keyword, area_code, si_gun_gu_code, content_type_id);
	}
}
