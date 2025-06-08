<template>
  <div class="flex flex-col items-center gap-3 p-5">
    <!-- 검색창 -->
    <div class="form-control w-70">
      <label class="label mb-2">
        <span class="label-text">검색어 입력</span>
      </label>

      <label class="input w-70 h-12">
        <svg class="h-[1em] opacity-50" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
          <g stroke-linejoin="round" stroke-linecap="round" stroke-width="2.5" fill="none" stroke="currentColor">
            <circle cx="11" cy="11" r="8"></circle>
            <path d="m21 21-4.3-4.3"></path>
          </g>
        </svg>
        <input v-model="keyword" type="search" required placeholder="검색할 키워드를 입력해주세요." />
      </label>
    </div>

    <!-- 지역 선택 -->
    <SidebarSearchRegion v-model:sido="sidoCode" v-model:gugun="gugunCode" />

    <!-- 카테고리 선택 -->
    <SidebarSearchCategory v-model:category="categoryCode" />
    <div></div>

    <!-- 검색 버튼 -->
    <button @click="searchAttractions" class="btn w-70 h-12 bg-blue-400 hover:bg-blue-500 text-white text-base">관광지 검색
    </button>

    <!-- 초기화 버튼 -->
    <button @click="resetSearch" class="btn w-70 h-12 bg-red-400 hover:bg-red-500 text-white text-base">검색 조건
      초기화</button>

  </div>
</template>

<script setup>
import SidebarSearchRegion from './SidebarSearchRegion.vue'
import SidebarSearchCategory from './SidebarSearchCategory.vue'
import { ref } from 'vue'

// pinia로 외부에서 updateMap을 사용할 수 있도록 함
import { useMapStore } from '@/stores/useMapStore'
const mapStore = useMapStore()

import { MapRegionStore } from '@/stores/MapRegionStore'
const regionStore = MapRegionStore()

// 커스텀 axios 인스턴스를 써야 함
import { useLoginUser } from '../../../../stores/loginUser.js'
const loginUser = useLoginUser()

const keyword = ref('')
const sidoCode = ref(-1)
const gugunCode = ref(-1)
const categoryCode = ref(-1)

// 관광지 검색 버튼 눌렀을 때 요청
const searchAttractions = async () => {
  const params = {
    keyword: keyword.value,
    area_code: sidoCode.value,
    si_gun_gu_code: gugunCode.value,
    content_type_id: categoryCode.value
  }

  try {
    const res = await loginUser.userAi.get('/api/map/attractions', { params })

    mapStore.updateMap(res.data.data)  // 검색한 데이터를 updateMap에 넘겨주어 지도 업데이트

  } catch (err) {
    console.error('관광지 검색 실패:', err)
  }
}

// 초기화 함수
const resetSearch = () => {
  keyword.value = ''
  sidoCode.value = -1
  gugunCode.value = -1
  categoryCode.value = -1
  regionStore.resetRegion() // 옵션 리스트도 초기화
}

defineExpose({ resetSearch });

</script>
