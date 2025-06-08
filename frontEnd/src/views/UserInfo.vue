<template>
    <MainHeader />
    <div class="hero bg-base-200 min-h-screen pb-100">
        <div class="hero-content flex-col lg:flex-row-reverse min-w-[800px]">
            <div class="flex flex-col justify-center bg-base-100 w-full min-h-[800px] shrink-0 shadow-2xl">
                <div class="flex flex-col gap-2 p-5">
                    <p>
                        <strong>ID: </strong>
                        <span>{{ user.id }}</span>
                    </p>
                    <p>
                        <strong>Email: </strong>
                        <span>{{ user.email }}</span>
                    </p>
                    <p>
                        <strong>NickName: </strong>
                        <span>{{ user.nickName }}</span>
                    </p>
                </div>
                <hr class="border-gray-300">
                <div class="flex flex-col p-5">
                    <strong>Course</strong>
                    <ul>
                        <CourseItem v-for="item in user.courses" :course="item" :key="item.id"/>
                    </ul>
                    <strong>Posts</strong>
                    <NoticeList :list="user.notices" />
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import MainHeader from '@/components/main/MainHeader.vue';
import NoticeList from '@/components/NoticePage/NoticeList.vue';
import CourseItem from '@/components/UserCourseList/CourseItem.vue';
import { useLoginUser } from '@/stores/loginUser';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const loginUser = useLoginUser();

const userId = route.params.id;
// 임시 데이터
const getData = async (id) => {
    const userAi = loginUser.userAi;
    const data = await userAi.get('/api/user/'+id);
    console.log(data.data);
    return data.data.data;
}

const user = ref({});
onMounted(async () => {
    user.value = await getData(userId);
    console.log(user.value);
})
</script>