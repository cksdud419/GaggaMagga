<template>
  <div class="flex items-center justify-center min-h-screen px-4">
    <div class="card w-full max-w-2xl bg-white shadow-md rounded-xl z-10">
      <div class="flex flex-row items-center p-8 gap-5">

        <!-- 왼쪽 로고 -->
        <div class="w-60 h-60 flex items-center justify-center">
          <img src="@/assets/logo.png" alt="로고" class="w-full h-full object-contain" />
        </div>

        <!-- 로그인 폼 -->
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

          <div class="text-sm text-blue-500 text-center space-x-2">
            <button @click="$emit('switch')" class="hover:underline">회원가입</button>
          </div>

          <button @click="onLogin" class="btn btn-primary w-full">로그인</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useLoginUser } from '@/stores/loginUser'

const loginUser = useLoginUser()
const userId = ref('')
const password = ref('')

const onLogin = async () => {
  await loginUser.login(userId.value, password.value)
}
</script>
