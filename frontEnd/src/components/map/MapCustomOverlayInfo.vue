<!-- 커스텀 오버레이에 표시할 정보를 저장 -->
<template>
  <div class="wrap relative">
    <div class="info">

      <div class="title bg-blue-400 text-white font-bold flex items-center gap-2">

        <!-- 제목 -->
        <span class="text-base ellipsis-text">{{ attraction.title }}</span>
      </div>


      <div class="image-center">
        <img :src="imageSrc" alt="이미지" class="info-image" />
      </div>

      <div class="desc-section">
        <div class="desc-text ellipsis-text" v-if="attraction.addr1">{{ attraction.addr1 }}</div>
        <div class="desc-text ellipsis-text" v-if="attraction.tel">{{ attraction.tel }}</div>
      </div>

      <div class="btnList flex flex-col gap-2 px-4 pb-4">
        <!-- 위쪽: 북마크 + 상세정보 -->
        <div class="flex items-center gap-2">
          <!-- 북마크 버튼 -->
          <button
            class="w-12 h-9 flex justify-center items-center rounded border border-gray-300 hover:border-gray-400 hover:bg-gray-100 shadow-sm"
            @click="toggleBookmark">
            <svg v-if="isBookmarked" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
              fill="red">
              <path
                d="M6.979 3.074a6 6 0 014.988 1.425l.037.033.034-.03a6 6 0 014.733-1.44 6 6 0 013.364 10.008l-.18.185-.048.041-7.45 7.379a1 1 0 01-1.407 0l-7.493-7.422a6 6 0 013.176-10.215z" />
            </svg>
            <svg v-else xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
              stroke="gray" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M19.5 12.572l-7.5 7.428-7.5-7.428a5 5 0 117.5-6.566 5 5 0 017.5 6.572" />
            </svg>
          </button>

          <!-- 상세 정보 버튼 -->
          <button class="flex-1 h-9 rounded bg-blue-400 hover:bg-blue-500 text-white font-bold shadow-sm"
            @click="$emit('openDetail', attraction)">
            상세 정보
          </button>
        </div>

        <!-- 아래쪽: '코스에 추가' 버튼 (선택된 코스가 있을 때만 표시) -->
        <button v-if="hasSelectedCourse && loginUser.loginUser.id == courseStore.selectedCourse.author_id"
          class="h-9 rounded bg-green-400 hover:bg-green-500 text-white font-bold w-full shadow-sm"
          @click="$emit('addCourse', attraction)">
          코스에 추가
        </button>

        <!-- 아래쪽: 닫기 버튼 -->
        <button class="h-9 rounded bg-red-400 hover:bg-red-500 text-white font-bold w-full shadow-sm"
          @click="$emit('close')">
          닫기
        </button>
      </div>

    </div>
  </div>
</template>

<script setup>
import noImage from '@/assets/noImage.jpg'
import { computed, } from 'vue';
import { bookmarkStore } from '@/stores/bookmarkStore';
import { useCourseStore } from '@/stores/courseStore';
import { useLoginUser } from '@/stores/loginUser';

const props = defineProps({
  attraction: Object
})
defineEmits(['close'])
const bmStore = bookmarkStore();
const loginUser = useLoginUser();

// 북마크 여부 판단 (관광지 ID 기준)
const isBookmarked = computed(() => {
  const id = props.attraction?.no ?? props.attraction?.attractions_id;
  return id && bookmarkStore().bookmarks.some(b => b.attractions_id === id);
});

// 북마크 토글 함수
const toggleBookmark = () => {
  const id = props.attraction?.no ?? props.attraction?.attractions_id;
  if (!id) {
    console.warn('유효하지 않은 관광지 ID입니다:', props.attraction);
    return;
  }
  bmStore.toggleBookmark(id);
};

const imageSrc = props.attraction.first_image1 || noImage

// 코스
const courseStore = useCourseStore();
const hasSelectedCourse = computed(() => !!courseStore.selectedCourse?.id);
console.log(courseStore.selectedCourse);

</script>

<style>
@charset "UTF-8";

.wrap {
  background: #fff;
  border: none;
  border-radius: 10px;
  width: 280px;
  max-width: 280px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
}

.info .title {
  padding: 8px 30px 8px 14px;
  font-weight: 1000;
  position: relative;
  border-bottom: 1px solid #ddd;
}

.image-center {
  display: flex;
  justify-content: center;
  padding: 10px 0;
}

.info-image {
  width: 100%;
  max-width: 200px;
  height: 140px;
  object-fit: cover;
  border-radius: 8px;
}


.desc-section {
  padding: 0 14px 14px 14px;
  font-size: 13px;
  color: #333;
}

.desc-text {
  margin-top: 6px;
  word-break: break-word;
}

.ellipsis-text {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  display: block;
  max-width: 100%;
  /* 또는 고정 px */
}
</style>
