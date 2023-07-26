<template>
  <div>
    강사검색
    <br />
    <SearchBar> </SearchBar>
    <hr>
    <SearchList> </SearchList>
  </div>
</template>

<script>
import SearchBar from "../components/Search/SearchBar.vue";
import SearchList from "../components/Search/SearchList.vue";

export default {
  name: "SearchView",
  components: {
    SearchBar,
    SearchList,
  },
};
</script>

<style scoped></style>
