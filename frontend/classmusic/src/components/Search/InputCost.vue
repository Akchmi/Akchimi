<template>
  <div>
    <h3>Expense</h3>
    <br />
    <Slider :value="cost" @change="onCostChange" />
    <br />
    <p>한시간당 : {{ cost[0] }}만원 에서 ~ {{ cost[1] }} 만원 까지</p>
    <button @click="closeFilter">완료</button>
  </div>
</template>

<script>
import Slider from "@vueform/slider";
import { mapGetters } from "vuex";

export default {
  components: { Slider },
  data: () => ({}),
  computed: {
    ...mapGetters({
      cost: "getSearchParamsCost",
    }),
  },
  methods: {
    onCostChange(costs) {
      console.log("InputCost.vue cost 수정");
      this.$emit("costChange", costs);
    },
    closeFilter() {
      this.$emit("closeDropdown");
    },
  },
};
</script>

<style src="@vueform/slider/themes/default.css"></style>
