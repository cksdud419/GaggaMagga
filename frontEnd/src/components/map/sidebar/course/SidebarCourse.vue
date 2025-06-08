<template>
  <div v-if="selectedCourse">
    <SidebarCourseDetail />
  </div>
  <div v-else>
    <div class="flex flex-col items-center gap-4 p-4">
      <input v-model="newTitle" placeholder="새 여행 계획 이름을 입력하세요." class="input input-bordered w-78" />
      <button class="btn btn-primary w-78" @click="handleCreateCourse">여행 계획 생성</button>
      <div class="divider text-gray-400">계획 목록</div>

      <SidebarCourseCard v-for="course in courseStore.courses" :key="course.id" :course="course"
        @delete-course="handleDeleteCourse"
        @open-detail="handleOpenDetail"
        class="shadow-md" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useCourseStore } from '@/stores/courseStore';
import SidebarCourseCard from './SidebarCourseCard.vue';
import SidebarCourseDetail from './SidebarCourseDetail.vue'

const courseStore = useCourseStore();
const newTitle = ref('');

const handleCreateCourse = async () => {
  if (newTitle.value.trim() === '') return;
  await courseStore.createCourse(newTitle.value);
  newTitle.value = '';
};

const handleDeleteCourse = async (id) => {
  await courseStore.deleteCourse(id);
};

// 코스 선택
const selectedCourse = computed(() => courseStore.selectedCourse);

const handleOpenDetail = async (course) => {
  courseStore.selectedCourse = { ...course, course_details: null }; // 먼저 화면 전환
  await courseStore.fetchCourseDetail(course.id); // 상세 정보는 업데이트로 늦게
};

onMounted(() => {
  courseStore.fetchCourses();
});
</script>
