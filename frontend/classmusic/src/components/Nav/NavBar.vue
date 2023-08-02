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
        <li @click="$router.push('/lecture/studentwaiting')">강의실</li>
        <li @click="$router.push('/search')">강사검색</li>
      </ul>

      <ul class="navbar__menu">
        <li v-if="isLoggedIn" @click="$router.push('/profile/myprofile')">마이페이지</li>
        <li v-if="isLoggedIn" @click="logOut">로그아웃</li>
        <li v-else @click="$router.push('/login/signin')">로그인</li>
      </ul>      
    </nav>
  </div>
</template>

<script>
import axios from "@/api/axios";

export default {
  data() {
    return {
      isLoggedIn: false
    }
  },
  created() {
    this.updateLoginStatus();
  },
  methods: {
    updateLoginStatus() {
      const vuexData = JSON.parse(localStorage.getItem("vuex"));
      this.isLoggedIn = vuexData && (vuexData.common.isLogin === 'true' || vuexData.common.isLogin === true) ? true : false;
    },
    async logOut() {
      try {
        const response = await axios.get('/users/logout');
        if (response.status === 200) {
          localStorage.setItem('vuex', JSON.stringify({ 
            common: { 
              isLogin: 'false',
              accessToken: null,
              id: null,
              userId: null 
            }
          }));
          this.updateLoginStatus();
        } else {
          console.error('Logout fail');
        }
      } catch(error) {
        console.error(error)
      }
    }
  },
  watch: {
    'localStorage.vuex': function() {
      this.updateLoginStatus();
    }
  }
}
</script>

<style scoped>
@import "@/assets/scss/templates/common.scss";
</style>
