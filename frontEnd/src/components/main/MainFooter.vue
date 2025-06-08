<template>
    <!-- 하단 정보 섹션 -->
    <footer class="grid grid-cols-1 md:grid-cols-3 gap-6 px-6 py-10 bg-gray-50 text-sm text-gray-700 border-t">
        <!-- 로고 및 아이콘 -->
        <div class="space-y-2">
            <img src="@/assets/logo.svg" alt="로고" class="w-16 h-16" />
            <div class="flex space-x-4 mt-2">
                <div class="flex flex-col items-center">
                    <a href="https://github.com/cksdud419">
                        <img src="@/assets/github.png" alt="정찬영" class="w-8 h-8" />
                        <span>정찬영</span>
                    </a>
                </div>
                <div class="flex flex-col items-center">
                    <a href="https://github.com/dolto">
                        <img src="@/assets/github.png" alt="홍도완" class="w-8 h-8" />
                        <span>홍도완</span>
                    </a>
                </div>
            </div>
        </div>

        <!-- 다른 사람의 여행코스 -->
        <div>
            <h3 class="font-semibold mb-2">다른 사람의 여행코스</h3>
            <UserCourseList :list="randomCourseData" />
        </div>

        <!-- 최근 게시글 -->
        <div>
            <h3 class="font-semibold mb-2">최근 게시글</h3>
            <NoticeList :list="curruntNoticeData" />
        </div>
    </footer>
</template>

<script setup>
import NoticeList from './NoticeList/NoticeList.vue';
import UserCourseList from '../UserCourseList/UserCourseList.vue';
import { useLoginUser } from '@/stores/loginUser';
import { onMounted, ref } from 'vue';

const loginUser = useLoginUser();

// 임시로이렇게 가져옴  이후 론, axios로 가져올 것
const randomCourseData = ref([]);
const getCourseRandom = async () => {
    if (!loginUser.loginUser.id)
        return [];
    const userAi = loginUser.userAi;
    try {
        const data = await userAi.get('api/course/rand');
        if(!data)
            return [];
        console.log(data.data);
        if(data.data.data.length > 10)
            data.data.data.length = 10;
        return data.data.data;
    } catch (e) {
        console.error(e);
    }
}
const getNoticeDate = async () => {
    if (!loginUser.loginUser.id)
        return [];
    const userAi = loginUser.userAi;
    try {
        const data = await userAi.get('/api/notice/list?page=0');
        console.log(data.data);
        data.data.data.length = 10;
    return data.data.data;
    } catch (e) {
        console.error(e);
    }
}
let curruntNoticeData = ref([]);

onMounted(async () => {
    curruntNoticeData.value = await getNoticeDate();
    randomCourseData.value = await getCourseRandom();
});
</script>