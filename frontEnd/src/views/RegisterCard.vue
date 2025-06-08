<template>
  <div class="flex items-center justify-center min-h-screen px-4">
    <div class="card w-full max-w-2xl bg-white shadow-md rounded-xl z-10">
      <div class="flex flex-row items-center p-8 gap-5">

        <!-- 왼쪽 로고 -->
        <div class="w-60 h-60 flex items-center justify-center">
          <img src="@/assets/logo.png" alt="로고" class="w-full h-full object-contain" />
        </div>

        <!-- 회원가입 폼 -->
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

          <div class="text-sm text-blue-500 text-center">
            <button @click="$emit('switch')" class="hover:underline">이미 계정이 있으신가요? 로그인</button>
          </div>

          <button @click="onRegist" class="btn btn-primary w-full">회원가입</button>
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
const userEmail = ref('')
const userNickName = ref('')
const password = ref('')
const password2 = ref('')
const worning = ref('')

const emit = defineEmits(['switch'])

const onRegist = async () => {
  let valid = true
  worning.value = ''

  if (!userId.value) worning.value += "<p>아이디를 입력해주세요</p>", valid = false
  if (!userEmail.value) worning.value += "<p>이메일을 입력해주세요</p>", valid = false
  else if (!userEmail.value.includes('@')) worning.value += "<p>이메일 형식이 아닙니다</p>", valid = false
  if (!userNickName.value) worning.value += "<p>닉네임을 입력해주세요</p>", valid = false
  if (!password.value) worning.value += "<p>비밀번호를 입력해주세요</p>", valid = false
  if (!password2.value) worning.value += "<p>비밀번호 확인을 입력해주세요</p>", valid = false
  if (password.value !== password2.value) worning.value += "<p>비밀번호가 일치하지 않습니다</p>", valid = false

  if (valid) {
    try {
      await loginUser.userAi.post('/api/user', {
        id: userId.value,
        nickName: userNickName.value,
        email: userEmail.value,
        password: password.value,
        refresh: null
      })
      alert('회원가입이 완료되었습니다! 로그인 해주세요.')
      emit('switch')
    } catch (e) {
      console.error('회원가입 실패!', e)
    }
  }
}
</script>
