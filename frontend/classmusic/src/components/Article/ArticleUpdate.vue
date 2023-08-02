<template>
  <div class="container">
    <div class="create__container">
      <div>
        <!-- 자유게판 글쓰기 최상단 -->
        <h1>자유게시판 수정</h1>
        <hr />
      </div>

      <div>
        <!-- 글 제목, 내용 -->
        <h3>제목</h3>
        <input class="input__title" type="text" v-model="articleDetail.title" />
        <h3>내용</h3>
        <input
          class="input__content"
          type="text"
          v-model="articleDetail.content"
        />
      </div>
      <hr />
      <div>
        <!-- 첨부파일 -->
        <button>첨부파일 추가</button>
      </div>

      <div>
        <!-- 수정완료 버튼 -->
        <button @click="articleUpdate">수정완료</button>
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
        userId: 1,
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
