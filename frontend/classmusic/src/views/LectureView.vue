<template>
  <div>
    <div>
      <NavBar></NavBar>
    </div>
    <TheBanner :title="'강의실'" />
    <div class="main__container">
      <router-view></router-view>
    </div>
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
