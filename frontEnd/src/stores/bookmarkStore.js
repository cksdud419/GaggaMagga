import { defineStore } from 'pinia';
import { ref } from 'vue';
import { useLoginUser } from '@/stores/loginUser';

export const bookmarkStore = defineStore('bookmark', () => {
  const bookmarks = ref([]);
  const loginUser = useLoginUser();

  const fetchBookmarks = async () => {
    try {
      const res = await loginUser.userAi.get(`/api/bookmark/${loginUser.loginUser.id}`);
      bookmarks.value = res.data.data;
    } catch (e) {
      console.error('북마크 불러오기 실패:', e);
    }
  };

  const toggleBookmark = async (attractions_id) => {
    try {
      await loginUser.userAi.post('/api/bookmark', {
        author_id: loginUser.loginUser.id,
        attractions_id
      });
      await fetchBookmarks();
    } catch (e) {
      console.error('북마크 추가/삭제 실패:', e);
    }
  };

  return {
    bookmarks,
    fetchBookmarks,
    toggleBookmark
  };
});
