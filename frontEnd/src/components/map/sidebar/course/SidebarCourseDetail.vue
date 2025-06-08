<template>
  <div class="flex flex-col h-full">

    <!-- 상단: 고정된 여행 정보 -->
    <div class="sticky top-0 bg-base-200 z-10 p-4 flex justify-center">
      <div class="card bg-base-100 w-78 shadow p-3 flex flex-col">
        <div class="text-xl font-extrabold text-center">{{ course.title }}</div>
        <div class="text-gray-600 text-base text-center">
          총 경유지 수: {{ course.course_details?.length || 0 }}
        </div>
      </div>
    </div>

    <!-- 중단: 스크롤 가능한 경유지 목록 -->
    <div class="flex-1 overflow-y-auto px-4 py-2 flex flex-col items-center gap-4">
      <template v-if="course.course_details">
        <SidebarCourseDetailCard v-for="detail in course.course_details" :key="detail.detail_order" :detail="detail"
          @open-modal="handleOpenDetail" />
      </template>
      <template v-else>
        <!-- 스켈레톤 및 스피너 -->
        <div class="w-78 h-50 bg-gray-100 rounded-lg animate-pulse flex items-center justify-center">
          <span class="loading loading-bars loading-xl text-blue-400"></span>
        </div>

      </template>
    </div>

    <!-- 하단: 고정된 버튼 -->
    <div class="sticky bottom-0 bg-base-200 z-10 p-4 flex justify-center">
      <button class="btn bg-red-400 hover:bg-red-500 text-white text-lg border-none w-78 h-13" @click="goBack">
        여행 계획 선택 해제
      </button>
    </div>
  </div>

  <teleport to="body">
    <MapDetailModal ref="modalRef" :attraction="selectedAttraction" />
  </teleport>
</template>



<script setup>
import { computed, watch, ref } from 'vue';
import { useCourseStore } from '@/stores/courseStore';
import { useMapStore } from '@/stores/useMapStore';

import SidebarCourseDetailCard from './SidebarCourseDetailCard.vue'
import MapDetailModal from '@/components/map/MapDetailModal.vue';

const store = useCourseStore();
const mapStore = useMapStore();
const course = computed(() => store.selectedCourse);

const goBack = () => {
  store.selectedCourse = null;
};

watch(course, (val) => {
  if (val?.course_details?.length) {
    mapStore.updateMap(val.course_details);
  }
}, { immediate: true });

const selectedAttraction = ref(null);
const modalRef = ref();

const handleOpenDetail = (course) => {
  selectedAttraction.value = course;
  modalRef.value?.open();
};
</script>
