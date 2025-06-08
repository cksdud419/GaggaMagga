package com.ssafy.project.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.project.model.dto.Guguns;

@Mapper
public interface GugunsDao {
	// 1. 구군 코드 가져오기
	List<Guguns> selectGuguns(int sidoCode);
}
