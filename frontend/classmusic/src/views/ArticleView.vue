<template>
  <div>
    <div>
      <NavBar></NavBar>
    </div>
    <div class="theBanner-container">
      <TheBanner :title="'자유게시판'" />
    </div>
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
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/templates/common.scss";

.theBanner-container {
  width: 100%;
  height: 150px;
}
</style>
