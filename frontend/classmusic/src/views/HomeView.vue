<template>
  <div>
    <div>
      <NavBar></NavBar>
    </div>
    <router-view></router-view>
    <div class="quokka__div">
      <div class="quokka__indiv">
        <img src="@/assets/images/quokkaband.png" alt="" class="quokka" />
        <h1>"악기라는 취미에 미치다" - 쿼카in</h1>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from "@/components/Nav/NavBar.vue";

export default {
  components: {
    NavBar,
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/templates/common.scss";
</style>

<style>
.quokka__div {
  display: flex;
  justify-content: center;
  margin: 100px;
  text-align: center;
}

.quokka__indiv {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.quokka {
  width: 650px;
  height: 650px;
}
</style>
