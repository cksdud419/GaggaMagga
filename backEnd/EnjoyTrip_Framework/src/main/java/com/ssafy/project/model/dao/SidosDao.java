package com.ssafy.project.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.project.model.dto.Sidos;

@Mapper
public interface SidosDao {
	// 1. 지역 코드(시도 코드) 가져오기
	List<Sidos> selectSidos();
}
