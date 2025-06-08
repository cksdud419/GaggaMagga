package com.ssafy.project.model.service;

import java.sql.Connection;
import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.dao.AttractionsDao;
import com.ssafy.project.model.dao.GugunsDao;
import com.ssafy.project.model.dao.SidosDao;
import com.ssafy.project.model.dto.Attractions;
import com.ssafy.project.model.dto.Guguns;
import com.ssafy.project.model.dto.GugunsAi;
import com.ssafy.project.model.dto.Sidos;
import com.ssafy.project.model.dto.SidosAi;
import com.ssafy.project.util.ChosungSearch;
import com.ssafy.project.util.ChosungSearchData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MapService {
    private final AttractionsDao attractionDao;
    private final SidosDao sidosDao;
    private final GugunsDao gugunsDao;
    
    /* ============================================ F01~03 ============================================ */
    // Todo 예외처리들
    
    // 1. 지역별 관광지 정보 수집
    @Tool(description = "관광지의 정보를 가져와서 찾습니다")
    public List<Attractions> getAttractions(@ToolParam(description = "검색 키워드 공백이면 전체를 뜻한다") String keyword, 
    		@ToolParam(description = "시도 코드 -1은 전체를 뜻한다") Integer area_code, 
    		@ToolParam(description =  "구군 코드 -1은 전체를 뜻한다") Integer si_gun_gu_code, 
    		@ToolParam(description = "컨텐츠 타입으로"
    				+ "-1은 전체"
    				+ "12는 관광지"
    				+ "14는 문화시설"
    				+ "15는 축제공연행사"
    				+ "25는 레포츠"
    				+ "32는 숙박"
    				+ "28은 쇼핑"
    				+ "29는 음식점을 뜻한다") Integer content_type_id) {
//    	System.out.println("대체 어떻게 검색하길레" + keyword);
//    	System.out.println("시도코드" + area_code);
//    	System.out.println("구군 코드" + si_gun_gu_code);
//    	System.out.println("컨텐츠 타입" + content_type_id);
        try {
            return attractionDao.selectAttractions(keyword, area_code, si_gun_gu_code, content_type_id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // 2. 지역 정보(시도) 반환
    public List<Sidos> getSidos() {
    	try {
    		return sidosDao.selectSidos();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    @Tool(description = "모든 시/도 정보를 조회합니다. 각 시도는 고유 코드와 이름을 포함합니다.")
    private List<SidosAi> getSidosAi(){
    	List<Sidos> list = getSidos();
    	if(list == null)
    		return null;
    	
    	return list.stream().map(i -> i.toAi()).toList();	
    }

    // 3. 상세 지역 정보(시군구) 반환
    public List<Guguns> getGuguns(int sidoCode) {
    	try {
    		return gugunsDao.selectGuguns(sidoCode);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    
    @Tool(description = "지정한 시도 코드에 해당하는 시군구 목록을 조회합니다. 각 항목은 고유 코드와 이름을 포함합니다.")
    private List<GugunsAi> getGugunsAi(int sidoCode){
    	List<Guguns> list = getGuguns(sidoCode);
    	if(list == null)
    		return null;
    	return list.stream().map(i -> i.toAi()).toList();
    }
}

