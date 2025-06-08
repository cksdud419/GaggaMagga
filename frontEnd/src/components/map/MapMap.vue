<template>
  <div id="map" style="width: 100%; height: 100%"></div>

  <button
    class="fixed bottom-7 left-1/2 transform -translate-x-1/2 w-45 h-13 z-999 rounded-full border-none bg-red-400 hover:bg-red-500 text-white font-semibold shadow-2xl flex items-center justify-center tooltip tooltip-top"
    data-tip="모든 인포윈도우 닫기" @click='closeAllOverlay'>
    <svg xmlns="http://www.w3.org/2000/svg" width="45" height="45" viewBox="0 0 24 24" fill="none" stroke="currentColor"
      stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
      class="icon icon-tabler icons-tabler-outline icon-tabler-browser-x">
      <path stroke="none" d="M0 0h24v24H0z" fill="none" />
      <path d="M4 4m0 1a1 1 0 0 1 1 -1h14a1 1 0 0 1 1 1v14a1 1 0 0 1 -1 1h-14a1 1 0 0 1 -1 -1z" />
      <path d="M4 8h16" />
      <path d="M8 4v4" />
      <path d="M10 16l4 -4" />
      <path d="M14 16l-4 -4" />
    </svg>
  </button>

  <button
    class="fixed bottom-7 right-8 w-20 h-20 z-999 rounded-full border-none bg-blue-400 hover:bg-blue-500 text-white font-semibold shadow-2xl flex items-center justify-center tooltip tooltip-left"
    data-tip="AI 챗봇과 대화하기" @click="onShowBot">
    <svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" viewBox="0 0 24 24" fill="none" stroke="currentColor"
      stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
      class="icon icon-tabler icons-tabler-outline icon-tabler-robot">
      <path stroke="none" d="M0 0h24v24H0z" fill="none" />
      <path d="M6 4m0 2a2 2 0 0 1 2 -2h8a2 2 0 0 1 2 2v4a2 2 0 0 1 -2 2h-8a2 2 0 0 1 -2 -2z" />
      <path d="M12 2v2" />
      <path d="M9 12v9" />
      <path d="M15 12v9" />
      <path d="M5 16l4 -2" />
      <path d="M15 14l4 2" />
      <path d="M9 18h6" />
      <path d="M10 8v.01" />
      <path d="M14 8v.01" />
    </svg>
  </button>
  <AIChat :open="showAi" @close="onShowBot" />

  <!-- 공통 모달 -->
  <MapDetailModal ref="modalRef" :attraction="selectedAttraction" />
</template>

<script setup>
import { ref, reactive, createApp, onMounted, watch, nextTick } from 'vue';
import { useMapStore } from '@/stores/useMapStore';
import MapCustomOverlayInfo from '@/components/map/MapCustomOverlayInfo.vue';
import MapDetailModal from '@/components/map/MapDetailModal.vue';
import { useCourseStore } from '@/stores/courseStore';

// 마커 이미지
import markerAttraction from '@/assets/markers/attraction.png';
import markerCourse from '@/assets/markers/course.png'
import markerCulture from '@/assets/markers/culture.png'
import markerFestival from '@/assets/markers/festival.png'
import markerHotel from '@/assets/markers/hotel.png'
import markerRestaurant from '@/assets/markers/restaurant.png'
import markerShopping from '@/assets/markers/shopping.png'
import markerSports from '@/assets/markers/sports.png'
import { useRoute } from 'vue-router';
import AIChat from './AI/AIChat.vue';

const showAi = ref(false);

const map = ref();
const markers = ref([]);
const customOverlays = ref([]);
const boundsList = ref([]);
const route = useRoute();

const bounds = reactive({
  minX: Infinity, minY: Infinity, maxX: -Infinity, maxY: -Infinity
});

// 스토어
const mapStore = useMapStore();
const courseStore = useCourseStore();

const onShowBot = () => {
  showAi.value = !showAi.value;
}
// 공통 모달 관련 상태
const selectedAttraction = ref(null);
const modalRef = ref(null);
const openModal = (attraction) => {
  selectedAttraction.value = attraction;
  nextTick(() => modalRef.value?.open());
};

const loadKakaoMap = () => {
  return new Promise((resolve, reject) => {
    if (window.kakao && window.kakao.maps) return resolve(window.kakao);
    const script = document.createElement('script');
    script.src = `https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${import.meta.env.VITE_KAKAO_JS_KEY}&libraries=services`;
    script.async = true;
    script.onload = () => window.kakao.maps.load(() => resolve(window.kakao));
    script.onerror = () => reject(new Error('카카오 맵 로드 실패'));
    document.head.appendChild(script);
  });
};

