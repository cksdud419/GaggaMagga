<template>
  <MainHeader />
  <main class="mt-2 flex justify-center flex-col">
    <div class='max-w-[800px] w-full mx-auto'>
      <SearchBar @search="searchHandler" />
      <RouterLink :to="{ name: 'noticeInsert' }"
        class="flex w-full h-5.5 justify-center items-center border border-gray-200 shadow-md rounded-xl bg-purple-50 hover:h-10 duration-300 text-gray-800">
        글쓰기</RouterLink>
      <NoticeList :list="curruntNoticeData" />
    </div>
    <div class="mt-4 flex justify-center">
      <div class="join">
        <button class="join-item btn" :disabled="page <= 1" @click="changePage(page - 1)">«</button>
        <button v-for="p in totalPages" :key="p" @click="changePage(p)" class="join-item btn"
          :class="{ 'btn-active': page === p }">
          {{ p }}
        </button>
        <button class="join-item btn" :disabled="page >= totalPages" @click="changePage(page + 1)">»</button>
      </div>
    </div>
  </main>
</template>

<script setup>
import MainHeader from '@/components/main/MainHeader.vue';
import NoticeList from '@/components/NoticePage/NoticeList.vue';
import SearchBar from '@/components/SearchBar.vue';
import { useLoginUser } from '@/stores/loginUser';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();
const page = ref(1);
const totalPages = ref(10) // 서버에서 받아오거나 하드코딩 가능

const changePage = async (newPage) => {
  if (newPage < 1 || newPage > totalPages.value) return
  page.value = newPage
  curruntNoticeData.value = await getList() // 검색 파라미터가 있으면 넘겨줘야 함
}
const getList = async (search) => {
  const userAi = loginUser.userAi;
  try {
    let data = null;
    if (search)
      data = await userAi.get(`/api/notice/list/${search}?page=${page.value - 1}`);
    else
      data = await userAi.get(`/api/notice/list?page=${page.value - 1}`);
    return data.data.data;
  } catch (e) {
    console.error("검색 실패", e);
  }
}
const loginUser = useLoginUser();
const curruntNoticeData = ref([]);

const searchHandler = async (search) => {
  console.log(page);
  curruntNoticeData.value = await getList(search);
}

onMounted(async () => {
  if(route.query.page)
    page.value = route.query.page;
  console.log(page.value);
  curruntNoticeData.value = await getList();
});
</script>
