<template>
  <div>
    <div>
      <NavBar></NavBar>
    </div>
    <TheBanner :title="'자유게시판'" />
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

<style lang="scss" scoped></style>
