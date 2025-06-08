package com.ssafy.project.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.project.model.dto.Course;
import com.ssafy.project.model.dto.CourseDetail;

@Mapper
public interface CourseDao {
	// 모든 코스 불러오기 (경로 미포함)
	List<Course> selectCourseAllAll();
	// 유저 코스 모두 불러오기 (경로 미포함)
    List<Course> selectCourseAll(String author_id);
    // 유저의 특정 코스 가져오기 (경로 포함)
    Course selectCourse(Integer id);
    // 특정 랜덤코스 id 가져오기
    int selectRandomCourseId();
    // 코스 생성
    int insertCourse(Course course);
    // 코스에 지점 추가
    int insertCourseDetail(@Param("id") Integer id, @Param("course_detail") CourseDetail detail);
    // 코스에 지점 추가(다중)
    int insertCourseDetailAll(@Param("id") Integer id, @Param("course_details") List<CourseDetail> details);
    // 코스의 특정 지점 변경
    int updateCourseDetail(@Param("id") Integer id, @Param("course_detail") CourseDetail detail);
    // 코스의 특정 지점 삭제
    int deleteCourseDetail(@Param("id") Integer id, @Param("order") Integer order);
    // 코스 삭제
    int deleteCourse(Integer id);
}
