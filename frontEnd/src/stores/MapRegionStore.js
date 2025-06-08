import { defineStore } from 'pinia'
import { ref } from 'vue'

export const MapRegionStore = defineStore('region', () => {
  const sidos = ref([])
  const guguns = ref([])
  const selectedSidoCode = ref('')

  async function fetchSidos(userAi) {
    try {
      const res = await userAi.get('/api/map/sidos')
      sidos.value = res.data.data
    } catch (e) {
      console.error('시도 정보 불러오기 실패:', e)
    }
  }

  async function fetchGuguns(userAi, sido_code) {
    if (!sido_code) {
      guguns.value = []
      return
    }
    try {
      const res = await userAi.get('/api/map/guguns', {
        params: { sido_code },
      })
      guguns.value = res.data.data
    } catch (e) {
      console.error('시군구 정보 불러오기 실패:', e)
      guguns.value = []
    }
  }

  async function setSelectedSidoCode(userAi, code) {
    selectedSidoCode.value = code
    if (code) {
      await fetchGuguns(userAi, code)
    } else {
      guguns.value = []
    }
  }

  function resetRegion() {
    guguns.value = []
  }

  return {
    sidos,
    guguns,
    selectedSidoCode,
    fetchSidos,
    fetchGuguns,
    setSelectedSidoCode,
    resetRegion
  }
})
