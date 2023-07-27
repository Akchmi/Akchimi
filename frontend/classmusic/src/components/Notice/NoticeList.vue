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
            <tr v-for="notice in notices" :key="notice.id">
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
        <button>검색</button>
      </div>
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
    const notices = computed(() => store.state.notices.noticeList);

    const searchCategory = ["전체", "제목", "내용"];

    return { notices, searchCategory };
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/notice.scss";
</style>
