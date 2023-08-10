<template>
  <div class="career-filter">
    <h3>경력</h3>
    <Slider
      class="career-filter__slider"
      @change="onCareerChange"
      :value="career"
    />
    <p>경력: {{ career[0] }}년에서 ~ {{ career[1] }}년까지</p>
    <button @click="closeFilter">완료</button>
  </div>
</template>

<script>
import Slider from "@vueform/slider";
import { mapGetters } from "vuex";

export default {
  components: { Slider },
  data() {
    return {};
  },
  computed: {
    ...mapGetters({ career: "getSearchParamsCareer" }),
  },
  methods: {
    onCareerChange(value) {
      console.log("InputCareer.vue career 수정");
      this.$emit("careerChange", value);
    },
    closeFilter() {
      this.$emit("closeDropdown");
    },
  },
};
</script>

<style src="@vueform/slider/themes/default.css"></style>
<style lang="scss" scoped>
h3 {
  font-size: 20px;
  font-weight: bold;
}

.career-filter {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  height: 180px;
}

.career-filter__slider {
  margin-top: 35px;
}
</style>
