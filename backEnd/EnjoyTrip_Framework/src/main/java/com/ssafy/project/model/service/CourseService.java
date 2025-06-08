package com.ssafy.project.model.service;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import com.ssafy.project.model.dao.CourseDao;
import com.ssafy.project.model.dto.Course;
import com.ssafy.project.model.dto.CourseAi;
import com.ssafy.project.model.dto.CourseDetail;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
	private final CourseDao courseDao;

	public List<Course> selectCourseAllAll() {
		try {
			return courseDao.selectCourseAllAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Tool(description = "모든 여행 코스를 조회합니다. 각 코스는 제목만 알 수 있고, 세부정보는 알 수 없습니다. 특정 지역이나 주제에대한 코스인지 확인하기 위해, selectCourseAi를 이용하여 상세정보를 통해 알아냅니다.")
	private List<CourseAi> selectCourseAiAllAll() {
		List<Course> list = selectCourseAllAll();
		if (list == null)
			return null;
		return list.stream().map(i -> i.toAi()).toList();
	}

	public List<Course> selectCourseAll(String author_id) {
		try {
			return courseDao.selectCourseAll(author_id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Tool(description = "지정된 사용자 ID에 해당하는 모든 여행 코스를 조회합니다. 각 코스는 제목만 알 수 있고, 세부정보는 알 수 없습니다.")
	private List<CourseAi> selectCourseAiAll(String author_id) {
		List<Course> list = selectCourseAll(author_id);
		if (list == null)
			return null;
		return list.stream().map(i -> i.toAi()).toList();
	}

	public Course selectCourse(Integer id) {
		try {
			return courseDao.selectCourse(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Tool(description = "지정한 ID에 해당하는 여행 코스의 상세 정보를 조회합니다. 코스에는 출발지부터 도착지까지의 경로 정보인 경유지 상세 정보가 포함됩니다. 이를 통해 위치나 테마(예: 산책, 서울 등)를 파악할 수 있습니다.")
	private CourseAi selectCourseAi(Integer id) {
		Course c = selectCourse(id);
		if (c == null) {
			return null;
		}
		return c.toAi();
	}

	public Course selectRandomCourse() {
		try {
			return courseDao.selectCourse(courseDao.selectRandomCourseId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Tool(description = "랜덤 여행코스의 상세정보를 조회합니다. 코스에는 출발지부터 도착지까지의 경로정보인 경유지 상세정보가 포함됩니다.")
	private CourseAi selectRandomCourseAi() {
		Course rand = selectRandomCourse();

		if (rand == null)
			return null;
		return rand.toAi();
	}

	public Integer insertCourse(Course course) {
		try {
			return courseDao.insertCourse(course);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Integer insertCourseDetail(Integer id, CourseDetail detail) {
		try {
			return courseDao.insertCourseDetail(id, detail);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Integer insertCourseDetailAll(Integer id, List<CourseDetail> details) {
		try {
			return courseDao.insertCourseDetailAll(id, details);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Integer updateCourseDetail(Integer id, CourseDetail detail) {
		try {
			return courseDao.updateCourseDetail(id, detail);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Integer deleteCourseDetail(Integer id, Integer detailId) {
		try {
			return courseDao.deleteCourseDetail(id, detailId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Integer deleteCourse(Integer id) {
		try {
			return courseDao.deleteCourse(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
