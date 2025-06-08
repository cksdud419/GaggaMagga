<template>
  <div class="min-h-screen bg-white text-gray-800 flex flex-col">
    <MainHeader />
    <MainPageBody />
    <MainFooter />
  </div>
</template>

<script setup>
import MainFooter from '@/components/main/MainFooter.vue';
import MainHeader from '@/components/main/MainHeader.vue';
import MainPageBody from '@/components/main/MainPageBody.vue';
</script>

<style scoped></style>
