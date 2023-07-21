<template>
  <div>
    <nav class="navbar">
      <div class="navbar__logo">
        <i class="fas fa-blog"></i>
        <a>Akchimi</a>
      </div>

      <ul class="navbar__menu">
        <li><a>공지사항</a></li>
        <li><a>자유게시판</a></li>
        <li><a>강의실</a></li>
        <li><a>강사검색</a></li>
        <li><a>로그인</a></li>
        <li><a>마이페이지</a></li>
      </ul>
    </nav>

    <!-- 구성 화면 -->
    <div class="container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  setup() {},
};
</script>
"

<style lang="scss" scoped>
@import "@/assets/scss/templates/common.scss";
</style>
