<template>
    <MainHeader />
    <main class="mt-2 flex justify-center">
      <div class='max-w-[800px] w-full mx-auto'>
        <SearchBar @search="searchHandler" />
        <QnaList :list="curruntQnaData"/>
      </div>
    </main>
</template>

<script setup>
import MainHeader from '@/components/main/MainHeader.vue';
import QnaList from '@/components/QnA/QnaList.vue';
import SearchBar from '@/components/SearchBar.vue';

// 임시 게시글 목록
const curruntQnaData = [
    {title: "질문이 있어요.", qnaId: 0, dateTime:"2025-05-12T09:41:00", author: 'admin'},
]
const searchHandler = () => {

}
</script>
