package com.ssafy.project.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.ssafy.project.model.dto.Attractions;

@Mapper
public interface AttractionsDao {
	// 1. 여행지 조회 (Map에서 기본 검색용, 데이터 표시 수 관계로 랜덤 및 리미트 존재)
	List<Attractions> selectAttractions(String keyword, Integer area_code, Integer si_gun_gu_code, Integer content_type_id);

	// 2. 여행지 id로 조회(북마크에서 사용)
	List<Attractions> selectAttractionsById(List<Integer> attractionIds);
	
	
}
