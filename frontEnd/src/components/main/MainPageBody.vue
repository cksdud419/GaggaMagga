<template>
    <main
        class="flex-1 flex flex-col items-center justify-center text-center px-4 py-20 bg-gradient-to-b from-blue-50 to-white">
        <div class="grid grid-cols-1 lg:grid-cols-3 gap-20 w-full max-w-[1900px] items-center">

            <!-- 슬로건 & 설명 -->
            <div class="text-left px-4 lg:px-0">
                <h2 class="text-5xl font-extrabold text-gray-800 mb-4 leading-snug">가까? 마까?</h2>
                <p class="text-xl text-gray-600 text-base leading-relaxed">
                    이 관광지를 갈까? 아니면 저 관광지를 갈까? <br>
                    당신만의 여행 코스를 만들어 보세요.
                </p>
            </div>

            <!-- 지도 카드 -->
            <router-link to="/map"
                class="relative group w-full max-w-[1400px] overflow-hidden rounded-xl shadow-lg hover:scale-105 transition-transform">
                <img :src="mapImg" alt="지도 이미지" class="w-full h-full object-cover" />
                <div class="absolute inset-0 pointer-events-none flex items-center justify-center">
                    <div
                        class="absolute inset-0 bg-black opacity-0 group-hover:opacity-40 transition-opacity duration-300">
                    </div>
                    <div
                        class="text-white text-center z-10 opacity-0 group-hover:opacity-100 transition-opacity duration-300 px-4">
                        <p class="text-4xl font-bold mb-2">지도 보러 가기</p>
                        <p class="text-xl font-light mx-auto leading-relaxed">
                            여행지를 검색하고, 관심있는 관광지를 북마크에 추가하세요. <br />
                            그리고 여행 계획에 추가하고 경로를 살펴볼 수 있습니다!
                        </p>
                    </div>
                </div>
            </router-link>

            <!-- 게시판 카드 -->
            <router-link to="/notice-list?page=1"
                class="relative group w-full max-w-[1400px] overflow-hidden rounded-xl shadow-lg hover:scale-105 transition-transform">
                <img :src="noticeImg" alt="게시판 이미지" class="w-full h-full object-cover" />
                <div class="absolute inset-0 pointer-events-none flex items-center justify-center">
                    <div
                        class="absolute inset-0 bg-black opacity-0 group-hover:opacity-40 transition-opacity duration-300">
                    </div>
                    <div
                        class="text-white text-center z-10 opacity-0 group-hover:opacity-100 transition-opacity duration-300 px-4">
                        <p class="text-4xl font-bold mb-2">게시판 보러 가기</p>
                        <p class="text-xl font-light mx-auto leading-relaxed">
                            다른 사람들과 대화를 나누고 여행지를 공유해 보세요.
                        </p>
                    </div>
                </div>
            </router-link>
        </div>
    </main>
</template>




<script setup>
import mapImg from '@/assets/MainPageImg/mapImg.png'
import noticeImg from '@/assets/MainPageImg/noticeImg.png'
</script>
