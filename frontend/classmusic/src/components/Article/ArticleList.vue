<template>
  <div class="container">
    <div class="article__list">
      <div>
        <!-- 자유게시판 리스트 최상단 -->
        <h1>자유게시판</h1>
        <button @click="goArticlecreate">글 작성</button>
        <hr />
      </div>
      <div>
        <!-- 자유게시판 정렬-->
        <select v-model="selectedSorttype" @change="sortTypeChange">
          <option v-for="(item, idx) in sortType" :key="idx" :value="item">
            {{ item }}
          </option>
        </select>
      </div>
      <div>
        <!-- 자유게시판 게시물 리스트 -->
        <table>
          <thead>
            <tr>
              <th>글 번호</th>
              <th>제목</th>
              <th>작성자</th>
              <th>작성일자</th>
              <th>조회수</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="article in articleList" :key="article.id">
              <td>{{ article.articleId }}</td>
              <td @click="$router.push(`/article/${article.articleId}`)">
                {{ article.title }}
              </td>
              <td>{{ article.name }}</td>
              <td>
                {{ toLocalTimeStamp(article.createdAt) }}
              </td>
              <td>{{ article.hit }}</td>
            </tr>
          </tbody>
        </table>
        <hr />
      </div>

      <div>
        <!-- 자유게시판 리스트 검색바 -->
        <select v-model="selectedSearchCategory">
          <option
            v-for="(item, idx) in searchCategory"
            :key="idx"
            :value="item"
          >
            {{ item }}
          </option>
        </select>
        <input style="margin: 10px" type="text" v-model="searchQuery" />
        <button @click="runSearch">검색</button>
      </div>

      <div>
        <!-- 페이지 번호-->
        <button @click="pageDown">이전</button>
        <button
          class="pageBtn"
          v-for="page in pages"
          :key="page"
          @click="pageChange(page)"
          v-show="page <= endPageno"
        >
          {{ page }}
        </button>
        <button @click="pageUp">다음</button>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted } from "vue";
import { useStore, mapGetters, mapActions } from "vuex";
import utils from "@/common/utils";

export default {
  data() {
    return {
      lastpage: 86,
      pageNo: 1,
      pages: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
      searchQuery: "",
      selectedSearchCategory: "전체",
      selectedSorttype: "최신순",
    };
  },
  computed: {
    ...mapGetters({ articleList: "getArticleList" }),
    ...mapGetters({ endPageno: "getEndPageNo" }),
    ...mapGetters({ isLogin: "getIsLogin" }),
  },
  methods: {
    ...mapActions(["getArticlelist"]),
    runSearch() {
      this.getArticlelist({
        searchType: this.selectedSearchCategory,
        keyword: this.searchQuery,
        pageNo: 1,
        sortType: this.selectedSorttype,
      });
    },

    pageDown() {
      if (this.pages[0] == 1) {
        alert("최근 페이지입니다.");
        return;
      }

      for (let i = 0; i < 10; i++) {
        this.pages[i] -= 10;
      }
    },

    pageUp() {
      if (this.pages[this.pages.length - 1] >= this.endPageno) {
        alert("마지막 페이지입니다.");
        return;
      }

      for (let i = 0; i < 10; i++) {
        this.pages[i] += 10;
      }
    },

    pageChange(page) {
      this.getArticlelist({
        searchType: this.selectedSearchCategory,
        keyword: this.searchQuery,
        pageNo: page,
        sortType: this.selectedSorttype,
      });
    },

    sortTypeChange() {
      this.getArticlelist({
        searchType: this.selectedSearchCategory,
        keyword: this.searchQuery,
        pageNo: 1,
        sortType: this.selectedSorttype,
      });
    },

    goArticlecreate() {
      if (this.isLogin == false) {
        alert("로그인이 필요합니다");
        this.$router.push("/login/signin");
        return;
      }
      this.$router.push("/article/create");
    },

    toLocalTimeStamp(unixTimeStamp) {
      return utils.unixTimeStampToLocalTimeStamp(unixTimeStamp);
    },
  },
  setup() {
    const store = useStore();
    const searchCategory = ["전체", "제목", "내용", "작성자"];
    const sortType = ["최신순", "조회순"];

    onMounted(() => {
      store.dispatch("getArticlelist", {
        searchType: "전체",
        keyword: "",
        pageNo: 1,
        sortType: "최신순",
      });
    });

    return { searchCategory, sortType };
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/article.scss";
</style>
