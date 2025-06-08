# SSAFY13_광주5_관통_FINAL_PJT_7팀_정찬영_홍도완
# 🚅 가까마까 : 공공데이터를 활용한 관광지 정보 소개 및 검색 서비스

![가까마까.png](/image/가까마까.png)

## 팀: 정도전 (정찬영 홍도완 도전!)
#### 팀장: 정찬영
##### BackEnd
- 관광지 및 지역 정보 조회 기능 개발
- 관광지 검색 및 필터링 기능 개발
- 관광지 북마크 저장 및 조회 기능 개발
- 날씨 정보 조회 기능 개발
##### FrontEnd
- 로그인, 회원가입 페이지 리뉴얼
- 메인 페이지 리뉴얼
- 지도 페이지 및 카카오 맵 적용
##### etc
- 프로젝트 내 사용되는 이미지(로고, 마커 등) 제작
- WBS
- 테이블 정의서
- 프로젝트 설계서
- 시연 영상
- 발표 자료
- 클래스 다이어그램
- readme

#### 팀원: 홍도완
##### BackEnd
- JWT 기반 인증시스템
- 로그인, 회원가입, 게시글 작성 기능
- 코스 기능 개발 (경로 산출 등)
- 챗봇 기능 개발
##### FrontEnd
- 메인 페이지, 로그인, 회원가입 초기 화면
- 게시글 및 게시글 작성 페이지
- 로그인된 유저 관리
##### etc
- ERD 다이어그램
- 유스케이스 다이어그램
- 발표 자료
- readme

