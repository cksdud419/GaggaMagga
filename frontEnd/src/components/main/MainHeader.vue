<template>
    <header class="flex items-center justify-between px-8 py-4 shadow-md bg-white w-screen">
        <!-- 로고 & 네비 -->
        <div class="flex items-center gap-6">
            <router-link to="/">
                <img src="@/assets/logo.svg" alt="로고" class="w-20 h-20" />
            </router-link>
            <router-link to="/map">
                <button class="px-4 py-2 rounded-md text-base font-semibold bg-blue-100 hover:bg-blue-200 transition">
                    지도 보기
                </button>
            </router-link>
            <router-link to="/notice-list?page=1">
                <button class="px-4 py-2 rounded-md text-base font-semibold bg-blue-100 hover:bg-blue-200 transition">
                    게시판
                </button>
            </router-link>
        </div>

        <!-- 로그인/로그아웃 영역 -->
        <div class="flex items-center gap-4">
            <template v-if="loginUser.isLoggedIn">
                안녕하세요, {{ loginUser.loginUser.nickName }}!
                <router-link :to="{ name: 'userInfo', params: { id: loginUser.loginUser.id } }">
                    <div class="text-sm font-medium text-gray-700 border px-4 py-2 rounded-md shadow-sm">
                        사용자 정보 확인하기
                    </div>
                </router-link>
                <button class="px-4 py-2 text-sm font-semibold bg-red-500 text-white rounded hover:bg-red-600"
                    @click="loginUser.logout">
                    로그아웃
                </button>
            </template>

            <template v-else>
                <router-link to="/login">
                    <button class="px-4 py-2 text-sm font-semibold border rounded hover:bg-gray-100">
                        로그인
                    </button>
                </router-link>
                <router-link to="/regist">
                    <button class="px-4 py-2 text-sm font-semibold bg-black text-white rounded hover:bg-gray-800">
                        회원가입
                    </button>
                </router-link>
            </template>
        </div>
    </header>
</template>

<script setup>
import { useLoginUser } from '@/stores/loginUser'
const loginUser = useLoginUser()
</script>
