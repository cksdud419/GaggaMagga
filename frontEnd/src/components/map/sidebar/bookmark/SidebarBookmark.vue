<template>
  <div class="flex flex-col items-center gap-4 p-4">
    <bookmarkCard v-for="bookmark in bmStore.bookmarks" :key="bookmark.attractions_id" :bookmark="bookmark"
      @open-detail="handleOpenDetail"
      class="shadow-md" />
  </div>

  <teleport to="body">
    <MapDetailModal ref="modalRef" :attraction="selectedAttraction" />
  </teleport>

</template>

<script setup>
import { ref, watch, onMounted } from 'vue';
import { bookmarkStore } from '@/stores/bookmarkStore';
import { useMapStore } from '@/stores/useMapStore';
import bookmarkCard from './SidebarBookmarkCard.vue';
import MapDetailModal from '@/components/map/MapDetailModal.vue';

const bmStore = bookmarkStore();
const mapStore = useMapStore();

const selectedAttraction = ref(null);
const modalRef = ref();

const handleOpenDetail = (bookmark) => {
  selectedAttraction.value = bookmark;
  modalRef.value?.open();
};

onMounted(() => {
  bmStore.fetchBookmarks();
});

// 북마크가 바뀔 때마다 지도 실시간 업데이트
watch(() => bmStore.bookmarks, (val) => {
  mapStore.updateMap(val);
}, { immediate: true });
</script>
