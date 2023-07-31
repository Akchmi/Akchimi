<template>
  <div class="container">
    <div class="notice__list">
      <div>
        <!-- 공지사항 리스트 최상단 -->
        <h1>공지사항</h1>
        <button @click="$router.push('/notice/create')">글 작성</button>
        <hr />
      </div>

      <div>
        <!-- 공지사항 게시물 리스트 -->
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
            <tr v-for="notice in noticeList" :key="notice.id">
              <td>{{ notice.id }}</td>
              <td @click="$router.push('/notice/detail')">
                {{ notice.title }}
              </td>
              <td>관리자</td>
              <td>{{ notice.createdAt }}</td>
            </tr>
          </tbody>
        </table>
        <hr />
      </div>

      <div>
        <!-- 공지사항 리스트 검색바 -->
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
        <button @click="searchNoticelist">검색</button>
      </div>
    </div>

    {{ noticeList }}
    {{ searchQuery }}
    {{ selectedSearchCategory }}
    {{ pageNo }}
  </div>
</template>

<script>
import { onMounted, ref } from "vue";
import { useStore, mapGetters } from "vuex";
// import axios from "@/common/axios";

export default {
  computed: {
    ...mapGetters({ noticeList: "getNoticeList" }),
  },
  setup() {
    const pageNo = ref(1);
    const searchQuery = ref("");
    const selectedSearchCategory = ref("전체");
    const searchCategory = ["전체", "제목", "내용"];
    const store = useStore();

    // const searchNoticelist = () => {
    //   const query = searchQuery.value;
    //   const category = selectedSearchCategory.value;
    //   const page = pageNo.value;

    //   store.dispatch("searchNoticelist", query, category, page);
    // };

    onMounted(() => {
      store.dispatch("getNoticelist");
    });

    return { searchCategory, pageNo, searchQuery, selectedSearchCategory };
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/notice.scss";
</style>
