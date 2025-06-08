package com.ssafy.project.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Course {
	Integer id;
	String author_id;
	String title;

	List<CourseDetail> course_details;

	// 출발지에서 목적지까지 경유지 포함 경로 배열 (카카오 모빌리티)
	List<Double> vertexes;

	public CourseAi toAi() {
		CourseAi ai = new CourseAi();
		ai.setAuthor_id(author_id);
		if (course_details != null)
			ai.setCourse_details(course_details.stream().map(i -> i.toAi()).toList());
		ai.setTitle(title);
		ai.setVertexes(vertexes);

		return ai;
	}
}