<template>
  <div class="out__container">
    <div class="container">
      <nav class="navbar">
        <div class="navbar__logo">
          <img
            class="mainLogo"
            src="@/assets/images/home/mainLogo2.png"
            @click="clickLogo"
            alt=""
          />
        </div>

        <ul class="navbar__menu">
          <li
            :class="{ navColor: nowNavPage == 'notice' }"
            @click="clickNotice"
          >
            공지사항
          </li>
          <li
            :class="{ navColor: nowNavPage == 'article' }"
            @click="clickArticle"
          >
            자유게시판
          </li>
          <li
            :class="{ navColor: nowNavPage == 'lecture' }"
            @click="clickLecture"
          >
            강의실
          </li>
          <li
            :class="{ navColor: nowNavPage == 'search' }"
            @click="clickSearch"
          >
            강사검색
          </li>
        </ul>

        <ul class="navbar__menu">
          <li
            :class="{ navColor: nowNavPage == 'mypage' }"
            if="isLoggedIn"
            @click="clickMypage"
          >
            마이페이지
          </li>
          <li v-if="isLoggedIn" @click="logOut">로그아웃</li>
          <li
            :class="{ navColor: nowNavPage == 'login' }"
            v-else
            @click="clickLogin"
          >
            로그인
          </li>
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
    ...mapGetters({ nowNavPage: "getNowNavPage" }),
  },
  methods: {
    logOut() {
      this.$store.commit("LOGOUT");
      this.$router.push("/main");
    },

    ...mapActions(["commitInstrument"]),
    ...mapActions(["commitGender"]),
    ...mapActions(["commitKeyword"]),
    ...mapActions(["commitClassDay"]),
    ...mapActions(["commitCost"]),
    ...mapActions(["commitTime"]),
    ...mapActions(["commitCareer"]),
    ...mapActions(["searchTeacher"]),
    ...mapActions(["commitOrderBy"]),

    ...mapActions(["changeNavPage"]),

    clickLogo() {
      this.changeNavPage("main");
      this.$router.push("/main");
    },
    clickNotice() {
      this.changeNavPage("notice");
      this.$router.push("/notice/list");
    },
    clickArticle() {
      this.changeNavPage("article");
      this.$router.push("/article/list");
    },
    clickLecture() {
      if (!this.$store.getters.getIsLogin) {
        alert("로그인이 필요한 서비스입니다.");
        this.$router.push("/login/signin");
        return;
      }
      this.changeNavPage("lecture");
      this.$router.push("/lecture/studentongoing");
    },
    clickSearch() {
      this.changeNavPage("search");
      this.commitInstrument("악기종류");
      this.commitGender("");
      this.commitKeyword("");
      this.commitClassDay("1111111");
      this.commitCost([0, 100]);
      this.commitTime([0, 100]);
      this.commitCareer([0, 100]);
      this.commitOrderBy("최신순");
      this.searchTeacher();

      this.$router.push("/search");
    },
    clickMypage() {
      this.changeNavPage("mypage");
      this.$router.push("/profile/myprofile");
    },
    clickLogin() {
      this.changeNavPage("login");
      this.$router.push("/login/signin");
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
  color: #edd9b7;
}

.mainLogo {
  height: 70px;
  cursor: pointer;
}

.navColor {
  color: #edd9b7;
}
</style>
