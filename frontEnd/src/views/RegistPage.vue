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

    <!-- 회원가입 카드 -->
    <div class="flex items-center justify-center min-h-screen relative z-10 px-4">
      <div class="card w-full max-w-2xl bg-white shadow-md rounded-xl z-10">
        <div class="flex flex-row items-center p-8 gap-5">

          <!-- 왼쪽 로고 -->
          <div class="w-60 h-60 flex items-center justify-center">
            <img src="@/assets/logo.png" alt="로고" class="w-full h-full object-contain" />
          </div>

          <!-- 오른쪽 회원가입 폼 -->
          <div class="flex-1 space-y-4">
            <h2 class="text-2xl font-bold text-blue-500 text-center">회원가입</h2>

            <div>
              <label class="label font-semibold">아이디</label>
              <input v-model="userId" type="text" class="input input-bordered w-full" placeholder="아이디" />
            </div>

            <div>
              <label class="label font-semibold">이메일</label>
              <input v-model="userEmail" type="email" class="input input-bordered w-full" placeholder="이메일" />
            </div>

            <div>
              <label class="label font-semibold">닉네임</label>
              <input v-model="userNickName" type="text" class="input input-bordered w-full" placeholder="닉네임" />
            </div>

            <div>
              <label class="label font-semibold">비밀번호</label>
              <input v-model="password" type="password" class="input input-bordered w-full" placeholder="비밀번호" />
            </div>

            <div>
              <label class="label font-semibold">비밀번호 확인</label>
              <input v-model="password2" type="password" class="input input-bordered w-full" placeholder="비밀번호 확인" />
            </div>

            <div v-html="worning" class="text-sm text-red-400"></div>

            <button @click="onRegist" class="btn btn-primary w-full">회원가입</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useLoginUser } from '@/stores/loginUser'
import { useRouter } from 'vue-router'

const loginUser = useLoginUser()
const router = useRouter()

const userId = ref('')
const userEmail = ref('')
const userNickName = ref('')
const password = ref('')
const password2 = ref('')
const worning = ref('')

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

const onRegist = async () => {
  let valid = true
  worning.value = ''
  if (!userId.value) {
    valid = false
    worning.value += "<p class='text-red-300'>아이디를 입력해주세요</p>"
  }
  if (!userEmail.value) {
    valid = false
    worning.value += "<p class='text-red-300'>이메일을 입력해주세요</p>"
  } else if (!userEmail.value.includes('@')) {
    valid = false
    worning.value += "<p class='text-red-300'>이메일형식이 아닙니다!</p>"
  }
  if (!userNickName.value) {
    valid = false
    worning.value += "<p class='text-red-300'>닉네임을 입력해주세요</p>"
  }
  if (!password.value) {
    valid = false
    worning.value += "<p class='text-red-300'>비밀번호를 입력해주세요</p>"
  }
  if (!password2.value) {
    valid = false
    worning.value += "<p class='text-red-300'>비밀번호 확인을 입력해주세요</p>"
  }
  if (password2.value !== password.value) {
    valid = false
    worning.value += "<p class='text-red-300'>비밀번호 확인이 일치하지 않습니다</p>"
  }

  if (valid) {
    try {
      await loginUser.userAi.post('/api/user', {
        id: userId.value,
        nickName: userNickName.value,
        email: userEmail.value,
        password: password.value,
        refresh: null
      })
      console.log("회원가입 성공!")
      router.push({ name: 'login' })
    } catch (e) {
      console.error("회원가입 실패!", e)
    }
  }
}
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
