import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useMapStore = defineStore('map', () => {
  const attractions = ref([])
  const map = ref(null) // 지도 객체
  const markerMap = ref(new Map()) // id → { marker, overlay }

  // 지도 업데이트
  const updateMap = (data) => {
    attractions.value = data
  }

  const keyword = ref('')
  const sidoCode = ref(-1)
  const gugunCode = ref(-1)
  const categoryCode = ref(-1)

  // 검색 내역 초기화
  const resetSearch = () => {
    keyword.value = ''
    sidoCode.value = -1
    gugunCode.value = -1
    categoryCode.value = -1
  }

  const setMapInstance = (mapInstance) => {
    map.value = mapInstance
  }

  const registerMarker = (id, marker, overlay) => {
    markerMap.value.set(id, { marker, overlay })
  }

  const focusMarker = (id) => {
    const entry = markerMap.value.get(id)
    if (!entry || !map.value) return
    entry.overlay.setMap(map.value)
    map.value.panTo(entry.marker.getPosition())
  }

  return {
    attractions,
    updateMap,
    keyword,
    sidoCode,
    gugunCode,
    categoryCode,
    resetSearch,
    setMapInstance,
    registerMarker,
    focusMarker,
  }
})