## 주요 프로젝트 구조 - FrontEnd
```
📦src
 ┣ 📂assets
 ┃ ┣ 📂loginImg
 ┃ ┃ ┣ 📜login1.jpg
 ┃ ┃ ┣ 📜login10.jpg
 ┃ ┃ ┣ 📜login11.jpg
 ┃ ┃ ┣ 📜login12.jpg
 ┃ ┃ ┣ 📜login13.jpg
 ┃ ┃ ┣ 📜login14.jpg
 ┃ ┃ ┣ 📜login15.jpg
 ┃ ┃ ┣ 📜login16.jpg
 ┃ ┃ ┣ 📜login17.jpg
 ┃ ┃ ┣ 📜login18.jpg
 ┃ ┃ ┣ 📜login19.jpg
 ┃ ┃ ┣ 📜login2.jpg
 ┃ ┃ ┣ 📜login3.jpg
 ┃ ┃ ┣ 📜login4.jpg
 ┃ ┃ ┣ 📜login5.jpg
 ┃ ┃ ┣ 📜login6.jpg
 ┃ ┃ ┣ 📜login7.jpg
 ┃ ┃ ┣ 📜login8.jpg
 ┃ ┃ ┗ 📜login9.jpg
 ┃ ┣ 📂MainPageImg
 ┃ ┃ ┣ 📜mapImg.png
 ┃ ┃ ┗ 📜noticeImg.png
 ┃ ┣ 📂markers
 ┃ ┃ ┣ 📜attraction.png
 ┃ ┃ ┣ 📜course.png
 ┃ ┃ ┣ 📜culture.png
 ┃ ┃ ┣ 📜festival.png
 ┃ ┃ ┣ 📜hotel.png
 ┃ ┃ ┣ 📜restaurant.png
 ┃ ┃ ┣ 📜shopping.png
 ┃ ┃ ┗ 📜sports.png
 ┃ ┣ 📜github.png
 ┃ ┣ 📜logo.png
 ┃ ┣ 📜logo.svg
 ┃ ┣ 📜logo_nobg.png
 ┃ ┣ 📜noImage.jpg
 ┃ ┗ 📜tailwind.css
 ┣ 📂components
 ┃ ┣ 📂main
 ┃ ┃ ┣ 📂NoticeList
 ┃ ┃ ┃ ┣ 📜NoticeLink.vue
 ┃ ┃ ┃ ┗ 📜NoticeList.vue
 ┃ ┃ ┣ 📜MainFooter.vue
 ┃ ┃ ┣ 📜MainHeader.vue
 ┃ ┃ ┗ 📜MainPageBody.vue
 ┃ ┣ 📂map
 ┃ ┃ ┣ 📂AI
 ┃ ┃ ┃ ┗ 📜AIChat.vue
 ┃ ┃ ┣ 📂sidebar
 ┃ ┃ ┃ ┣ 📂bookmark
 ┃ ┃ ┃ ┃ ┣ 📜SidebarBookmark.vue
 ┃ ┃ ┃ ┃ ┗ 📜SidebarBookmarkCard.vue
 ┃ ┃ ┃ ┣ 📂course
 ┃ ┃ ┃ ┃ ┣ 📜SidebarCourse.vue
 ┃ ┃ ┃ ┃ ┣ 📜SidebarCourseCard.vue
 ┃ ┃ ┃ ┃ ┣ 📜SidebarCourseDetail.vue
 ┃ ┃ ┃ ┃ ┗ 📜SidebarCourseDetailCard.vue
 ┃ ┃ ┃ ┗ 📂search
 ┃ ┃ ┃ ┃ ┣ 📜SidebarSearch.vue
 ┃ ┃ ┃ ┃ ┣ 📜SidebarSearchCategory.vue
 ┃ ┃ ┃ ┃ ┣ 📜SidebarSearchRegion.vue
 ┃ ┃ ┃ ┃ ┣ 📜SidebarSearchSido.vue
 ┃ ┃ ┃ ┃ ┗ 📜SidebarSearchSigungu.vue
 ┃ ┃ ┣ 📜MapBigSidebar.vue
 ┃ ┃ ┣ 📜MapCustomOverlayInfo.vue
 ┃ ┃ ┣ 📜MapDetailModal.vue
 ┃ ┃ ┣ 📜MapMap.vue
 ┃ ┃ ┣ 📜MapMenu.vue
 ┃ ┃ ┗ 📜MapSidebar.vue
 ┃ ┣ 📂NoticePage
 ┃ ┃ ┣ 📜NoticeComment.vue
 ┃ ┃ ┣ 📜NoticeCommentList.vue
 ┃ ┃ ┣ 📜NoticeList.vue
 ┃ ┃ ┗ 📜NoticeListItem.vue
 ┃ ┣ 📂QnA
 ┃ ┃ ┣ 📜QnaList.vue
 ┃ ┃ ┗ 📜QnaListItem.vue
 ┃ ┣ 📂UserCourseList
 ┃ ┃ ┣ 📜CourseItem.vue
 ┃ ┃ ┣ 📜UserCourse.vue
 ┃ ┃ ┗ 📜UserCourseList.vue
 ┃ ┣ 📜AuthLayout.vue
 ┃ ┣ 📜DatePrint.vue
 ┃ ┗ 📜SearchBar.vue
 ┣ 📂router
 ┃ ┗ 📜config.js
 ┣ 📂stores
 ┃ ┣ 📜bookmarkStore.js
 ┃ ┣ 📜common.js
 ┃ ┣ 📜courseStore.js
 ┃ ┣ 📜loginUser.js
 ┃ ┣ 📜MapRegionStore.js
 ┃ ┗ 📜useMapStore.js
 ┣ 📂views
 ┃ ┣ 📜AuthPage.vue
 ┃ ┣ 📜FindPassword.vue
 ┃ ┣ 📜LoginCard.vue
 ┃ ┣ 📜LoginPage.vue
 ┃ ┣ 📜MainPage.vue
 ┃ ┣ 📜MapPage.vue
 ┃ ┣ 📜NoticeInsertPage.vue
 ┃ ┣ 📜NoticeListPage.vue
 ┃ ┣ 📜NoticePage.vue
 ┃ ┣ 📜QnaListPage.vue
 ┃ ┣ 📜QnaPage.vue
 ┃ ┣ 📜RegisterCard.vue
 ┃ ┣ 📜RegistPage.vue
 ┃ ┗ 📜UserInfo.vue
 ┣ 📜App.vue
 ┗ 📜main.js
 ```

