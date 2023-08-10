<template>
  <div>
    <div>
      <NavBar></NavBar>
      <HomePage></HomePage>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
import NavBar from "@/components/common/NavBar.vue";
import HomePage from "@/components/Home/HomePage.vue";

export default {
  components: {
    NavBar,
    HomePage,
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/home.scss";
</style>

<style></style>
