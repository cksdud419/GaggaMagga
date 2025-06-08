package com.ssafy.project.model.dto;

import java.math.BigDecimal;

public class Attractions {
	private int no;					// 명소코드 pk, auto increment
	private String title;			// 명소이름, varchar(500)
	private String addr1;			// 주소1, varchar(100)
	private String addr2;			// 주소2, varchar(100)
	private int area_code;			// 시도코드
	private int si_gun_gu_code;		// 구군코드
	private int content_id;			// 콘텐츠번호
	private int content_type_id;	// 콘텐츠타입
	// 12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점
	private String first_image1;	// 이미지경로1, varchar(100)
	private String first_image2;	// 이미지경로2, varchar(100)
	private String homepage;		// 홈페이지, varchar(1000)
	private BigDecimal latitude;	// 위도, decimal(20,17)
	private BigDecimal longitude;	// 경도, decimal(20,17)
	private int map_level;			// 줌레벨
	private String overView;		// 설명, varchar(10000)
	private String tel;				// 전화번호, varchar(20)
	
	public Attractions() {}
	
	// SELECT용 생성자 (전체 필드)
    public Attractions(int no, String title, String addr1, String addr2, int area_code, int si_gun_gu_code,
                         int content_id, int content_type_id, String first_image1, String first_image2, 
                         String homepage, BigDecimal latitude, BigDecimal longitude, int map_level, 
                         String overView, String tel) {
        this.no = no;
        this.title = title;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.area_code = area_code;
        this.si_gun_gu_code = si_gun_gu_code;
        this.content_id = content_id;
        this.content_type_id = content_type_id;
        this.first_image1 = first_image1;
        this.first_image2 = first_image2;
        this.homepage = homepage;
        this.latitude = latitude;
        this.longitude = longitude;
        this.map_level = map_level;
        this.overView = overView;
        this.tel = tel;
    }

    // INSERT용 생성자 (no 제외)
    public Attractions(String title, String addr1, String addr2, int area_code, int si_gun_gu_code,
                         int content_id, int content_type_id, String first_image1, String first_image2, 
                         String homepage, BigDecimal latitude, BigDecimal longitude, int map_level, 
                         String overView, String tel) {
        this.title = title;
        this.addr1 = addr1;
        this.addr2 = addr2;
        this.area_code = area_code;
        this.si_gun_gu_code = si_gun_gu_code;
        this.content_id = content_id;
        this.content_type_id = content_type_id;
        this.first_image1 = first_image1;
        this.first_image2 = first_image2;
        this.homepage = homepage;
        this.latitude = latitude;
        this.longitude = longitude;
        this.map_level = map_level;
        this.overView = overView;
        this.tel = tel;
    }

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public int getArea_code() {
		return area_code;
	}

	public void setArea_code(int area_code) {
		this.area_code = area_code;
	}

	public int getSi_gun_gu_code() {
		return si_gun_gu_code;
	}

	public void setSi_gun_gu_code(int si_gun_gu_code) {
		this.si_gun_gu_code = si_gun_gu_code;
	}

	public int getContent_id() {
		return content_id;
	}

	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}

	public int getContent_type_id() {
		return content_type_id;
	}

	public void setContent_type_id(int content_type_id) {
		this.content_type_id = content_type_id;
	}

	public String getFirst_image1() {
		return first_image1;
	}

	public void setFirst_image1(String first_image1) {
		this.first_image1 = first_image1;
	}

	public String getFirst_image2() {
		return first_image2;
	}

	public void setFirst_image2(String first_image2) {
		this.first_image2 = first_image2;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public int getMap_level() {
		return map_level;
	}

	public void setMap_level(int map_level) {
		this.map_level = map_level;
	}

	public String getOverView() {
		return overView;
	}

	public void setOverView(String overView) {
		this.overView = overView;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Override
	public String toString() {
	    return "AttractionDTO{" +
	            "no=" + no +
	            ", title='" + title + '\'' +
	            ", addr1='" + addr1 + '\'' +
	            ", addr2='" + addr2 + '\'' +
	            ", area_code=" + area_code +
	            ", si_gun_gu_code=" + si_gun_gu_code +
	            ", content_id=" + content_id +
	            ", content_type_id=" + content_type_id +
	            ", first_image1='" + first_image1 + '\'' +
	            ", first_image2='" + first_image2 + '\'' +
	            ", homepage='" + homepage + '\'' +
	            ", latitude=" + latitude +
	            ", longitude=" + longitude +
	            ", map_level=" + map_level +
	            ", overView='" + overView + '\'' +
	            ", tel='" + tel + '\'' +
	            '}';
	}
}
