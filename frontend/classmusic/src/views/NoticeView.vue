<template>
  <div>
    <div>
      <nav class="navbar">
        <div class="navbar__logo">
          <i class="fas fa-blog"></i>
          <a @click="$router.push('/')">Akchimi</a>
        </div>

        <ul class="navbar__menu">
          <li @click="$router.push('/notice/list')">공지사항</li>
          <li @click="$router.push('/article/list')">자유게시판</li>
          <li @click="$router.push('/lecture/studentwaiting')">강의실</li>
          <li @click="$router.push('/search')">강사검색</li>
        </ul>

        <ul class="navbar__menu">
          <li @click="$router.push('/login/signin')">로그인</li>
          <li @click="$router.push('/profile/myprofile')">마이페이지</li>
        </ul>
      </nav>
    </div>
    <div class="max-w-screen-md">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/templates/common.scss";
</style>
