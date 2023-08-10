<template>
  <div>
    <div>
      <NavBar></NavBar>
    </div>
    <TheBanner :title="'프로필'" />
    <br />
    <div class="top-button-container">
      <button>
        <router-link :to="myProfilePath">내 프로필</router-link>
      </button>
      |
      <button @click="navigateToTeacherProfile">강사 프로필</button>
    </div>
    <br />
    <router-view></router-view>
  </div>
</template>

<script>
import NavBar from "@/components/common/NavBar.vue";
import TheBanner from "@/components/common/TheBanner";

export default {
  components: {
    NavBar,
    TheBanner,
  },
  computed: {
    myProfilePath() {
      return "/profile/myprofile";
    },
  },
  methods: {
    navigateToTeacherProfile() {
      const userType = this.$store.state.common.userType;     
      const teacherId = JSON.parse(localStorage.getItem("vuex")).common
        .teacherId;
      if (userType === 0) {
        this.$router.push("/profile/teacherprofileprompt");
        
      } else if (userType === 1) {
        this.$router.push(`/profile/teacherprofile/${teacherId}`);     
       
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.top-button-container{
  width: 800px;
  margin: 0 auto;
}
</style>