## 주요 프로젝트 구조 - BackEnd
```
📦src
 ┣ 📂main
 ┃ ┣ 📂java
 ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┗ 📂ssafy
 ┃ ┃ ┃ ┃ ┗ 📂project
 ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜APISecurityConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChatConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomSecurityConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomUserDetailsService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SecurityConfig.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BookMarkControllerRest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChatControllerRest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CourseController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜GlobalLoginUserAdvice.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MapControllerRest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜NoticeControllerRest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserControllerRest.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂Filter
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜JWTAuthenticationFilter.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜JWTVerificationFilter.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SecurityExceptionHandlingFilter.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AttractionsDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BookMarkDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CourseDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜GugunsDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜NoticeDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SidosDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserDao.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Attractions.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BookMark.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BookMarkAi.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Comment.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentAi.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Course.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CourseAi.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CourseDetail.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CourseDetailAi.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Guguns.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜GugunsAi.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Notice.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜NoticeAi.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ResultDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Sidos.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SidosAi.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜User.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserAi.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AttractionsService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BookMarkService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CommentService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CourseService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜MapService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserService.java
 ┃ ┃ ┃ ┃ ┃ ┣ 📂util
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChosungSearch.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ChosungSearchData.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomUserDetails.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜JWTUtil.java
 ┃ ┃ ┃ ┃ ┃ ┗ 📜SsafyProjectApplication.java
 ┃ ┣ 📂resources
 ┃ ┃ ┣ 📂db
 ┃ ┃ ┃ ┣ 📂map
 ┃ ┃ ┃ ┃ ┗ 📜ssafytrip.sql
 ┃ ┃ ┃ ┗ 📂user
 ┃ ┃ ┃ ┃ ┗ 📜user.sql
 ┃ ┃ ┣ 📂mappers
 ┃ ┃ ┃ ┣ 📜Attractions.xml
 ┃ ┃ ┃ ┣ 📜BookMark.xml
 ┃ ┃ ┃ ┣ 📜Comment.xml
 ┃ ┃ ┃ ┣ 📜Course.xml
 ┃ ┃ ┃ ┣ 📜Guguns.xml
 ┃ ┃ ┃ ┣ 📜Notice.xml
 ┃ ┃ ┃ ┣ 📜Sidos.xml
 ┃ ┃ ┃ ┗ 📜User.xml
 ┃ ┃ ┗ 📜application.properties
 ```
 
## ERD 다이어그램
![ERD.png](/diagram/ERD.png)

## 유스케이스 다이어그램
![Usecase.png](/diagram/Usecase.png)

## 클래스 다이어그램
#####  그래프 크기 상 두 섹션으로 분할
#### Main
![uml_main.png](/diagram/UML/uml_main.png)

#### Maps
![uml_maps.png](/diagram/UML/uml_maps.png)

---
## 기능

#### 회원 관리 및 기본 기능
**1. 회원가입**

![회원가입.gif](/gif/회원가입.gif)
- 요구하는 입력란을 모두 입력 시 회원 가입이 가능하다.

**2. 로그인**

![로그인.gif](/gif/로그인.gif)
- 회원가입된 아이디와 올바른 비밀번호를 입력하면 로그인이 가능하다.

**3. 페이지 이동**

![메인화면_페이지이동.gif](/gif/메인화면_페이지이동.gif)
- 로그인 후 여러 페이지를 이동할 수 있다.

#### 지도
**1. 검색기능**

![검색기능.gif](/gif/검색기능.gif)
- 지도에서 다음과 같은 내용으로 필터링하여 관광지를 검색할 수 있다.
- 입력 또는 선택하지 않은 필터는 범위를 전체로 하여 검색한다.
   - 제목
   - 지역
      - 시도: 행정구역 기반으로 특별시, 광역시, 특별자치시, 도, 특별자치도를 포함한다.
      - 시군구: 시도를 선택해야만 선택이 가능하며 해당 시도의 시군구를 포함한다.
   - 관광 타입
      - 관광지
      - 문화시설
      - 축제공연행사
      - 여행코스
      - 레포츠
      - 숙박
      - 쇼핑
      - 음식점


