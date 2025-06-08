<template>
    <ul v-if="list" class="space-y-1">
        <li>
            <strong class="mr-2">{{ list.title }}</strong>
            <router-link :to="{
                path: '/map', query: {
                    courseId: list.id
                }
            }" class="text-pink-600 hover:underline cursor-pointer">보러가기</router-link>

        </li>
        <UserCourse v-for="item in list.course_details" :key="item.title" :title="item.title" />
    </ul>
</template>

<script setup>
import UserCourse from './UserCourse.vue';

const props = defineProps({
    list: Object,
});

console.log(props);
</script>