<template>
  <div class="container">
    <div class="create__container">
      <div>
        <!-- 공지사항 글쓰기 최상단 -->
        <h1>공지사항 수정</h1>
        <hr />
      </div>

      <div>
        <!-- 글 제목, 내용 -->
        <h3>제목</h3>
        <input class="input__title" type="text" v-model="noticeDetail.title" />
        <h3>내용</h3>
        <input
          class="input__content"
          type="text"
          v-model="noticeDetail.content"
        />
      </div>

      <hr />
      <div>
        <!-- 첨부파일 -->
        <button>첨부파일 추가</button>
      </div>

      <div>
        <!-- 수정완료 버튼 -->
        <button @click="noticeUpdate">수정완료</button>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted } from "vue";
import { useStore, mapGetters, mapActions } from "vuex";
import { useRoute } from "vue-router";

export default {
  computed: {
    ...mapGetters({ noticeDetail: "getNoticeDetail" }),
  },
  methods: {
    ...mapActions(["putNoticeupdate"]),

    noticeUpdate() {
      this.putNoticeupdate({
        title: this.noticeDetail.title,
        content: this.noticeDetail.content,
        file: "",
        noticeId: this.noticeId,
      });
    },
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
