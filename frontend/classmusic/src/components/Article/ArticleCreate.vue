<template>
  <div class="container">
    <div class="create__container">
      <div>
        <!-- 공지사항 글쓰기 최상단 -->
        <button @click="$router.push('/article/list')">목록으로</button>
        <h1>자유게시판 작성</h1>
        <hr />
      </div>

      <div>
        <!-- 글 제목, 내용 -->
        <h3>제목</h3>
        <input class="input__title" type="text" v-model="title" />
        <h3>내용</h3>
        <textarea
          class="input__content"
          type="text"
          v-model="content"
        ></textarea>
        <hr />
      </div>

      <div>
        <!-- 첨부파일 -->
        <button>첨부파일 추가</button>
      </div>

      <div>
        <!-- 작성완료 버튼 -->
        <button @click="postArticle">작성완료</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  data() {
    return {
      title: "",
      content: "",
    };
  },
  computed: {
    ...mapGetters({ createId: "getArticleCreateId" }),
  },
  methods: {
    ...mapActions(["postArticleCreate"]),

    postArticle() {
      this.postArticleCreate({
        title: this.title,
        content: this.content,
      });
    },
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/article.scss";
</style>
