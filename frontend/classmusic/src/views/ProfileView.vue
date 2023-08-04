<template>
  <div>
    <div>
      <NavBar></NavBar>
    </div>
    <br />
    <button>
      <router-link :to="myProfilePath">내 프로필</router-link>
    </button>
    |
    <button @click="navigateToTeacherProfile">
      
      강사 프로필
    </button>
    <hr>
    <router-view></router-view>
  </div>
</template>

<script>
import NavBar from "@/components/Nav/NavBar.vue"

export default {
  components: {
    NavBar
  },
  computed: {
    myProfilePath() {
      return "/profile/myprofile";
    },
  },
  methods: {
    navigateToTeacherProfile() {   
      const userType = this.$store.state.common.userType;   
      if (userType === 0) {
        this.$router.push("/profile/teacherprofileprompt");
      } else if (userType === 1) {
        this.$router.push("/profile/teacherprofile");
      }
    },
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/templates/common.scss";
</style>
