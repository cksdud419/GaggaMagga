<template>
  <div class="relative min-h-screen overflow-hidden font-sans">
    <!-- 배경 이미지 A -->
    <div
      class="bg-image absolute inset-0 z-0 transition-opacity duration-2000 scale-animation"
      :style="{ backgroundImage: `url(${imageA})`, opacity: showA ? 1 : 0 }"
    ></div>

    <!-- 배경 이미지 B -->
    <div
      class="bg-image absolute inset-0 z-0 transition-opacity duration-2000 scale-animation"
      :style="{ backgroundImage: `url(${imageB})`, opacity: showA ? 0 : 1 }"
    ></div>

    <!-- 내부 콘텐츠 -->
    <div class="relative z-10">
      <slot />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

// 이미지 자동 로드
const modules = import.meta.glob('/src/assets/loginImg/*.{jpg,png,webp}', { eager: true })
const imageList = Object.values(modules).map(m => m.default)

const imageA = ref('')
const imageB = ref('')
const showA = ref(true)

onMounted(() => {
  if (imageList.length >= 2) {
    imageA.value = imageList[Math.floor(Math.random() * imageList.length)]
    imageB.value = imageList[Math.floor(Math.random() * imageList.length)]

    setInterval(() => {
      const next = imageList[Math.floor(Math.random() * imageList.length)]
      if (showA.value) imageB.value = next
      else imageA.value = next
      showA.value = !showA.value
    }, 7000)
  }
})
</script>

<style scoped>
.bg-image {
  background-size: cover;
  background-position: center;
  width: 100vw;
  height: 100vh;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 0;
  transition: opacity 2s ease-in-out;
  pointer-events: none;
}

.scale-animation {
  animation: slideZoom 14s ease-in-out infinite;
  transform-origin: center;
}

@keyframes slideZoom {
  0% {
    transform: scale(1) translateX(0);
  }
  50% {
    transform: scale(1.06) translateX(-1.5vw);
  }
  100% {
    transform: scale(1) translateX(0);
  }
}
</style>
