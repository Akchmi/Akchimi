<template>
  <div>
    <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/notice/list">공지사항</router-link> |
      <router-link to="/article/list">자유게시판</router-link> |
      <router-link to="/lecture/studentongoing">강의실</router-link> |
      <router-link to="/search">강사검색</router-link> |
      <router-link to="/login/signin">로그인</router-link> |
      <router-link to="/profile">마이페이지</router-link>
    </nav>
    <router-view />
  </div>
</template>

<script>
export default {
  setup() {},
};
</script>
"

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
