<template>
    <article class="flex flex-col border-b-gray-300 border-b-1 py-2">
        <div>
            <RouterLink :to="{name:'userInfo', params:{'id':author_id}}">
            <small class="bg-amber-50 hover:bg-amber-400 p-1 rounded-xl text-amber-900 cursor-pointer hover:shadow-xl">{{ author_id }}</small>
            </RouterLink>
            <div>{{ content }}</div>
        </div>
        <div class="w-full flex justify-end">
            <DatePrint :date="create_at" />
            <button @click="deleteComment" v-if="isMe" class="p-1 rounded-sm bg-red-200 text-gray-800 ml-2 cursor-pointer">삭제</button>
        </div>
    </article>
</template>

<script setup>
import { useLoginUser } from '@/stores/loginUser';
import { computed } from 'vue';
import DatePrint from '../DatePrint.vue';

const props = defineProps({
    id: Number,
    notice_id: Number,
    author_id: String,
    content: String,
    create_at: String
});

const emits = defineEmits(['update']);

const loginUser = useLoginUser();
const isMe = computed(() => {
    return loginUser.loginUser.id === props.author_id;
});

const deleteComment = async () => {
    const userAi = loginUser.userAi;
    try{
        await userAi.post("/api/notice/comment/delete/"+props.id);
        emits('update', props.id);
    } catch(e){
        console.error("댓글 삭제 실패!",e);
    }
}

</script>