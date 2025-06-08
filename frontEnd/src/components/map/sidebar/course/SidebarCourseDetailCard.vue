<template>
    <div class="collapse collapse-arrow bg-base-100 w-78 shadow">
        <input type="checkbox" />
        <div class="collapse-title font-semibold ellipsis-text">
            {{ detail.detail_order }}. {{ detail.title }}
        </div>

        <div class="collapse-content text-sm w-78">
            <!-- 이미지 -->
            <figure class="px-7 pt-7" v-if="detail.first_image1">
                <img :src="detail.first_image1" alt="img" class="w-full h-50 object-cover rounded-xl" />
            </figure>

            <div class="card-body items-center text-center">
                <h2 class="card-title ellipsis-text">{{ detail.title }}</h2>
                <p class="ellipsis-text">{{ detail.addr1 }}</p>
                <p class="ellipsis-text">{{ detail.tel }}</p>

                <!-- 버튼들 -->
                <div class="flex w-full justify-between gap-2">
                    <button class="btn px-4 bg-white border-gray-300 hover:border-gray-400 hover:bg-gray-100"
                        @click="focusOnMap">
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
                        @click="emit('open-modal', detail)">
                        상세 정보
                    </button>
                </div>

                <div class="w-full" v-if="loginUser.loginUser.id == courseStore.selectedCourse.author_id">
                    <button class="btn w-full bg-red-400 text-white border-none hover:bg-red-500"
                        @click="deleteCourseDetail">
                        경유지 제거
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useMapStore } from '@/stores/useMapStore';
import { useCourseStore } from '@/stores/courseStore';
import { useLoginUser } from '@/stores/loginUser';

const mapStore = useMapStore();
const courseStore = useCourseStore();
const loginUser = useLoginUser();

const props = defineProps({
    detail: Object,
});
const emit = defineEmits(['open-detail']);

const deleteCourseDetail = async () => {
  const courseId = props.detail.course_id;
  const detailOrder = props.detail.detail_order;

  try {
    const success = await courseStore.deleteCourseDetail(courseId, detailOrder);
    if (success) {
      // 삭제 성공 시 다시 course detail 목록 갱신
      await courseStore.fetchCourseDetail(courseId);
    }
  } catch (e) {
    console.error('경유지 제거 실패:', e);
    alert('경유지 제거에 실패했습니다.');
  }
}

const focusOnMap = () => {
    mapStore.focusMarker(props.detail.attractions_id);
};
</script>

<style scoped>
.ellipsis-text {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 100%;
    display: block;
}
</style>
