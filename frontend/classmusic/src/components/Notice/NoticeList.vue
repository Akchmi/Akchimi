<template>
  <div>
    <!-- 공지사항 리스트 최상단-->
    <div class="flex justify-between">
      <p class="text-4xl">공지사항</p>
      <button
        class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full"
        @click="$router.push('/notice/create')"
      >
        글 작성
      </button>
    </div>
    <hr class="my-12" />

    <!-- 공지사항 리스트 -->
    <div>
      <table>
        <thead>
          <tr>
            <th class="w-1/12">글 번호</th>
            <th class="w-7/12">제목</th>
            <th class="w-2/12">작성자</th>
            <th class="w-2/12">작성 날짜</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="notice in notices" :key="notice.id">
            <td>{{ notice.id }}</td>
            <td @click="$router.push('/notice/detail')">{{ notice.title }}</td>
            <td>관리자</td>
            <td>{{ notice.date }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <hr class="my-12" />

    <!-- 공지사항 리스트 검색 바-->
    <div>
      <select class="border mx-4" v-model="selectedSearchCategory">
        <option v-for="(item, idx) in searchCategory" :key="idx" :value="item">
          {{ item }}
        </option>
      </select>
      <input class="border mx-4" type="text" v-model="searchQuery" />
      <button
        class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 mx-4"
      >
        검색
      </button>
    </div>

    {{ selectedSearchCategory }} <br />
    {{ searchQuery }}
  </div>
</template>

<script>
//import { computed } from "vue";
import { useStore } from "vuex";

export default {
  data() {
    return {
      selectedSearchCategory: "전체",
      searchCategory: ["전체", "제목", "내용"],
      searchQuery: "",
    };
  },
  setup() {
    const store = useStore();
    const notices = store.state.notices.notices;

    return {
      notices,
    };
  },
};
</script>

<style></style>
