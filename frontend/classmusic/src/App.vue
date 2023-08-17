<template>
  <div>
    <!-- 구성 화면 -->
    <div class="container">
      <router-view style="min-height: 600px"></router-view>
      <TheFooter></TheFooter>
    </div>
  </div>
</template>

<script>
import TheFooter from "@/components/common/TheFooter";
export default {
  name: "AppVue",
  components: {
    TheFooter,
  },
};
</script>
"

<style lang="scss">
@import "@/assets/scss/reset.scss";
@import "@/assets/scss/templates/common.scss";

@font-face {
  font-family: "mainfont";
  src: url("assets/font/Maplestory.ttf") format("truetype");
}

.container {
  font-family: "mainfont";
}
</style>
