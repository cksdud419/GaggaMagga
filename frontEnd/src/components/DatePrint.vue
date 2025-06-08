<template>
    <span class="rounded-xl bg-blue-100 p-1 px-2 mr-1 text-blue-700">{{ formattedDate }}</span>
    <span class="rounded-xl bg-blue-100 p-1 px-2 text-blue-700">{{ formattedTime }}</span>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
    date: String
})

const formattedDate = computed(() => {
  const date = new Date(props.date)
  return `${date.getUTCFullYear()}년 ${date.getUTCMonth() + 1}월 ${date.getUTCDate()}일`
})

const formattedTime = computed(() => {
  const date = new Date(props.date)
  const hour = date.getUTCHours().toString().padStart(2, '0')
  const minute = date.getUTCMinutes().toString().padStart(2, '0')
  return `${hour}시${minute}분`
})
</script>