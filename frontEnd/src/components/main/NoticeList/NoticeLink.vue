<template>
    <li><router-link :to="{
        path: '/notice-content', query: {
            noticeId: noticeId
        }
    }" class="text-pink-600 hover:underline cursor-pointer">    
    {{ title }}
    </router-link></li>
</template>

<script setup>
defineProps({
    noticeId: Number,
    title: String
});
</script>