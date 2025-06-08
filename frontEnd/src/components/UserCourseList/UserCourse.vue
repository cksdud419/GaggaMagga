<template>
    <li><span>{{ title }}</span></li>
</template>

<script setup>
defineProps({
    title: String
});
</script>