**4. 관광지 정보 조회 기능**

![관광지정보조회.gif](/gif/관광지정보조회.gif)
- 지도에 표시된 관광지들은 마커의 이미지로 관광 타입을 알 수 있으며, 마커를 클릭 시 인포 윈도우가 출력된다.
- 인포 윈도우에서는 빠르게 북마크를 추가하거나 상세 정보를 볼 수 있다.
- '상세 정보' 버튼을 누를 시 해당 관광지의 상세 정보 및 5일 내 기상 상태를 확인할 수 있다.
- 인포 윈도우는 '닫기' 버튼으로 닫을 수 있으며 하단 중앙의 '모든 인포 윈도우 닫기' 버튼을 누르면 지도에 열린 모든 인포윈도우를 한번에 닫을 수 있다.

**3. 북마크 기능**

![북마크기능.gif](/gif/북마크기능.gif)
- 지도에서 원하는 관광지를 북마크 추가하여 언제든 조회할 수 있다.
- 북마크는 지도의 마커를 눌러 나오는 인포윈도우나 상세 정보 모달을 통해서 추가할 수 있다.
- 북마크 탭으로 이동하면, 북마크의 위치 정보를 파악하여 지도 레벨을 자동으로 조정한다.
- 북마크 리스트에서 선택한 북마크의 위치를 조회할 수 있으며, 삭제하거나 상세 정보를 확인할 수 있다.

**4. 여행 계획 생성**

![코스생성.gif](/gif/코스생성.gif)
- 여행 계획 탭에서 새로운 여행 계획을 생성할 수 있다.
- 여행 계획 탭에서 여행 계획을 선택하면 여행 계획이 활성화된다.
- 여행 계획이 활성화 되었을 때 지도에서 관광지 마커를 누르면 나오는 인포윈도우에 '코스에 추가' 버튼이 추가된다.
- '코스에 추가' 버튼을 누르면 여행 계획에 해당 관광지가 추가된다.
- 관광지가 추가될 때마다 지도에 경로가 실시간으로 갱신된다.

**5. 여행 계획 조회**

![코스조회.gif](/gif/코스조회.gif)
- 여행 계획 탭에서 여행 계획을 선택하면 여행 계획이 활성화되고, 클릭 시 저장했던 계획을 조회할 수 있다.
- 선택된 여행 계획 탭에서 여행 계획들은 간소화되어 보여지고, 클릭 시 상세 정보가 출력된다.
- 여행 경로는 지도 상에 실제 도로를 기반으로 시각적으로 표시된다.

**6. AI검색**

![AI검색.gif](/gif/AI검색.gif)
- 지도 우측 하단의 'AI 챗봇' 버튼을 눌러 AI와 채팅을 할 수 있다.
- AI에게 관광지 추천 또는 특정 유저의 코스 조회 등을 부탁하면 해당 정보를 추천 또는 확인하여 응답한다.

#### 게시글 및 유저 여행 계획 조회
**1. 게시글입력**

![게시글입력.gif](/gif/게시글입력.gif)
- 게시판에 게시글을 입력할 수 있다.

**2. 게시글 수정**

![게시글수정.gif](/gif/게시글수정.gif)
- 본인이 작성한 게시글을 수정할 수 있다.

**3. 초성검색**

![초성검색.gif](/gif/초성검색.gif)
- 작성된 게시글을 초성으로 검색할 수 있다.

**4. 댓글**

![댓글.gif](/gif/댓글.gif)
- 등록된 게시글에 댓글을 작성할 수 있다.

**5. 댓글 및 게시글 삭제**

![댓글및게시글삭제.gif](/gif/댓글및게시글삭제.gif)
- 본인이 등록한 댓글 및 게시글을 삭제할 수 있다.

**6. 유저 정보 및 여행 계획 조회**
- 특정 유저의 정보와 여행 계획을 조회할 수 있다.

![유저정보코스1.gif](/gif/유저정보코스1.gif)
![유저정보코스2.gif](/gif/유저정보코스2.gif)
