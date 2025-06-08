package com.ssafy.project.model.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class CourseDetail{
	Integer attractions_id;
	Integer course_id;
	Integer detail_order;

	// 외래키로 가져올 정보
	BigDecimal latitude;	// 위도, decimal(20,17)
	BigDecimal longitude;	// 경도, decimal(20,17)
	String title;
	String first_image1;
	String addr1;
	String tel;
	int content_type_id;
	
	public CourseDetailAi toAi() {
		CourseDetailAi ai = new CourseDetailAi();
		ai.setAddr1(addr1);
		ai.setDetail_order(detail_order);
		ai.setFirst_image1(first_image1);
		ai.setLatitude(latitude);
		ai.setLongitude(longitude);
		ai.setTel(tel);
		ai.setTitle(title);
		ai.setContent_type_id(content_type_id);
		
		return ai;
	}
}