const updateMap = (attractions) => {
  if (!map.value) return;
  const kakaoMap = map.value;

  // course가 선택되어 있으면 course_details를 합쳐줌
  if (courseStore.selectedCourse?.course_details?.length) {
    attractions = [...attractions, ...courseStore.selectedCourse.course_details];
  }

  markers.value.forEach(m => m.setMap(null));
  customOverlays.value.forEach(o => o.setMap(null));
  markers.value = [];
  customOverlays.value = [];
  boundsList.value = [];

  for (let i = 0; i < attractions.length; i++) {
    const attraction = attractions[i];
    const position = new window.kakao.maps.LatLng(attraction.latitude, attraction.longitude);

    // 마커 이미지
    let imageSrc;
    switch (attraction.content_type_id) {
      case 12:  // 관광지
        imageSrc = markerAttraction
        break;
      case 14:  // 문화시설
        imageSrc = markerCulture
        break;
      case 15:  // 축제공연행사
        imageSrc = markerFestival
        break;
      case 25:  // 여행코스
        imageSrc = markerCourse
        break;
      case 28:  // 레포츠
        imageSrc = markerSports
        break;
      case 32:  // 숙박
        imageSrc = markerHotel
        break;
      case 38:  // 쇼핑
        imageSrc = markerShopping
        break;
      case 39:  // 음식점
        imageSrc = markerRestaurant
        break;
    }

    // 마커 이미지 사이즈
    const imageSize = new window.kakao.maps.Size(58, 56)

    // 마커 옵션, 마커 좌표와 이미지 좌표 일치 설정
    const imageOption = { offset: new window.kakao.maps.Point(27, 58) };

    // 마커의 이미지 정보를 가지고 있는 마커 이미지 생성
    const markerImage = new window.kakao.maps.MarkerImage(imageSrc, imageSize, imageOption)

    const marker = new window.kakao.maps.Marker({ map: kakaoMap, position, title: attraction.title, image: markerImage });
    markers.value[i] = marker;
    boundsList.value[i] = [attraction.latitude, attraction.longitude];

    const id = attraction.attractions_id || attraction.no; // 고유 ID 확보

    const overlayContents = document.createElement('div');
    const app = createApp(MapCustomOverlayInfo, { attraction });
    const vm = app.mount(overlayContents);

    vm.$.emit = (event, payload) => {
      if (event === 'openDetail') openModal(payload);
      if (event === 'close') closeOverlay(i);
      if (event === 'addCourse') handleAddCourse(payload);
    };

    const overlay = new window.kakao.maps.CustomOverlay({
      content: overlayContents,
      position, xAnchor: 0.5, yAnchor: 1.2, clickable: true
    });

    customOverlays.value[i] = overlay;

    mapStore.registerMarker(id, marker, overlay); // 마커 & 오버레이 등록

    window.kakao.maps.event.addListener(marker, 'click', () => {
      overlay.setMap(kakaoMap);
      kakaoMap.panTo(position);
    });
  }

  if (attractions.length > 0) {
    getXY(boundsList.value);
    const sw = new window.kakao.maps.LatLng(bounds.minY, bounds.minX);
    const ne = new window.kakao.maps.LatLng(bounds.maxY, bounds.maxX);
    map.value.setBounds(new window.kakao.maps.LatLngBounds(sw, ne));
  }
};


const getXY = (list) => {
  bounds.minX = bounds.minY = Infinity;
  bounds.maxX = bounds.maxY = -Infinity;
  list.forEach(([lat, lng]) => {
    if (lat < 33 || lat > 38.5 || lng < 124.5 || lng > 131.0) return;
    if (lat < bounds.minY) bounds.minY = lat;
    if (lat > bounds.maxY) bounds.maxY = lat;
    if (lng < bounds.minX) bounds.minX = lng;
    if (lng > bounds.maxX) bounds.maxX = lng;
  });
};

const closeOverlay = (index) => {
  if (customOverlays.value[index]) {
    customOverlays.value[index].setMap(null);
  }
};

const closeAllOverlay = () => {
  customOverlays.value.forEach((overlay) => {
    overlay.setMap(null);
  });
}

onMounted(async () => {
  const kakao = await loadKakaoMap();
  map.value = new kakao.maps.Map(document.getElementById('map'), {
    center: new kakao.maps.LatLng(37.5665, 126.9780),
    level: 7
  });

  mapStore.setMapInstance(map.value); // 스토어에 지도 객체 등록

  if(route.query.courseId){
    courseStore.selectedCourse = route.query.courseId;
    await courseStore.fetchCourseDetail(courseStore.selectedCourse);
  }

  // if (mapStore.attractions.length > 0) {
  updateMap(mapStore.attractions);
  // }
});

watch(() => mapStore.attractions, (val) => {
  updateMap(val);
});

// 코스 관련
const handleAddCourse = async (attraction) => {
  const course = courseStore.selectedCourse;
  if (!course?.id) {
    alert('선택된 여행 계획이 없습니다.');
    return;
  }

  const payload = {
    course_id: course.id,
    attractions_id: attraction.attractions_id ?? attraction.no
  };

  try {
    await courseStore.addCourseDetail(payload);
    await courseStore.fetchCourseDetail(course.id);
  } catch (e) {
    console.error('코스에 경유지 추가 실패:', e);
    alert('코스 추가에 실패했습니다.');
  }
};

// 카카오 모빌리티 이용
// 지도 상태 상단에 추가
const polyline = ref(null);

// 라인 그리기
const drawLine = (vertexes) => {
  if (!map.value || !vertexes?.length) return;

  // 이전 라인 제거
  if (polyline.value) {
    polyline.value.setMap(null);
  }

  const path = [];
  for (let i = 0; i < vertexes.length; i += 2) {
    const lng = vertexes[i];
    const lat = vertexes[i + 1];
    path.push(new window.kakao.maps.LatLng(lat, lng));
  }

  polyline.value = new window.kakao.maps.Polyline({
    map: map.value,
    path: path,
    strokeWeight: 5,
    strokeColor: '#0028DB',
    strokeOpacity: 0.8,
    strokeStyle: 'solid',
    endArrow: true,
  });

  polyline.value.setMap(map.value);
};

// 라인 제거
const clearLine = () => {
  if (polyline.value) {
    polyline.value.setMap(null);
    polyline.value = null;
  }
};

// 코스가 선택되고 변동이 있을 시 계속해서 라인을 업데이트 함
watch(() => courseStore.selectedCourse, (course) => {
  if (!course || !course.vertexes?.length) {
    clearLine();
  } else {
    drawLine(course.vertexes);
  }
});

</script>
