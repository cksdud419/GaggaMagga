<template>
    <li>
        <RouterLink :to="{
            path: '/qna-content',
            query: {
                qnaId: qnaId
            }
        }">
            <div
                class="flex items-center justify-between border border-gray-300 rounded px-4 py-2 mx-6 my-1 bg-gray-100">
                <div class="flex items-center">
                    <span class="text-gray-800 font-medium mr-2">{{ title }}</span>
                    <small>{{ author }}</small>
                </div>
                <div class="flex space-x-2 text-sm text-blue-600">
                    <span>{{ formattedDate }}</span>
                    <span>{{ formattedTime }}</span>
                </div>
            </div>
        </RouterLink>
    </li>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
    title: String,
    dateTime: String, // 예: "2024-06-10T09:41:00"
    qnaId: Number,
    author: String
})

const formattedDate = computed(() => {
    const date = new Date(props.dateTime)
    return date.toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
    })
})

const formattedTime = computed(() => {
    const date = new Date(props.dateTime)
    return date.toLocaleTimeString('ko-KR', {
        hour: 'numeric',
        minute: '2-digit',
    })
})
</script>
