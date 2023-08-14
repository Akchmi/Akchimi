<template>
  <div>
    <div>
      <NavBar></NavBar>
    </div>
    <TheBanner :title="'강사검색'" />
    <SearchBar ref="searchBar"> </SearchBar>
    <hr />
    <SearchList> </SearchList>
  </div>
</template>

<script>
import SearchBar from "../components/Search/SearchBar.vue";
import SearchList from "../components/Search/SearchList.vue";
import NavBar from "@/components/common/NavBar.vue";
import TheBanner from "@/components/common/TheBanner";

export default {
  name: "SearchView",
  data() {
    return {};
  },
  components: {
    SearchBar,
    SearchList,
    NavBar,
    TheBanner,
  },

  methods: {},
};
</script>

<style lang="scss" scoped></style>
