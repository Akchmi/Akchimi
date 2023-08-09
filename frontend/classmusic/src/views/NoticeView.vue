<template>
  <div>
    <div>
      <NavBar></NavBar>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
import NavBar from "@/components/common/NavBar.vue";

export default {
  components: {
    NavBar,
  },
};
</script>

<style lang="scss" scoped></style>
