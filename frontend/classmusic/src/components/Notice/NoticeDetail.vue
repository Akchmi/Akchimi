<template>
  <div>
    <!-- 공지사항 상세보기 최상단-->
    <div class="flex justify-between">
      <p class="text-4xl">공지사항</p>
    </div>
    <hr class="my-12" />

    <!-- 공지사항 상세보기 제목/내용-->
    <div>
      <span class="text-2xl flex justify-between">제목입력</span>

      <span class="text-2xl flex justify-between">내용입력</span>
    </div>
    {{ noticedetail }}
  </div>
</template>

<script>
import { useStore } from "vuex";

export default {
  setup() {
    const store = useStore();
    const noticedetail = store.state.notices.noticedetail;

    return {
      noticedetail,
    };
  },
};
</script>

<style></style>
