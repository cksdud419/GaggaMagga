<template>
    <ul class="space-y-1">
        <NoticeLink v-for="item in list" :key="item.id" :notice-id="item.id" :title="item.title"/>
    </ul>
</template>

<script setup>
import NoticeLink from './NoticeLink.vue';

defineProps({
    list: Array
});
</script>
