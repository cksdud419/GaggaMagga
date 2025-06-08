<template>
  <div class="card bg-base-100 w-78 shadow">
    <!-- 관광지 이미지 -->
    <figure class="px-7 pt-7" v-if="bookmark.first_image1">
      <img :src="bookmark.first_image1" alt="img" class="w-full h-50 object-cover rounded-xl" />
    </figure>

    <div class="card-body items-center text-center">
      <!-- 관광지 제목 -->
      <h2 class="card-title ellipsis-text">{{ bookmark.title }}</h2>
      <p class="ellipsis-text">{{ bookmark.addr1 }}</p>
      <p class="ellipsis-text">{{ bookmark.tel }}</p>

      <!-- 첫 번째 줄: 지도 + 상세 정보 -->
      <div class="flex w-full justify-between gap-2">
        <button class="btn px-4 bg-white border-gray-300 hover:border-gray-400 hover:bg-gray-100" @click="focusOnMap">
          <svg xmlns="http://www.w3.org/2000/svg" width="23" height="23" viewBox="0 0 24 24" fill="none"
            stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path stroke="none" d="M0 0h24v24H0z" fill="none" />
            <path d="M12 18.5l-3 -1.5l-6 3v-13l6 -3l6 3l6 -3v7.5" />
            <path d="M9 4v13" />
            <path d="M15 7v5.5" />
            <path
              d="M21.121 20.121a3 3 0 1 0 -4.242 0c.418 .419 1.125 1.045 2.121 1.879c1.051 -.89 1.759 -1.516 2.121 -1.879z" />
            <path d="M19 18v.01" />
          </svg>
        </button>

        <button class="btn flex-1 bg-blue-400 text-white border-none hover:bg-blue-500"
          @click="emit('open-detail', bookmark)">
          상세 정보
        </button>
      </div>

      <!-- 두 번째 줄: 북마크 제거 -->
      <div class="w-full ">
        <button class="btn w-full bg-red-400 text-white border-none hover:bg-red-500" @click="deleteBookmark">
          북마크 제거
        </button>
      </div>
    </div>


  </div>
</template>

<script setup>
import { bookmarkStore } from '@/stores/bookmarkStore';
import { useMapStore } from '@/stores/useMapStore';


// 북마크 스토어
const store = bookmarkStore();
// 지도 스토어
const mapStore = useMapStore();

const props = defineProps({
  bookmark: Object
});

// 모달은 상위에서 염
const emit = defineEmits(['open-detail']);

// 북마크 삭제
const deleteBookmark = () => {
  store.toggleBookmark(props.bookmark.attractions_id);
};

// 지도 마커 위치 포커스
const focusOnMap = () => {
  mapStore.focusMarker(props.bookmark.attractions_id);
};
</script>

<style scoped>
.ellipsis-text {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 100%;
  /* 필수: 너비가 지정돼야 ellipsis가 작동함 */
  display: block;
}
</style>
