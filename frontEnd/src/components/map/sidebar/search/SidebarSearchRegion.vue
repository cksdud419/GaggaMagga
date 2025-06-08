<template>
  <!-- 시도 선택 -->
  <div class="form-control w-70">
    <label class="label mb-2">
      <span class="label-text"> 지역 선택</span>
    </label>
    <select class="select w-70 h-12" :value="props.sido" @change="onSidoChange">
      <option value="-1">전체 지역</option>
      <option v-for="sido in regionStore.sidos" :key="sido.no" :value="sido.sido_code">
        {{ sido.sido_name }}
      </option>
    </select>
  </div>


  <!-- 시군구 선택 -->
  <div class="form-control w-70">
    <label class="label mb-2">
      <span class="label-text">상세 지역 선택</span>
    </label>
    <select class="select w-70 h-12" :value="props.gugun" @change="onGugunChange">
      <option value="-1">전체 시군구</option>
      <option v-for="gugun in regionStore.guguns" :key="gugun.no" :value="gugun.gugun_code">
        {{ gugun.gugun_name }}
      </option>
    </select>
  </div>
</template>

<!-- ======================================================================== -->
<script setup>
import { onMounted, watch } from 'vue'
import { MapRegionStore } from '../../../../stores/MapRegionStore.js'
import { useLoginUser } from '../../../../stores/loginUser.js'
const loginUser = useLoginUser()
const regionStore = MapRegionStore()

onMounted(() => {
  regionStore.fetchSidos(loginUser.userAi)
})

// selectedSidoCode가 바뀌면 시군구도 자동으로 fetch
watch(() => regionStore.selectedSidoCode, (newVal) => {
  regionStore.setSelectedSidoCode(loginUser.userAi, newVal)

  emit('update:sido', newVal) // 시도 변경될 때 emit

  // 시도가 바뀌면 시군구 초기화 (강제로 -1 설정 -> 기존에 선택된 시군구 코드는 쓸 수 없기 때문)
  regionStore.selectedGugunCode = -1
  emit('update:gugun', -1)
})

// 시군구 변경 시 emit
watch(() => regionStore.selectedGugunCode, (newVal) => {
  emit('update:gugun', newVal)
})

const emit = defineEmits(['update:sido', 'update:gugun'])

const props = defineProps({
  sido: Number,
  gugun: Number
})

const onSidoChange = (event) => {
  const newVal = parseInt(event.target.value)
  emit('update:sido', newVal)
  regionStore.setSelectedSidoCode(loginUser.userAi, newVal)
  regionStore.selectedGugunCode = -1
  emit('update:gugun', -1)
}

const onGugunChange = (event) => {
  const newVal = parseInt(event.target.value)
  emit('update:gugun', newVal)
  regionStore.selectedGugunCode = newVal
}


</script>

<style scoped>
select:disabled {
  background-color: #f0f0f0;
  color: #666666;
  border-color: #ccc;
  cursor: not-allowed;
}
</style>
