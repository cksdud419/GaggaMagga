<template>
    <div class="fixed bottom-4 right-4 z-999">
        <!-- Chat Box -->
        <transition name="fade">
            <div v-if="open" class="w-80 h-[500px] bg-base-200 rounded-xl shadow-xl mt-2 flex flex-col overflow-hidden">
                <div class="bg-base-100 p-3 border-b font-bold flex justify-between items-center">
                    챗봇
                    <button class="btn btn-sm btn-ghost" @click="close">✕</button>
                </div>

                <div class="p-3 flex-1 overflow-y-auto" ref="chatBox">
                    <div v-for="(msg, index) in messages" :key="index" class="mb-3">
                        <div class="chat" :class="msg.role === 'user' ? 'chat-end' : 'chat-start'">
                            <div class="chat-bubble"
                                :class="msg.role === 'user' ? 'chat-bubble-primary' : 'chat-bubble-secondary'">
                                <span v-if="msg.role === 'user'">{{ msg.content }}</span>
                                <span v-else v-html="msg.content"></span>
                            </div>
                        </div>
                    </div>
                </div>

                <form @submit.prevent="sendMessage" class="flex gap-2 p-3 border-t">
                    <input v-model="input" class="input input-bordered flex-1" placeholder="메시지를 입력하세요" />
                    <button type="submit" class="btn btn-primary" :disabled="loading">
                        {{ loading ? '...' : '전송' }}
                    </button>
                </form>
            </div>
        </transition>
    </div>
</template>

<script setup>
import { ref, nextTick } from 'vue'
import { marked } from 'marked'
import { useLoginUser } from '@/stores/loginUser'

defineProps({
    open: Boolean
});
const emit = defineEmits(['close']);

const input = ref('')
const messages = ref([])
const loading = ref(false)
const chatBox = ref(null)

const loginUser = useLoginUser();
const close = () => {
    emit('close');
}
const sendMessage = async () => {
    const userAi = loginUser.userAi;
    if (!input.value.trim()) return
    const send = input.value;
    input.value = ''

    messages.value.push({ role: 'user', content: send })
    loading.value = true

    try {
        const response = await userAi.post('/api/chat', send)
        messages.value.push({ role: 'bot', content: marked(response.data.data) })
    } catch (error) {
        messages.value.push({ role: 'bot', content: marked('**오류가 발생했습니다.**') })
        console.error(error)
    } finally {
        loading.value = false
        nextTick(() => {
            chatBox.value.scrollTop = chatBox.value.scrollHeight
        })
    }
}
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
    transition: all 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
    opacity: 0;
    transform: scale(0.95);
}

.chat-bubble {
    max-width: 75%;
    word-wrap: break-word;
}
</style>