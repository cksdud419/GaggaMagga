<template>
    <MainHeader />
    <main class="flex flex-col justify-center items-center w-full">
        <section class="p-4 m-5 border-light rounded-xl bg-gray-50 text-gray-800 w-full max-w-[800px]">
            <h3 v-if="!isUpdate" class="mb-4 text-xl">{{ notice.title }}</h3>
            <input type="text" v-else v-model="notice.title" placeholder="제목을 입력하세요"
                class="p-2 mb-4 text-xl border-b-1 w-full bg-gray-200 rounded-xl shadow-sm" />

            <div class="mb-2">
                <span>게시자: </span>
                <RouterLink :to="{ name: 'userInfo', params: { 'id': notice.authorId ? notice.authorId : 'loading' } }"
                    class="cursor-pointer hover:underline font-bold">{{ notice.authorId }}</RouterLink>
            </div>
            <div v-if="!isUpdate" class="whitespace-pre-wrap bg-gray-200 p-3 rounded-xl mb-3 ">{{ notice.content }}</div>
            <textarea v-else v-model="notice.content"
                class="whitespace-pre bg-gray-200 p-3 rounded-xl mb-3 w-full h-auto">
            </textarea>
            <article class="flex justify-end w-full">
                <DatePrint :date="notice.createdAt" />
            </article>

        </section>
        <section v-if="!isUpdate" class="max-w-[800px] w-full mb-2">
            <button
                @click="router.back()"
                class="border border-gray-300 rounded-xl w-full bg-gray-100 hover:bg-gray-500 hover:text-gray-50 cursor-pointer">
                게시글 목록으로
            </button>
        </section>
        <section v-if="isMe" class="flex flex-column w-full justify-center gap-1 max-w-[800px]">
            <button @click="updateNotice"
                class="shadow-sm cursor-pointer border border-gray-300 rounded-xl w-full bg-gray-100 hover:bg-gray-500 hover:text-gray-50">
                수정</button>
            <button @click="deleteNotice" v-if="!isUpdate"
                class="shadow-sm cursor-pointer border border-gray-300 rounded-xl w-full bg-gray-950 text-gray-50 hover:bg-gray-500 hover:text-gray-50">
                삭제</button>
            <button @click="isUpdate = false" v-else
                class="shadow-sm cursor-pointer border border-gray-300 rounded-xl w-full bg-gray-950 text-gray-50 hover:bg-gray-500 hover:text-gray-50">
                취소</button>
        </section>

        <template v-if="!isUpdate">
            <NoticeCommentList :list="notice.comments" @update="getNotice(route.query.noticeId)" />
            <article class="p-2 max-w-[800px] w-full border rounded-xl border-gray-100 shadow-sm mt-1">
                <strong class="p-2">댓글 입력</strong>
                <hr class="border-gray-300 my-2">
                <textarea v-model="comment" class="p-2 w-full resize-y rounded-xl bg-gray-200"></textarea>
                <div class="w-full flex justify-end">
                    <button @click="insertComment()"
                        class="rounded-xl w-30 shadow-sm bg-gray-300 hover:bg-gray-800 hover:text-white cursor-pointer">입력</button>
                </div>
            </article>
        </template>
    </main>
</template>

<script setup>
import DatePrint from '@/components/DatePrint.vue';
import MainHeader from '@/components/main/MainHeader.vue';
import NoticeCommentList from '@/components/NoticePage/NoticeCommentList.vue';
import { useLoginUser } from '@/stores/loginUser';
import { computed, ref } from 'vue';
import { RouterLink, useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();
const loginUser = useLoginUser();
const comment = ref('');
const isUpdate = ref(false);

// 임시 데이터 가져오기
const getNotice = async (id) => {
    try {
        const userAi = loginUser.userAi;
        const data = await userAi.get(`/api/notice/${id}`);
        console.log(data.data);
        notice.value = data.data.data;
    } catch (e) {
        console.error("게시글 데이터 가져오기 실패!", e);
    }
}
const notice = ref(getNotice(route.query.noticeId));
const isMe = computed(() => {
    return loginUser.loginUser.id === notice.value.authorId;
});

const insertComment = async () => {
    if (comment.value) {
        try {
            const userAi = loginUser.userAi;
            const user = loginUser.loginUser;
            await userAi.post(`/api/notice/comment/insert`, {
                author_id: user.id,
                notice_id: notice.value.id,
                content: comment.value
            });
            comment.value = '';
            getNotice(route.query.noticeId);
        } catch (e) {
            console.error("댓글 입력 실패!", e);
        }
    } else {
        alert("댓글을 입력해주세요!");
    }
}

const updateNotice = async () => {
    if (isUpdate.value) {
        const userAi = loginUser.userAi;
        try {
            await userAi.post('/api/notice', {
                ...notice.value
            });
            isUpdate.value = false;
            getNotice(route.query.noticeId);
        } catch (e) {
            console.error('수정 실패!', e);
        }
    } else {
        isUpdate.value = true;
    }
}
const deleteNotice = async () => {
    const userAi = loginUser.userAi;
    try {
        await userAi.post('/api/notice/delete/' + notice.value.id);
        router.push({ name: 'noticeList' });
    } catch (e) {
        console.error('게시글 삭제 실패', e);
    }
}
</script>
