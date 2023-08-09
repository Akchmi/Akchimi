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
import NavBar from "@/components/common/NavBar.vue";
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
      store.dispatch("commitInstrument", "악기종류");
      store.dispatch("commitGender", "");
      store.dispatch("commitKeyword", "");
      // 모든 요일 설정
      // store.dispatch("commitClassDay", (1 << 8) - 1);
      store.dispatch("commitClassDay", "1111111");

      store.dispatch("searchTeacher");
    });
  },
  methods: {},
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/templates/common.scss";
</style>
