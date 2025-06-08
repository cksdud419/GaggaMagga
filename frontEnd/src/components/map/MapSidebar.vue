<template>
  <!-- 사이드바 영역 -->
  <aside class="w-90 h-full bg-base-200 shadow-lg flex flex-col border-r border-gray-300">
    <!-- 상단 타이틀 -->
    <div class="flex items-center w-full p-4 bg-blue-400 text-white font-semibold sticky top-0 z-10">
      <span class="flex-1 text-xl font-semibold ml-6">{{ sidebarTitle }}</span>

      <!-- 닫기 버튼 -->
      <button class="tooltip tooltip-right btn btn-sm btn-ghost ml-auto" data-tip="숨기기" @click="closeSidebar">
        <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 24 24" fill="none"
          stroke="currentColor" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"
          class="icon icon-tabler icons-tabler-outline icon-tabler-arrow-bar-left">
          <path stroke="none" d="M0 0h24v24H0z" fill="none" />
          <path d="M4 12l10 0" />
          <path d="M4 12l4 4" />
          <path d="M4 12l4 -4" />
          <path d="M20 4l0 16" />
        </svg>
      </button>
    </div>

    <!-- 선택된 사이드바 기능을 불러옴 -->
    <div class="flex-1 overflow-auto no-scrollbar">
      <component :is="currentComponent"/>
    </div>
  </aside>
</template>

<script setup>
import { computed } from 'vue'

// 사이드바 메뉴들
import SidebarSearch from './sidebar/search/SidebarSearch.vue'
import SidebarBookmark from './sidebar/bookmark/SidebarBookmark.vue'
import SidebarCourse from './sidebar/course/SidebarCourse.vue'

// active: 어떤 기능(컴포넌트)를 보여줄지 결정
const props = defineProps(['active'])

const emit = defineEmits(['closeSidebar'])

// 각 메뉴에 따라 타이틀 반환
const sidebarTitle = computed(() => {
  switch (props.active) {
    case 'bookmark': return '북마크'
    case 'course': return '여행 계획'
    case 'search':
    default: return '관광지 검색'
  }
})

// active값에 따라 현재 사이드바에 보여줄 컴포넌트 동적 선택
const currentComponent = computed(() => {
  switch (props.active) {
    case 'bookmark': return SidebarBookmark
    case 'course': return SidebarCourse

    case 'search':
    default:
      return SidebarSearch  // 기본은 검색
  }
})

// 닫기 활성화
function closeSidebar() {
  emit('closeSidebar')
}

</script>
