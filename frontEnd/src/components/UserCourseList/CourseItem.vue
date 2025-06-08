<template>
  <div class="card bg-base-100 w-78 shadow">
    <div class="card-body">
      <h2 class="card-title">{{ course.title }}</h2>
      <p>{{ course.des }}</p>

      <div class="card-actions justify-center gap-3">
        <div class="flex w-full justify-between gap-2">
          <button class="btn flex-1 bg-blue-400 text-white border-none hover:bg-blue-500"
            @click="onClick()"
          >보러가기</button>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const prop = defineProps({ course: Object });
const router = useRouter();
const onClick = () => {
  router.push({name:'map', query: {'courseId': prop.course.id}});
}
</script>
