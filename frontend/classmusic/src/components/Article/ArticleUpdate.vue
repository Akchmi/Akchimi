<template>
  <div class="container">
    <div class="create__container">
      <div>
        <!-- 수정 최상단 -->
        <button class="createButton" @click="$router.go(-1)">돌아가기</button>

        <h1 style="margin: 20px; font-size: 28px">자유게시판 수정</h1>
        <hr />
      </div>

      <!-- 글 제목, 내용 -->
      <div class="create__content">
        <div style="width: 90%">
          <h3 style="margin: 10px; font-size: 24px">제목</h3>
          <input
            class="input__title"
            type="text"
            v-model="articleDetail.title"
            maxlength="50"
          />
          <h3 style="margin: 10px; font-size: 24px">내용</h3>
          <textarea
            class="input__content"
            type="text"
            v-model="articleDetail.content"
            maxlength="200"
          ></textarea>
        </div>
      </div>
      <div class="create__content__bottom">
        <div>
          <!-- 첨부파일 -->
          <button class="createButton">첨부파일 추가</button>
        </div>

        <div>
          <!-- 작성완료 버튼 -->
          <button class="createButton" @click="articleUpdate">수정완료</button>
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
    ...mapGetters({ articleDetail: "getArticleDetail" }),
  },
  methods: {
    ...mapActions(["putArticleupdate"]),

    articleUpdate() {
      this.putArticleupdate({
        title: this.articleDetail.title,
        content: this.articleDetail.content,
        file: "",
        userId: this.articleDetail.userId,
        articleId: this.articleId,
      });
    },
  },
  setup() {
    const store = useStore();
    const route = useRoute();
    const articleId = route.params.id;

    onMounted(() => {
      store.dispatch("getArticledetail", articleId);
    });

    return { articleId };
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/article.scss";
</style>
