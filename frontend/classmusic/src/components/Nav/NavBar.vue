<template>
  <div>
    <nav class="navbar">
      <div class="navbar__logo">
        <i class="fas fa-blog"></i>
        <a @click="$router.push('/')">Akchimi</a>
      </div>

      <ul class="navbar__menu">
        <li @click="$router.push('/notice/list')">공지사항</li>
        <li @click="$router.push('/article/list')">자유게시판</li>
        <li @click="goLecture">강의실</li>
        <li @click="$router.push('/search')">강사검색</li>
      </ul>

      <ul class="navbar__menu">
        <li v-if="isLoggedIn" @click="$router.push('/profile/myprofile')">
          마이페이지
        </li>
        <li v-if="isLoggedIn" @click="logOut">로그아웃</li>
        <li v-else @click="$router.push('/login/signin')">로그인</li>
      </ul>
    </nav>
  </div>
</template>

<script>
// import axios from "@/api/axios";
// import common from "@/store/actions/common";

export default {
  data() {
    return {
      // isLoggedIn: false
    };
  },
  computed: {
    isLoggedIn() {
      return this.$store.state.common.isLogin;
    },
  },
  methods: {
    logOut() {
      this.$store.commit("LOGOUT");
      this.$router.push("/");
    },

    goLecture() {
      if (!this.$store.getters.getIsLogin) {
        alert("로그인이 필요한 서비스입니다.");
        this.$router.push("/login/signin");
        return;
      }
      this.$router.push("/lecture");
    },
  },
  created() {
    this.isLoggedIn = this.$store.state.common.isLogin;
  },
};
</script>

<style scoped>
@import "@/assets/scss/templates/common.scss";
</style>
