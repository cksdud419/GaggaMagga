<template>
    <ul>
        <QnaListItem v-for="item in list" :key="item.qnaId" v-bind="item"/>
    </ul>
</template>

<script setup>
import QnaListItem from './QnaListItem.vue';

defineProps({
    list: Array
});
</script>
