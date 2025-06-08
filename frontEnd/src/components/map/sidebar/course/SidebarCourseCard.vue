<template>
  <div class="card bg-base-100 w-78 shadow">
    <div class="card-body">
      <h2 class="card-title">{{ course.title }}</h2>
      <p>{{ course.des }}</p>

      <div class="card-actions justify-center gap-3">
        <div class="flex w-full justify-between gap-2">
          <button class="btn flex-1 bg-blue-400 text-white border-none hover:bg-blue-500"
            @click="$emit('open-detail', course)"
          >여행 계획 선택</button>
          <button class="btn bg-red-400 text-white border-none hover:bg-red-500"
            @click="$emit('delete-course', course.id)">계획 제거</button>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
defineProps({ course: Object });
defineEmits(['delete-course', 'open-detail']);
</script>
