<template>
    <section class="flex flex-col mt-2 bg-gray-200 p-3 rounded-xl gap-1 w-full max-w-[800px] min-h-15">
        <strong class="pl-1">댓글</strong>
        <hr class="border-gray-300">
        <NoticeComment v-for="item in list" :key="item.id" @update="onUpdate" v-bind="item"/>
    </section>
</template>

<script setup>
import NoticeComment from './NoticeComment.vue';
defineProps({
    list: Array,
})

const emits = defineEmits(['update']);
const onUpdate = () => {
    emits('update');
}
</script>