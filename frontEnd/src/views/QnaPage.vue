<template>
  <MainHeader />
  <main class="flex flex-col justify-center items-center w-full">
    <!-- 질문 섹션 -->
    <section class="p-4 m-5 border-light rounded-xl bg-gray-50 text-gray-800 w-full max-w-[800px]">
      <h3 class="mb-4 text-xl"><b>질문</b> {{ qna.title }}</h3>
      <div class="mb-2">
        <span>질문자: </span><strong>{{ qna.author }}</strong>
      </div>
      <div class="whitespace-pre bg-gray-200 p-3 rounded-xl mb-3">{{ qna.content }}</div>
      <article class="flex justify-end w-full">
        <span class="rounded-xl bg-blue-100 p-1 px-2 mr-1 text-blue-700">{{ formattedDate }}</span>
        <span class="rounded-xl bg-blue-100 p-1 px-2 text-blue-700">{{ formattedTime }}</span>
      </article>
    </section>

    <!-- 질문 답변 구분선 -->
    <div class="flex w-full flex-col">
      <div class="divider">답변</div>
    </div>
    <!-- 답변 섹션 -->
    <section class="p-4 m-5 border-light rounded-xl bg-gray-50 text-gray-800 w-full max-w-[800px]">
      <div class="mb-2">
        <span>답변자: </span><strong>지식인마스터</strong>
      </div>
      <div class="whitespace-pre bg-gray-200 p-3 rounded-xl mb-3">잠이나 자라</div>
      <article class="flex justify-end w-full">
        <span class="rounded-xl bg-blue-100 p-1 px-2 mr-1 text-blue-700">2025년 5월 13일</span>
        <span class="rounded-xl bg-blue-100 p-1 px-2 text-blue-700">오전 7:12</span>
      </article>
    </section>


    <!-- 버튼 섹션 -->
    <section class="max-w-[800px] w-full mb-2">
      <button class="border border-gray-300 rounded-xl w-full bg-gray-100 hover:bg-gray-500 hover:text-gray-50">게시글
        목록으로</button>
    </section>
    <section class="flex flex-column w-full justify-center gap-1 max-w-[800px]">
      <button
        class="border border-gray-300 rounded-xl w-full bg-gray-100 hover:bg-gray-500 hover:text-gray-50">수정</button>
      <button
        class="border border-gray-300 rounded-xl w-full bg-gray-950 text-gray-50 hover:bg-gray-500 hover:text-gray-50">삭제</button>
    </section>
  </main>

</template>

<script setup>
import MainHeader from '@/components/main/MainHeader.vue';
import { computed, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
// 임시 데이터 가져오기
const getQna = (id) => {
  const data = [
    {
      title: "질문이 있어요.", qnaId: 0, dateTime: "2025-05-12T09:41:00", author: 'admin',
      content: '강호동이랑 최홍만이랑 싸우면 누가 이기나요?'
    },
  ]
  const qna = data.find((i) => i.qnaId == id);
  return qna;
}
const qna = ref(getQna(route.query.qnaId));

console.log(qna);
const formattedDate = computed(() => {
  const date = new Date(qna.value.dateTime)
  return date.toLocaleDateString('ko-KR', {
    year: 'numeric',
    month: 'short',
    day: 'numeric',
  })
})

const formattedTime = computed(() => {
  const date = new Date(qna.value.dateTime)
  return date.toLocaleTimeString('ko-KR', {
    hour: 'numeric',
    minute: '2-digit',
  })
})

</script>
