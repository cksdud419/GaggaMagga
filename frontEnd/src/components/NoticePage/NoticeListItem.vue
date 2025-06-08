<template>
    <li>
        <RouterLink :to="{
            path: '/notice-content',
            query: {
                noticeId: id
            }
        }">
            <div
                class="flex items-center justify-between border border-gray-300 rounded px-4 py-2 mx-6 my-1 bg-gray-100">
                <div class="flex items-center">
                    <span class="text-gray-800 font-medium mr-2 text-ellipsis">{{ title }}</span>
                    <small>| {{ authorId }}</small>
                    <small v-show="commentsCount" class="bg-gray-500 text-white rounded-xl px-2 ml-2">{{ commentsCount }}</small>
                </div>
                <div class="flex space-x-2 text-sm text-blue-600">
                    <DatePrint :date="createdAt" />
                </div>
            </div>
        </RouterLink>
    </li>
</template>

<script setup>
import DatePrint from '../DatePrint.vue'

defineProps({
    title: String,
    createdAt: String,
    id: Number,
    authorId: String,
    commentsCount: Number
})
</script>
