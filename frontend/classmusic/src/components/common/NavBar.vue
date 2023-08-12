<template>
  <div class="out__container">
    <div class="container">
      <nav class="navbar">
        <div class="navbar__logo">
          <img
            class="mainLogo"
            src="@/assets/images/home/mainLogo2.png"
            @click="$router.push('/main')"
            alt=""
          />
        </div>

        <ul class="navbar__menu">
          <li @click="$router.push('/notice/list')">공지사항</li>
          <li @click="$router.push('/article/list')">자유게시판</li>
          <li @click="goLecture">강의실</li>
          <li @click="goSearch">강사검색</li>
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
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
  data() {
    return {};
  },
  computed: {
    ...mapGetters({ isLoggedIn: "getIsLogin" }),
  },
  methods: {
    logOut() {
      this.$store.commit("LOGOUT");
      this.$router.push("/main");
    },

    goLecture() {
      if (!this.$store.getters.getIsLogin) {
        alert("로그인이 필요한 서비스입니다.");
        this.$router.push("/login/signin");
        return;
      }
      this.$router.push("/lecture/studentongoing");
    },

    ...mapActions(["commitInstrument"]),
    ...mapActions(["commitGender"]),
    ...mapActions(["commitKeyword"]),
    ...mapActions(["commitClassDay"]),
    ...mapActions(["commitCost"]),
    ...mapActions(["commitTime"]),
    ...mapActions(["commitCareer"]),
    ...mapActions(["searchTeacher"]),

    goSearch() {
      this.commitInstrument("악기종류");
      this.commitGender("");
      this.commitKeyword("");
      this.commitClassDay("1111111");
      this.commitCost([0, 100]);
      this.commitTime([0, 100]);
      this.commitCareer([0, 100]);

      this.searchTeacher();

      this.$router.push("/search");
    },
  },
};
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.navbar__logo {
  padding-left: 0;
}

.navbar__menu {
  list-style: none;
  display: flex;
  margin: 0;
  padding-left: 0;
}

.navbar__menu li {
  padding: 8px 30px;
}

.container {
  width: 1024px;
}

.out__container {
  display: flex;
  justify-content: center;
}

a {
  cursor: pointer;
}

li {
  cursor: pointer;
  margin: 10px;
  font-size: 20px;
}

li:hover {
  cursor: pointer;
  font-size: 22px;
  margin: 6.7px;
}

.mainLogo {
  height: 70px;
  cursor: pointer;
}
</style>
