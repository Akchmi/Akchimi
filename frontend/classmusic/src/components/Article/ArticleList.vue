<template>
  <div>
    <div class="container">
      <!-- 자유게시판 리스트 최상단 -->
      <h1>자유게시판</h1>
      <button @click="$router.push('/article/create')">글 작성</button>
      <hr />
    </div>

    <div class="container">
      <!-- 자유게시판 게시물 리스트 -->
      <table>
        <thead>
          <tr>
            <th>글 번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일자</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="article in articles" :key="article.id">
            <td>{{ article.id }}</td>
            <td @click="$router.push('/article/detail')">
              {{ article.title }}
            </td>
            <td>{{ article.user }}</td>
            <td>{{ article.date }}</td>
          </tr>
        </tbody>
      </table>
      <hr />
    </div>

    <div class="container">
      <!-- 자유게시판 리스트 검색바 -->
      <select v-model="selectedSearchCategory">
        <option v-for="(item, idx) in searchCategory" :key="idx" :value="item">
          {{ item }}
        </option>
      </select>
      <input style="margin: 10px" type="text" v-model="searchQuery" />
      <button>검색</button>
    </div>
  </div>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";

export default {
  data() {
    return {
      searchQuery: "",
      selectedSearchCategory: "전체",
    };
  },
  setup() {
    const store = useStore();
    const articles = computed(() => store.state.articles.articles);

    const searchCategory = ["전체", "제목", "내용", "작성자"];

    return { articles, searchCategory };
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/article.scss";
</style>
