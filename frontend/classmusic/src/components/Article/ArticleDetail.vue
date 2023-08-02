<template>
  <div class="container">
    <div class="create__container">
      <div>
        <button @click="$router.push('/article/list')">목록으로</button>
        <hr />
        <div>
          <h2>{{ articleDetail.title }}</h2>
        </div>

        <div>
          <p>조회수:{{ articleDetail.hit }}</p>
          <p>작성자:{{ articleDetail.name }}</p>
        </div>
        <hr />

        <div>
          <h3>{{ articleDetail.content }}</h3>
        </div>
        <hr />

        <h3>첨부파일</h3>

        <button @click="articleUpdate">수정</button>
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
    ...mapGetters({ articleDetail: "getArticleDetail" }),
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
