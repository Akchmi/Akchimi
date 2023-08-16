<template>
  <div>
    <div>
      <NavBar></NavBar>
    </div>
    <TheBanner :title="'강의실'" />

    <router-view></router-view>
  </div>
</template>

<script>
import NavBar from "@/components/common/NavBar.vue";
import TheBanner from "@/components/common/TheBanner";
import { onMounted } from "vue";

export default {
  components: {
    NavBar,
    TheBanner,
  },
  setup() {
    onMounted(() => {
      window.scrollTo(0, 0);
    });
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/lecture.scss";
</style>
