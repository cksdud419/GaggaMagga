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
public class CourseDetailAi{
	Integer detail_order;

	// 외래키로 가져올 정보
	BigDecimal latitude;	// 위도, decimal(20,17)
	BigDecimal longitude;	// 경도, decimal(20,17)
	String title;
	String first_image1;
	String addr1;
	String tel;
	int content_type_id;
}
