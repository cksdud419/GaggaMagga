<template>
  <nav class="flex flex-col w-19 h-screen bg-gray-50 shadow-lg border-r border-gray-300 min-w-19">
    <ul>
      <!-- 홈 버튼 -->
      <li>
        <router-link to="/"
          class="tooltip tooltip-right font-semibold flex justify-center items-center w-full p-3 hover:bg-blue-100"
          data-tip="홈">

          <svg xmlns="http://www.w3.org/2000/svg" width="40" height="60" viewBox="0 0 24 24" fill="none"
            stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
            class="icon icon-tabler icons-tabler-outline icon-tabler-home">
            <path stroke="none" d="M0 0h24v24H0z" fill="none" />
            <path d="M5 12l-2 0l9 -9l9 9l-2 0" />
            <path d="M5 12v7a2 2 0 0 0 2 2h10a2 2 0 0 0 2 -2v-7" />
            <path d="M9 21v-6a2 2 0 0 1 2 -2h2a2 2 0 0 1 2 2v6" />
          </svg>
        </router-link>
      </li>

      <!-- 검색 버튼 -->
      <li>
        <button class="tooltip tooltip-right flex justify-center items-center w-full p-3" data-tip="검색"
          @click="$emit('toggle-sidebar', 'search'); activate('search')" :class="[
            'flex justify-center items-center w-full p-3',
            activeButton === 'search'
              ? 'bg-blue-400 text-white font-semibold'
              : 'hover:bg-blue-100 font-bold'
          ]">
          <svg xmlns="http://www.w3.org/2000/svg" width="40" height="60" viewBox="0 0 24 24" fill="none"
            stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
            class="icon icon-tabler icons-tabler-outline icon-tabler-search">
            <path stroke="none" d="M0 0h24v24H0z" fill="none" />
            <path d="M10 10m-7 0a7 7 0 1 0 14 0a7 7 0 1 0 -14 0" />
            <path d="M21 21l-6 -6" />
          </svg>
        </button>
      </li>

      <!-- 북마크 버튼 -->
      <li>
        <button class="tooltip tooltip-right flex justify-center items-center w-full p-3" data-tip="북마크"
          @click="$emit('toggle-sidebar', 'bookmark'); activate('bookmark')" :class="[
            'flex justify-center items-center w-full p-3',
            activeButton === 'bookmark'
              ? 'bg-blue-400 text-white font-semibold'
              : 'hover:bg-blue-100 font-bold'
          ]">
          <svg xmlns="http://www.w3.org/2000/svg" width="40" height="60" viewBox="0 0 24 24" fill="currentColor"
            class="icon icon-tabler icons-tabler-filled icon-tabler-heart">
            <path stroke="none" d="M0 0h24v24H0z" fill="none" />
            <path
              d="M6.979 3.074a6 6 0 0 1 4.988 1.425l.037 .033l.034 -.03a6 6 0 0 1 4.733 -1.44l.246 .036a6 6 0 0 1 3.364 10.008l-.18 .185l-.048 .041l-7.45 7.379a1 1 0 0 1 -1.313 .082l-.094 -.082l-7.493 -7.422a6 6 0 0 1 3.176 -10.215z" />
          </svg>
        </button>
      </li>

      <!-- 여행 계획 버튼 -->
      <li>
        <button class="tooltip tooltip-right flex justify-center items-center w-full p-3" data-tip="여행 계획"
          @click="$emit('toggle-sidebar', 'course'); activate('course')" :class="[
            'flex justify-center items-center w-full p-3',
            activeButton === 'course'
              ? 'bg-blue-400 text-white font-semibold'
              : 'hover:bg-blue-100 font-bold'
          ]">
          <svg xmlns="http://www.w3.org/2000/svg" width="40" height="60" viewBox="0 0 24 24" fill="none"
            stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
            class="icon icon-tabler icons-tabler-outline icon-tabler-route">
            <path stroke="none" d="M0 0h24v24H0z" fill="none" />
            <path d="M3 19a2 2 0 1 0 4 0a2 2 0 0 0 -4 0" />
            <path d="M19 7a2 2 0 1 0 0 -4a2 2 0 0 0 0 4z" />
            <path d="M11 19h5.5a3.5 3.5 0 0 0 0 -7h-8a3.5 3.5 0 0 1 0 -7h4.5" />
          </svg>
        </button>
      </li>

      <!-- 테스트 ~~~~~~~~~~~~ 게시글 버튼 -->
      <!-- 게시글 버튼 -->
      <!-- <li>
        <button class="tooltip tooltip-right flex justify-center items-center w-full p-3" data-tip="게시글"
          @click="$emit('toggle-sidebar', 'notice'); activate('notice')"
          :class="[
          'flex justify-center items-center w-full p-3',
          activeButton === 'notice'
          ? 'bg-blue-400 text-white font-semibold'
          : 'hover:bg-blue-100 font-bold'
          ]"
          >
          <svg xmlns="http://www.w3.org/2000/svg" width="40" height="60" viewBox="0 0 24 24" fill="none"
            stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
            class="icon icon-tabler icons-tabler-outline icon-tabler-route">
            <path stroke="none" d="M0 0h24v24H0z" fill="none" />
            <path d="M3 19a2 2 0 1 0 4 0a2 2 0 0 0 -4 0" />
            <path d="M19 7a2 2 0 1 0 0 -4a2 2 0 0 0 0 4z" />
            <path d="M11 19h5.5a3.5 3.5 0 0 0 0 -7h-8a3.5 3.5 0 0 1 0 -7h4.5" />
          </svg>
        </button>
      </li> -->

    </ul>

  </nav>

</template>

<script>
export default {
  data() {
    return {
      activeButton: 'search',
    };
  },
  methods: {
    activate(name) {
      this.activeButton = name;
    },
  },
};
</script>
