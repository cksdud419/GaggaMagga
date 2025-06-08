import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

export const useLoginUser = defineStore(
  'loginUser',
  () => {
    const _isLoggedIn = ref(false)
    const _loginUser = ref({})
    const _accessToken = ref('')
    const _refreshToken = ref('')
    const router = useRouter()

    const isLoggedIn = computed(() => _isLoggedIn.value)
    const loginUser = computed(() => _loginUser.value)

    const userAi = ref(
      axios.create({
        baseURL: 'http://localhost:8080',
        // timeout: 10000,
      }),
    )
    userAi.value.interceptors.request.use((config) => {
      if (_accessToken.value) {
        config.headers.Authorization = `Bearer ${_accessToken.value}`
      }
      return config
    })
    // 응답 인터셉터 - 401이면 refreshToken 사용해서 재발급
    userAi.value.interceptors.response.use(
      (response) => response,
      async (error) => {
        const originalRequest = error.config

        if (error.response?.status === 404) {
          return
        }
        // 401 오류이면서 아직 재시도하지 않은 요청이라면
        console.log('혹시 갱신이 필요하나?')
        if (error.response?.status === 401 && !originalRequest._retry) {
          originalRequest._retry = true
          console.log('갱신이 필요하네!')

          try {
            // refreshToken으로 새 accessToken 요청
            const res = await axios.post('http://localhost:8080/api/user/refresh', null, {
              headers: {
                'Refresh-Token': _refreshToken.value,
              },
            })

            console.log(res.data)

            const newAccessToken = res.data.data.accessToken
            const newRefreshToken = res.data.data.refreshToken
            _accessToken.value = newAccessToken
            _refreshToken.value = newRefreshToken

            console.log('newAccessToken' + newAccessToken)
            console.log('newRefreshToken' + newRefreshToken)
            // Authorization 헤더 갱신 후 원래 요청 재시도
            originalRequest.headers.Authorization = `Bearer ${newAccessToken}`
            return axios(originalRequest)
          } catch (refreshError) {
            console.error('Token refresh failed:', refreshError)
            // 재발급 실패 시 logout 로직 또는 에러 전파
            return Promise.reject(refreshError)
          }
        } else {
          alert('먼저 로그인을 해주세요!')
          router.push({ name: 'home' })
        }

        return Promise.reject(error)
      },
    )

    const fetchCurrentUser = async () => {
      try {
        const response = await userAi.value.get('/api/user/me')

        const user = response.data.data // ResultDTO<User> 형태로 왔을 때
        console.log('유저 정보:', user)
        _loginUser.value = user
      } catch (error) {
        console.error('유저 정보 요청 실패', error)
      }
    }

    const login = async (id, password) => {
      const params = new URLSearchParams()
      params.append('id', id)
      params.append('password', password)

      try {
        const response = await axios.post('http://localhost:8080/api/user/login', params, {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          },
        })

        const { accessToken, refreshToken } = response.data
        _accessToken.value = accessToken
        _refreshToken.value = refreshToken
        _isLoggedIn.value = true
        console.log('로그인 성공!', _accessToken.value, _refreshToken.value)

        await fetchCurrentUser()
      } catch (err) {
        console.error('로그인 실패', err)
        alert('아이디 또는 비밀번호를 확인해주세요.')
        router.push({ name: 'auth' }) // 실패 시 로그인 페이지로 다시 이동
      }

      resetTokenTime()
      router.push({ name: 'main' })
    }

    const logout = async () => {
      // TODO: 02-2. logout 처리해보자.
      try {
        console.log(_accessToken.value)
        console.log(_refreshToken.value)
        await axios.post('http://localhost:8080/api/user/logout', null, {
          headers: {
            'Refresh-Token': _refreshToken.value,
          },
        })
      } finally {
        _loginUser.value = {}
        _isLoggedIn.value = false
        _accessToken.value = ''
        _refreshToken.value = ''
        router.push({ name: 'home' })
        console.log('로그아웃 성공!')
      }
    }

    // token의 만료 상태를 확인하기 위한 부분----------
    const tokenTime = ref(0)
    let intervalId
    const resetTokenTime = () => {
      window.clearInterval(intervalId)
      tokenTime.value = 0
      intervalId = setInterval(() => {
        tokenTime.value++
      }, 1000)
    }

    const tokenStatus = computed(() => {
      if (!_refreshToken.value) {
        window.clearInterval(intervalId)
        tokenTime.value = 0
        return '로그아웃 상태'
      }
      if (tokenTime.value > 120) {
        return 'refresh token 만료'
      } else if (tokenTime.value > 60) {
        return 'access token 만료'
      } else {
        return 'token 유효'
      }
    })
    // token의 만료 상태를 확인하기 위한 부분----------

    return {
      isLoggedIn,
      loginUser,
      login,
      logout,
      _loginUser,
      _isLoggedIn,
      tokenTime,
      tokenStatus,
      userAi,
      _accessToken,
      _refreshToken,
    }
  },
  {
    persist: true,
  },
)
