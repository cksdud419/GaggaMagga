<template>
  <div class="form-control w-70">
    <label class="label mb-2">
      <span class="label-text">관광 타입 선택</span>
    </label>
    <select class="select w-70 h-12" :value="props.category" @change="onCategoryChange">
      <option value="-1" selected>전체</option>
      <option value="12">관광지</option>
      <option value="14">문화시설</option>
      <option value="15">축제공연행사</option>
      <option value="25">여행코스</option>
      <option value="28">레포츠</option>
      <option value="32">숙박</option>
      <option value="38">쇼핑</option>
      <option value="39">음식점</option>
    </select>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
const selectedCategory = ref(-1)

const emit = defineEmits(['update:category'])

const props = defineProps({
  category: Number
})

watch(selectedCategory, (newVal) => {
  emit('update:category', newVal)
})

const onCategoryChange = (event) => {
  const newVal = parseInt(event.target.value)
  emit('update:category', newVal)
}
</script>

<style scoped></style>
