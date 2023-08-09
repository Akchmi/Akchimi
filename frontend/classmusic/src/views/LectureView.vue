<template>
  <div>
    <div>
      <NavBar></NavBar>
    </div>
    <div class="main__container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import NavBar from "@/components/common/NavBar.vue";

export default {
  components: {
    NavBar,
  },
  data() {
    return {
      currentTab: "student",
      studentSubTab: "ongoing",
      teacherSubTab: "ongoing",
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/lecture.scss";
</style>
