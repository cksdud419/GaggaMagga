<template>
    <ul>
        <NoticeListItem v-for="item in list" :key="item.id" v-bind="item"/>
    </ul>
</template>

<script setup>
import NoticeListItem from './NoticeListItem.vue';

defineProps({
    list: Array
});
</script>
