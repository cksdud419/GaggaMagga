import { createRouter, createWebHistory } from 'vue-router'

import MainPage from '@/views/MainPage.vue'
import NoticeListPage from '@/views/NoticeListPage.vue'
import MapPage from '@/views/MapPage.vue'
import NoticePage from '@/views/NoticePage.vue'
import UserInfo from '@/views/UserInfo.vue'
import NoticeInsertPage from '@/views/NoticeInsertPage.vue'
import AuthPage from '@/views/AuthPage.vue'

import { useLoginUser } from '@/stores/loginUser'


//라우트(routes) 정의 : URL 요청에 대해 어떤 페이지(컴포넌트)를 보여줄지에 대한 매핑정보를 정의
const routes = [
  {
    path: '/',
    name: 'home',
    component: AuthPage,
  },
  {
    path: '/notice-list',
    name: 'noticeList',
    component: NoticeListPage
  },
  {
    path: '/map',
    name: 'map',
    component: MapPage
  },
  {
    path: '/notice-content',
    name: 'noticeContent',
    component: NoticePage
  },
  {
    path: '/notice-insert',
    name: 'noticeInsert',
    component: NoticeInsertPage
  },
  {
    path: '/user_info/:id',
    name: 'userInfo',
    component: UserInfo
  },
  {
    path: '/main',
    name: 'main',
    component: MainPage
  },
]

const router = createRouter({
  history: createWebHistory('/'),
  routes,
})

router.beforeEach((to, from, next) => {
  const loginStore = useLoginUser()

  // ✅ 이미 로그인한 사용자가 'home'(AuthPage)으로 접근하면 main으로 보내기
  if (to.name === 'home' && loginStore.isLoggedIn) {
    return next({ name: 'main' })
  }

  next() // 그 외는 허용
})

// 인증이 필요한 라우트 목록
const protectedRoutes = ['main', 'map', 'noticeInsert', 'userInfo', 'qnaContent']

router.beforeEach((to, from, next) => {
  const loginStore = useLoginUser()

  // 1. 로그인된 유저가 home(/) 접근 시 → main으로 리다이렉션
  if (to.name === 'home' && loginStore.isLoggedIn) {
    return next({ name: 'main' })
  }

  // 2. 로그인 안 된 유저가 보호된 경로 접근 시 → home으로 리다이렉션
  if (protectedRoutes.includes(to.name) && !loginStore.isLoggedIn) {
    alert('로그인이 필요합니다.')
    return next({ name: 'home' })
  }

  // 그 외는 허용
  next()
})


export default router
