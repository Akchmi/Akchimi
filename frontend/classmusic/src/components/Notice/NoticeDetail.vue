<template>
  <div class="container">
    <div class="create__container">
      <div class="create__content">
        <div style="width: 95%; padding: 10px">
          <div class="notice-detail__header">
            <p style="margin: 10px; font-size: 28px; word-break: break-all">
              {{ noticeDetail.title }}
            </p>
            <div
              style="
                display: flex;
                justify-content: space-between;
                margin: 10px;
              "
            >
              <p>작성자 : 관리자</p>
              <p>
                작성일 :
                {{ toLocalTimeStamp(noticeDetail.createAt) }}
              </p>
            </div>
          </div>
          <hr />
          <h3
            style="
              margin: 20px 5px;
              min-height: 300px;
              white-space: pre-line;
              word-break: break-all;
            "
          >
            {{ noticeDetail.content }}
          </h3>

          <hr />
          <div class="notice-detail__buttons">
            <button @click="$router.push('/notice/list')">목록으로</button>
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
          </div>

          <!-- <h3>첨부파일</h3> -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted } from "vue";
import { useStore, mapGetters, mapActions } from "vuex";
import { useRoute } from "vue-router";
import utils from "@/common/utils";

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
    toLocalTimeStamp(unixTimeStamp) {
      return utils.unixTimeStampToLocalTimeStamp(unixTimeStamp);
    },
  },
  setup() {
    const store = useStore();
    const route = useRoute();
    const noticeId = route.params.id;
    const todayDate = utils.todayDate();

    onMounted(() => {
      store.dispatch("getNoticedetail", noticeId);
    });

    return { noticeId, todayDate };
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/notice.scss";

.notice-detail__buttons {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
}

.notice-detail__header {
  display: flex;
  flex-direction: column;

  justify-content: space-between;
  margin-bottom: 15px;
}
</style>
