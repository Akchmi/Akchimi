<template>
  <div>
    <div>
      <NavBar></NavBar>
    </div>
    <br />
    <SearchBar ref="searchBar"> </SearchBar>
    <hr />
    <SearchList> </SearchList>
  </div>
</template>

<script>
import SearchBar from "../components/Search/SearchBar.vue";
import SearchList from "../components/Search/SearchList.vue";
import NavBar from "@/components/Nav/NavBar.vue"
import { useStore } from "vuex";
import { onMounted } from "vue";

export default {
  name: "SearchView",
  data() {
    return {};
  },
  components: {
    SearchBar,
    SearchList,
    NavBar,
  },
  setup() {
    const store = useStore();

    onMounted(() => {
      store.dispatch("searchTeacher");
    });
  },
  methods: {},
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/templates/common.scss";
</style>
