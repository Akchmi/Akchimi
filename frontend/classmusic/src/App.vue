<template>
  <div>
    <nav>
      <router-link to="/">Home</router-link> |
      <router-link to="/notice">공지사항</router-link> |
      <router-link to="/article">자유게시판</router-link> |
      <router-link to="/lecture">강의실</router-link> |
      <router-link to="/search">강사검색</router-link> |
      <router-link to="/login">로그인</router-link> |
      <router-link to="/profile">마이페이지</router-link>
    </nav>
    <br />

    <router-view />

    <button class="py-2 px-4 rounded-lg shadow-md text-white bg-blue-500">
      Click me
    </button>
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
