<template>
  <div class="container">
    <div class="create__container">
      <div>
        <button @click="$router.push('/notice/list')">목록으로</button>
        <hr />

        <div>
          <h2>{{ noticeDetail.title }}</h2>
        </div>

        <div>
          <p>작성자: 관리자</p>
        </div>
        <hr />

        <div>
          <h3>{{ noticeDetail.content }}</h3>
        </div>
        <hr />

        <h3>첨부파일</h3>
        <div v-if="userType == 2">
          <button @click="$router.push(`/notice/update/${noticeId}`)">
            수정
          </button>
          <button @click="noticeDelete">삭제</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted } from "vue";
import { useStore, mapGetters, mapActions } from "vuex";
import { useRoute } from "vue-router";

export default {
  data() {
    return {};
  },
  computed: {
    ...mapGetters({ noticeDetail: "getNoticeDetail" }),
    ...mapGetters({ userType: "getUsertype" }),
  },
  methods: {
    ...mapActions(["deleteNoticeDelete"]),

    noticeDelete() {
      this.deleteNoticeDelete(this.noticeId);
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
