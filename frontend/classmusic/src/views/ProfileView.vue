<template>
  <div>
    <div>
      <NavBar></NavBar>
    </div>
    <br />
    <button>
      <router-link to="/profile/myprofile">내 프로필</router-link>
    </button>
    |
    <button>
      <router-link to="/profile/teacherprofile">강사 프로필</router-link>
    </button>
    <router-view></router-view>
  </div>
</template>

<script>
import NavBar from "@/components/Nav/NavBar.vue"
export default {
  components: {
    NavBar
  }

};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/templates/common.scss";
</style>
