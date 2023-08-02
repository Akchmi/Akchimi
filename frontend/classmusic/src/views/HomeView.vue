<template>   
  <div>
    <div>
      <NavBar></NavBar>
    </div>
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
