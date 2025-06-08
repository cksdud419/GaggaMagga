package com.ssafy.project.model.dto;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookMark {
	private String author_id;
	private Integer attractions_id;
	
	// 외래키로 가져올 정보
	BigDecimal latitude;	// 위도, decimal(20,17)
	BigDecimal longitude;	// 경도, decimal(20,17)
	String title;
	String first_image1;
	String addr1;
	String tel;
	int content_type_id;
	
	public BookMarkAi toAi() {
		BookMarkAi bookmark = new BookMarkAi();
		bookmark.setAttractions_id(attractions_id);
		bookmark.setAddr1(addr1);
		bookmark.setAuthor_id(author_id);
		bookmark.setFirst_image1(first_image1);
		bookmark.setLatitude(latitude);
		bookmark.setLongitude(longitude);
		bookmark.setTel(tel);
		bookmark.setTitle(title);
		bookmark.setContent_type_id(content_type_id);
		
		return bookmark;
	}
}