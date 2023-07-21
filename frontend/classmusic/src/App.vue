<template>
  <div>
    <!-- 상단 네비게이션 바-->
    <nav class="bg-gray-100 mb-12">
      <div class="mx-auto px-4">
        <div class="flex justify-between">
          <div class="flex">
            <button class="px-2" @click="$router.push('/')">Akchimi</button>
            <button class="px-2" @click="$router.push('/notice/list')">
              공지사항
            </button>
            <button class="px-2" @click="$router.push('/article/list')">
              자유게시판
            </button>
            <button
              class="px-2"
              @click="$router.push('/lecture/studentongoing')"
            >
              강의실
            </button>
            <button class="px-2" @click="$router.push('/search')">
              강사검색
            </button>
          </div>
          <div class="flex">
            <button class="px-2" @click="$router.push('/login/signin')">
              로그인
            </button>
            <button class="px-2" @click="$router.push('/profile')">
              마이페이지
            </button>
          </div>
        </div>
      </div>
    </nav>

    <div class="justify-center">
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

<style>
#app {
  text-align: center;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
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
