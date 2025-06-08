<template>
  <div class="relative min-h-screen overflow-hidden font-sans">

    <!-- 배경 이미지 A -->
    <div class="bg-image absolute inset-0 z-0 transition-opacity duration-2000 scale-animation"
      :style="{ backgroundImage: `url(${imageA})`, opacity: showA ? 1 : 0 }"></div>

    <!-- 배경 이미지 B -->
    <div class="bg-image absolute inset-0 z-0 transition-opacity duration-2000 scale-animation"
      :style="{ backgroundImage: `url(${imageB})`, opacity: showA ? 0 : 1 }"></div>

    <!-- 로그인 UI -->
    <div class="flex items-center justify-center min-h-screen relative z-10 px-4">
      <div class="card w-full max-w-2xl bg-white shadow-md rounded-xl z-10">

        <div class="flex flex-row items-center p-8 gap-5">

          <!-- 왼쪽 로고 -->
          <div class="w-60 h-60 flex items-center justify-center">
            <img src="@/assets/logo.png" alt="로고" class="w-full h-full object-contain" />
          </div>

          <!-- 오른쪽 로그인 폼 -->
          <div class="flex-1 space-y-4">
            <h2 class="text-2xl font-bold text-blue-500 text-center">로그인</h2>

            <div>
              <label class="label font-semibold">아이디</label>
              <input v-model="userId" type="text" class="input input-bordered w-full" placeholder="아이디" />
            </div>

            <div>
              <label class="label font-semibold">비밀번호</label>
              <input v-model="password" type="password" class="input input-bordered w-full" placeholder="비밀번호" />
            </div>

            <!-- 회원가입 + 비밀번호 찾기 -->
            <div class="text-sm text-blue-500 text-right space-x-2">
              <RouterLink to="/regist" class="hover:underline">회원 가입</RouterLink>
              <span>|</span>
              <RouterLink to="/find-password" class="hover:underline">비밀번호 찾기</RouterLink>
            </div>


            <button @click="onLogin" class="btn btn-primary w-full">로그인</button>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useLoginUser } from '@/stores/loginUser'
import { RouterLink } from 'vue-router'

const loginUser = useLoginUser()
const userId = ref('')
const password = ref('')

const onLogin = async () => {
  await loginUser.login(userId.value, password.value)
}

// ✅ 이미지 자동 로딩
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
