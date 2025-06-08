<template>
  <div class="flex h-screen relative">
    <MapMenu class="relative z-[999]" @toggle-sidebar="toggleSidebar" />

    <!-- 기존 사이드바 -->
    <transition name="slide-left">
      <MapSidebar v-if="sidebarOpen && sidebarType === 'normal'" :active="activeSidebar"
        @closeSidebar="toggleSidebar('')" class="absolute top-0 left-18 h-full z-10" />
    </transition>

    <!-- 확장 사이드바 -->
    <transition name="slide-big">
      <MapBigSidebar v-if="sidebarOpen && sidebarType === 'big'" :active="activeSidebar"
        @closeSidebar="toggleSidebar('')" class="absolute top-0 left-18 h-full z-20" />
    </transition>

    <MapMap />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

import MapMenu from '@/components/map/MapMenu.vue'
import MapSidebar from '@/components/map/MapSidebar.vue'
import MapBigSidebar from '@/components/map/MapBigSidebar.vue'
import MapMap from '@/components/map/MapMap.vue'

import { useMapStore } from '@/stores/useMapStore'
import { bookmarkStore } from '@/stores/bookmarkStore'
import { MapRegionStore } from '@/stores/MapRegionStore'
import { useCourseStore } from '@/stores/courseStore'

// 상태
const sidebarOpen = ref(true)
const sidebarType = ref('normal') // 'normal' | 'big'
const activeSidebar = ref('search') // 초기값

// 사이드바 열고 닫기 토글
function toggleSidebar(name) {
  const bigPanels = ['notice', 'someOtherBigPanel']

  if (name === '') {
    sidebarOpen.value = false
    sidebarType.value = 'normal'
    activeSidebar.value = ''
    return
  }

  const isBig = bigPanels.includes(name)
  const isSame = activeSidebar.value === name

  activeSidebar.value = name
  sidebarType.value = isBig ? 'big' : 'normal'

  sidebarOpen.value = isSame && sidebarOpen.value ? false : true
}

// 초기화
const mapStore = useMapStore()
const bmStore = bookmarkStore()
const regionStore = MapRegionStore()
const courseStore = useCourseStore()

onMounted(() => {
  mapStore.updateMap([])
  mapStore.resetSearch()
  regionStore.resetRegion()
  bmStore.bookmarks = []
  courseStore.resetCourse()
})
</script>

<style scoped>
.slide-left-enter-from {
  transform: translateX(-100%);
}

.slide-left-enter-active {
  transition: transform 0.3s ease-in-out;
}

.slide-left-enter-to {
  transform: translateX(0%);
}

.slide-left-leave-from {
  transform: translateX(0%);
}

.slide-left-leave-active {
  transition: transform 0.3s ease-in-out;
}

.slide-left-leave-to {
  transform: translateX(-100%);
}

.slide-big-enter-from {
  transform: translateX(-100%);
}

.slide-big-enter-active {
  transition: transform 0.3s ease-in-out;
}

.slide-big-enter-to {
  transform: translateX(0%);
}

.slide-big-leave-from {
  transform: translateX(0%);
}

.slide-big-leave-active {
  transition: transform 0.3s ease-in-out;
}

.slide-big-leave-to {
  transform: translateX(-100%);
}
</style>
