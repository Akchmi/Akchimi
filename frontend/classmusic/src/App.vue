<template>
  <div>
    <!-- 구성 화면 -->
    <div class="container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
// import { computed } from "vue";
// import { useStore } from "vuex";

export default {
  setup() {
    // const store = useStore();
  },
};
</script>
"

<style lang="scss">
@import "@/assets/scss/templates/common.scss";
@import "@/assets/scss/reset.scss";

@font-face {
  font-family: "mainfont";
  src: url("assets/font/BMEuljiro10yearslater.ttf") format("truetype");
  font-weight: 100;
}

.container {
  font-family: "mainfont";
}
</style>
