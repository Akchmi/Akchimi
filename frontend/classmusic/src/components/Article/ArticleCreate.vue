<template>
  <div class="container">
    <div class="create__container">
      <div>
        <!-- 공지사항 글쓰기 최상단 -->
        <button class="createButton" @click="$router.push('/article/list')">
          목록으로
        </button>
        <h1 style="margin: 20px; font-size: 28px">자유게시판 작성</h1>
        <hr />
      </div>

      <!-- 글 제목, 내용 -->
      <div class="create__content">
        <div style="width: 90%">
          <h3 style="margin: 10px; font-size: 24px">제목</h3>
          <input
            class="input__title"
            type="text"
            v-model="title"
            maxlength="50"
          />
          <h3 style="margin: 10px; font-size: 24px">내용</h3>
          <textarea
            class="input__content"
            type="text"
            v-model="content"
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
          <button class="createButton" @click="postArticle">작성완료</button>
        </div>
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

.createButton {
  font-size: 16px;
}
</style>
