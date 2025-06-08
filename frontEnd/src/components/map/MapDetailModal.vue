<template>
  <div>
    <dialog ref="modalRef" class="modal fixed inset-0 z-999 m-0">
      <div v-if="attraction" class="modal-box w-130 max-w-5xl p-0 overflow-hidden">

        <!-- 제목, 상단 배경 파랑 -->
        <div class="bg-blue-400 px-6 py-4">
          <h3 class="text-xl font-bold text-white">{{ attraction.title }}</h3>
        </div>

        <div class="bg-white p-6">
          <!-- 이미지 -->
          <div class="flex justify-center mb-4">
            <template v-if="attraction.first_image1">
              <img v-if="!loadingWeather" :src="attraction.first_image1" alt="관광지 이미지"
                class="rounded-lg w-full max-w-md object-contain" />
              <div v-else class="w-full max-w-md h-48 bg-gray-200 flex items-center justify-center rounded-lg">
                <span class="loading loading-bars loading-md text-blue-500"></span>
              </div>
            </template>
          </div>

          <!-- 상세 정보 -->
          <div class="space-y-2 text-sm text-gray-700 break-words">
            <div v-if="attraction.addr1"><strong>주소:</strong> {{ attraction.addr1 }}</div>
            <div v-if="attraction.tel"><strong>전화:</strong> {{ attraction.tel }}</div>
          </div>


          <!-- 날씨 -->
          <div class="mt-4">
            <div class="font-bold mb-1">날씨</div>
            <div class="grid grid-cols-5 gap-2 text-xs text-center">
              <template v-if="!loadingWeather">
                <div v-for="(item, index) in allWeather" :key="item.dt"
                  class="bg-blue-50 p-2 rounded flex flex-col items-center text-xs text-center gap-2">

                  <div>
                    {{
                      index === 0
                        ? '현재 (' + new Date().toLocaleDateString('ko-KR', { weekday: 'short' }) + ')'
                        : new Date(item.dt * 1000).toLocaleDateString('ko-KR', {
                          month: '2-digit',
                          day: '2-digit',
                    weekday: 'short'
                    })
                    }}
                  </div>

                  <div
                    class="w-12 h-12 bg-blue-200 border border-none rounded-full shadow flex items-center justify-center my-1">
                    <img :src="`https://openweathermap.org/img/wn/${item.weather[0].icon}@2x.png`" class="w-10 h-10" />
                  </div>

                  <div>{{ item.main.temp }}°C</div>
                  <div>{{ item.weather[0].description }}</div>
                </div>

              </template>
              <template v-else>
                <div v-for="n in 5" :key="n"
                  class="bg-gray-200 p-2 rounded h-32 flex flex-col items-center justify-center gap-2">
                  <span class="loading loading-spinner text-blue-400"></span>
                </div>
              </template>
            </div>
          </div>

          <!-- 버튼 -->
          <div class="modal-action mt-6">
            <!-- 북마크 버튼 -->
            <button
              class="w-35 h-9 flex justify-center items-center gap-2 rounded border border-gray-300 hover:border-gray-400 hover:bg-gray-100 shadow-sm font-bold text-gray-800"
              @click="toggleBookmark">
              <!-- 아이콘 -->
              <svg v-if="isBookmarked" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"
                fill="red">
                <path
                  d="M6.979 3.074a6 6 0 014.988 1.425l.037.033.034-.03a6 6 0 014.733-1.44 6 6 0 013.364 10.008l-.18.185-.048.041-7.45 7.379a1 1 0 01-1.407 0l-7.493-7.422a6 6 0 013.176-10.215z" />
              </svg>
              <svg v-else xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                stroke="gray" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M19.5 12.572l-7.5 7.428-7.5-7.428a5 5 0 117.5-6.566 5 5 0 017.5 6.572" />
              </svg>

              <!-- 텍스트 -->
              북마크
            </button>
            <!-- 닫기 버튼 -->
            <form method="dialog" class="w-full text-right">
              <button class="h-9 rounded bg-red-400 hover:bg-red-500 text-white font-bold w-full shadow-sm">닫기
              </button>
            </form>
          </div>
        </div>
      </div>
    </dialog>
  </div>
</template>

<script setup>
import { ref, watchEffect, computed } from 'vue';
import { bookmarkStore } from '@/stores/bookmarkStore';

const props = defineProps({
  attraction: Object
});

const modalRef = ref();
const open = () => modalRef.value?.showModal();
defineExpose({ open });

// 날씨 설정
const currentWeather = ref(null);
const forecastList = ref([]);
const API_KEY = import.meta.env.VITE_OPENWEATHER_KEY;
const loadingWeather = ref(true);


watchEffect(() => {
  console.log("날씨 api 요청!")
  console.log(props.attraction)
  if (!props.attraction || !props.attraction.latitude || !props.attraction.longitude) return;
  const lat = props.attraction.latitude;
  const lon = props.attraction.longitude;
  if (!lat || !lon) return;

  loadingWeather.value = true;

  Promise.all([
    fetch(`https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric&lang=kr`).then(res => res.json()),
    fetch(`https://api.openweathermap.org/data/2.5/forecast?lat=${lat}&lon=${lon}&appid=${API_KEY}&units=metric&lang=kr`).then(res => res.json())
  ]).then(([current, forecast]) => {
    currentWeather.value = current;
    forecastList.value = forecast.list.filter(item => item.dt_txt.includes("03:00:00")).slice(0, 4);
  }).finally(() => {
    loadingWeather.value = false;
  });
});

const allWeather = computed(() => {
  return currentWeather.value ? [currentWeather.value, ...forecastList.value] : [];
});

const bmStore = bookmarkStore();

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
</script>

<style scoped></style>
