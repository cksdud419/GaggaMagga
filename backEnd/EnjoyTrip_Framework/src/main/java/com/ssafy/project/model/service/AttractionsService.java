package com.ssafy.project.model.service;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.dao.AttractionsDao;
import com.ssafy.project.model.dao.BookMarkDao;
import com.ssafy.project.model.dto.Attractions;
import com.ssafy.project.model.dto.BookMark;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionsService {
	private final AttractionsDao attractionsDao;

	// attracions_id를 받아서 관광지 리스트 반환
	@Tool(description = "관광지를 키워드, 지역 코드(시/도), 시군구 코드, 콘텐츠 타입 ID를 기준으로 검색합니다. 결과에는 관광지의 위치, 제목, 이미지, 주소 등이 포함됩니다.")
	public List<Attractions> getAttractionsById(List<Integer> attractionIds) {
		return attractionsDao.selectAttractionsById(attractionIds);
	}
	
	
	
	
}
