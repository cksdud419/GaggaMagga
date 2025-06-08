import { defineStore } from 'pinia'
import { ref } from 'vue'
import { useLoginUser } from '@/stores/loginUser'

export const useCourseStore = defineStore('course', () => {
  const courses = ref([])
  const selectedCourse = ref(null)
  const vertexes = ref([])

  const loginUser = useLoginUser()

  const fetchCourses = async () => {
    try {
      const res = await loginUser.userAi.get(`/api/course/all/${loginUser.loginUser.id}`)
      courses.value = res.data.data
      console.log('코스 목록 불러옴')
    } catch (e) {
      console.error('코스 목록 불러오기 실패:', e)
    }
  }

  const createCourse = async (title) => {
    try {
      await loginUser.userAi.post('/api/course', {
        title,
        author_id: loginUser.loginUser.id,
      })
      await fetchCourses() // 생성 후 목록 갱신
    } catch (e) {
      console.error('코스 생성 실패:', e)
    }
  }

  const deleteCourse = async (id) => {
    try {
      await loginUser.userAi.delete(`/api/course/${id}`)
      courses.value = courses.value.filter((c) => c.id !== id) // 로컬에서 바로 제거
    } catch (e) {
      console.error('코스 삭제 실패:', e)
    }
  }

  const fetchCourseDetail = async (id) => {
    try {
      const res = await loginUser.userAi.get(`/api/course/${id}`)
      selectedCourse.value = res.data.data
      vertexes.value = res.data.data.vertexes || []
      console.log("코스 상세 조회 성공")
    } catch (e) {
      console.error('코스 상세 조회 실패:', e)
    }
  }

  const addCourseDetail = async ({ course_id, attractions_id }) => {
    try {
      await loginUser.userAi.post(`/api/course/${course_id}/detail`, {
        course_id,
        attractions_id,
        detail_order: null,
      })
    } catch (e) {
      console.error('코스 디테일 추가 실패:', e)
      throw e
    }
  }

  const deleteCourseDetail = async ( course_id, detail_order ) => {
    try {
      const { data } = await loginUser.userAi.delete(`/api/course/${course_id}/detail/${detail_order}`)
      return data.data // 성공 여부 반환
    } catch (e) {
      console.error('코스 디테일 삭제 실패', e)
      throw e
    }
  }

  // 지도 나갔다 들어올 때 선택된 여행 계획 초기화 하기 위함
  const resetCourse = () => {
    selectedCourse.value = null
  }

  return {
    courses,
    fetchCourses,
    selectedCourse,
    vertexes,
    createCourse,
    deleteCourse,
    fetchCourseDetail,
    addCourseDetail,
    resetCourse,
    deleteCourseDetail,
  }
})
