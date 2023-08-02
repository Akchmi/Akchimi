<template>
  <div>
    <div>
      <NavBar></NavBar>
    </div>
    <br />
    <SearchBar> </SearchBar>
    <hr />
    <SearchList> </SearchList>
  </div>
</template>

<script>
import SearchBar from "../components/Search/SearchBar.vue";
import SearchList from "../components/Search/SearchList.vue";
import NavBar from "@/components/Nav/NavBar.vue"

export default {
  name: "SearchView",
  components: {
    SearchBar,
    SearchList,
    NavBar,
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/templates/common.scss";
</style>
