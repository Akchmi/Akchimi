<template>
  <div class="container">
    <div class="create__container">
      <div>
        <button @click="$router.push('/notice/list')">목록으로</button>
        <hr />
        글 id: {{ noticeId }}
        {{ noticeDetail }}
        <div>
          <h2>{{ noticeDetail.title }}</h2>
        </div>

        <div>
          <p>조회수: {{ noticeDetail.hit }}</p>
          <p>작성자: 관리자</p>
        </div>
        <hr />

        <div>
          <h3>{{ noticeDetail.content }}</h3>
        </div>
        <hr />

        <h3>첨부파일</h3>

        <button @click="$router.push('/notice/update')">수정</button>
        <button>삭제</button>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted } from "vue";
import { useStore, mapGetters } from "vuex";
import { useRoute } from "vue-router";

export default {
  data() {
    return {};
  },
  computed: {
    ...mapGetters({ noticeDetail: "getNoticeDetail" }),
  },
  setup() {
    const store = useStore();
    const route = useRoute();
    const noticeId = route.params.id;

    onMounted(() => {
      store.dispatch("getNoticedetail", noticeId);
    });

    return { noticeId };
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/notice.scss";
</style>
