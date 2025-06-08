<template>
  <AuthLayout>
    <component :is="isLogin ? LoginCard : RegisterCard" @switch="toggleMode" />
  </AuthLayout>
</template>

<script setup>
import AuthLayout from '@/components/AuthLayout.vue'
import LoginCard from './LoginCard.vue'
import RegisterCard from './RegisterCard.vue'
import { ref } from 'vue'

const isLogin = ref(true)
const toggleMode = () => {
  isLogin.value = !isLogin.value
}

</script>
