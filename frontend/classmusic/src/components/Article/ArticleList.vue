<template>
  <div class="container">
    <div class="article__list">
      <div class="articleTableBanner">
        <!-- 자유게시판 정렬-->
        <select v-model="selectedSorttype" @change="sortTypeChange">
          <option v-for="(item, idx) in sortType" :key="idx" :value="item">
            {{ item }}
          </option>
        </select>
        <button id="articleCreateButton" @click="goArticlecreate">
          글 작성
        </button>
      </div>
      <div>
        <!-- 자유게시판 게시물 리스트 -->
        <div>
          <table>
            <thead>
              <tr>
                <th style="width: 10%">글 번호</th>
                <th style="width: 45%">제목</th>
                <th style="width: 20%">작성자</th>
                <th style="width: 15%">작성일</th>
                <th style="width: 10%">조회수</th>
              </tr>
            </thead>

            <tbody>
              <tr v-for="article in articleList" :key="article.id">
                <td>{{ article.articleId }}</td>
                <td
                  @click="$router.push(`/article/${article.articleId}`)"
                  style="cursor: pointer"
                >
                  {{ article.title }}
                </td>
                <td>{{ article.name }}</td>
                <td>
                  <p
                    v-if="
                      todayDate !=
                      toLocalTimeStamp(article.createdAt).substr(0, 10)
                    "
                  >
                    {{ toLocalTimeStamp(article.createdAt).substr(0, 10) }}
                  </p>
                  <p
                    v-if="
                      todayDate ==
                      toLocalTimeStamp(article.createdAt).substr(0, 10)
                    "
                  >
                    {{ toLocalTimeStamp(article.createdAt).substr(10) }}
                  </p>
                </td>
                <td>{{ article.hit }}회</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <!-- 자유게시판 리스트 검색바 -->
      <div class="articleSearchBar">
        <div class="articleSearchSelect">
          <select
            class="articleSearchSelectBar"
            v-model="selectedSearchCategory"
          >
            <option
              v-for="(item, idx) in searchCategory"
              :key="idx"
              :value="item"
            >
              {{ item }}
            </option>
          </select>
        </div>
        <input
          class="articleSearchInput"
          style="margin: 10px"
          type="text"
          v-model="searchQuery"
          @keyup.enter="runSearch"
        />

        <img
          @click="runSearch"
          id="articleSearchButton"
          src="@/assets/images/home/searchButton.png"
          alt="메인검색버튼"
        />
      </div>

      <!-- 페이지 번호-->

      <div class="articlePageBox">
        <button @click="pageDown" v-if="pages[0] != 1">이전</button>
        <button
          class="pageBtn"
          v-for="page in pages"
          :key="page"
          @click="pageChange(page)"
          v-show="page <= endPageno"
        >
          {{ page }}
        </button>
        <button @click="pageUp" v-if="pages[pages.length - 1] < endPageno">
          다음
        </button>
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
      pageNo: 1,
      pages: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
      searchQuery: "",
      selectedSearchCategory: "전체",
      selectedSorttype: "최신순",
    };
  },
  computed: {
    ...mapGetters({ articleList: "getArticleList" }),
    ...mapGetters({ endPageno: "getArticleEndPageNo" }),
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
      // if (this.pages[0] == 1) {
      //   alert("1페이지 입니다.");
      //   return;
      // }

      for (let i = 0; i < 10; i++) {
        this.pages[i] -= 10;
      }
    },

    pageUp() {
      // if (this.pages[this.pages.length - 1] >= this.endPageno) {
      //   alert("마지막 페이지입니다.");
      //   return;
      // }

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
    const todayDate = utils.todayDate();

    onMounted(() => {
      if (store.state.articles.articleList.length == 0) {
        store.dispatch("getArticlelist", {
          searchType: "전체",
          keyword: "",
          pageNo: 1,
          sortType: "최신순",
        });
      }
    });

    return {
      searchCategory,
      sortType,
      todayDate,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/article.scss";

#articleCreateButton {
  padding: 5px 10px;
  // font-size: 16px;
}
</style>
