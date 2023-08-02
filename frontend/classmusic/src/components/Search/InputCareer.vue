<template>
  <div>
    <h3>career</h3>
    <br />
    <Slider @change="onCareerChange" v-model="career" />
    <br />
    <p>경력: {{ career[0] }}년에서 ~ {{ career[1] }}년까지</p>
    <button>완료</button>
  </div>
</template>

<script>
import Slider from "@vueform/slider";

export default {
  components: { Slider },
  data: () => ({
    career: [10, 90],
  }),
  methods: {
    onCareerChange(careers) {
      console.log("InputCareer.vue career 수정");
      this.$emit("careerChange", careers);
    },
  },
};
</script>

<style src="@vueform/slider/themes/default.css"></style>
