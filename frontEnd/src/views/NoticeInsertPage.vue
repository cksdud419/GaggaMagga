<template>
    <MainHeader />
    <main class="flex flex-col justify-center items-center w-full">
        <section class="p-4 m-5 border-light rounded-xl bg-gray-50 text-gray-800 w-full max-w-[800px]">
            <input type="text" v-model="notice.title" placeholder="제목을 입력하세요" class="p-2 mb-4 text-xl border-b-1 w-full bg-gray-200 rounded-xl shadow-sm" />
            <div class="mb-2">
                <span>게시자: </span><strong>{{ notice.authorId }}</strong>
                <br>
                <span>본문</span>
            </div>
            <textarea v-model="notice.content" class="whitespace-pre bg-gray-200 p-3 rounded-xl mb-3 w-full h-auto">
            </textarea>

        </section>
        <section class="flex flex-column w-full justify-center gap-1 max-w-[800px]">
            <button @click="insertNotice"
                class="shadow-sm cursor-pointer border border-gray-300 rounded-xl w-full bg-gray-100 hover:bg-gray-500 hover:text-gray-50">
                입력</button>
            <button @click="router.push({ name: 'noticeList' })"
                class="shadow-sm cursor-pointer border border-gray-300 rounded-xl w-full bg-gray-950 text-gray-50 hover:bg-gray-500 hover:text-gray-50">
                취소</button>
        </section>
    </main>
</template>

<script setup>
import MainHeader from '@/components/main/MainHeader.vue';
import { useLoginUser } from '@/stores/loginUser';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const loginUser = useLoginUser();

const notice = ref({
    authorId: loginUser.loginUser.id,
    title: '',
    content: ''
});

const insertNotice = async () => {
    if(!notice.value.title){
        alert("제목을 입력하세요");
        return;
    }else if(!notice.value.content) {
        alert('내용을 입력하세요')
        return;
    }
    const userAi = loginUser.userAi;
    try {
        await userAi.post('/api/notice', {
            ...notice.value
        });
        router.push({name:'noticeList'})
    } catch (e) {
        console.error('수정 실패!', e);
    }
}
</script>
