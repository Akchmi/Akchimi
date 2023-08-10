<template>
  <div class="container">
    <div class="create__container">
      <div class="create__content">
        <div style="width: 90%">
          <button class="detailButton" @click="$router.push('/notice/list')">
            목록으로
          </button>
          <p style="margin: 10px; font-size: 28px">
            {{ noticeDetail.title }}
          </p>
          <div class="detailTitleBottom">
            <p>작성자 : 관리자</p>
          </div>
          <div
            v-if="userType == 2"
            style="display: flex; justify-content: right"
          >
            <button
              style="margin-right: 10px"
              @click="$router.push(`/notice/update/${noticeId}`)"
            >
              수정
            </button>
            <button @click="noticeDelete">삭제</button>
          </div>
          <hr />
          <h3 style="margin: 10px; font-size: 24px; min-height: 300px">
            {{ noticeDetail.content }}
          </h3>

          <hr />

          <h3>첨부파일</h3>
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

.detailButton {
  font-size: 16px;
  margin: 10px;
}

.detailTitleBottom {
  display: flex;
  justify-content: space-between;
  padding: 0px 0px 10px 10px;
}
</style>
