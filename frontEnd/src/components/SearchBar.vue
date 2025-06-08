<template>
    <div class="flex items-center border-2 border-purple-500 rounded-full px-4 py-2 w-full bg-white">
        <input v-model="searchText" type="text" placeholder="검색어를 입력하세요."
            class="flex-grow outline-none bg-transparent placeholder-gray-500 text-gray-800" @keyup.enter="onSearch" />
        <button @click="onSearch" class="text-black hover:text-purple-700">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="currentColor" viewBox="0 0 20 20">
                <path fill-rule="evenodd"
                    d="M12.9 14.32a8 8 0 111.414-1.414l4.387 4.387a1 1 0 01-1.414 1.414l-4.387-4.387zM14 8a6 6 0 11-12 0 6 6 0 0112 0z"
                    clip-rule="evenodd" />
            </svg>
        </button>
    </div>
</template>
<script setup>
import { ref } from 'vue'

const searchText = ref('')

const emit = defineEmits(['search'])

const onSearch = () => {
    emit('search', searchText.value)
}
</script>
