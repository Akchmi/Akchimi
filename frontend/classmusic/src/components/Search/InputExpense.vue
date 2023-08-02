<template>
  <div>
    <h3>Expense</h3>
    <br />
    <Slider v-model="cost" @change="onCostChange" />
    <br />
    <p>한시간당 : {{ cost[0] }}만원 에서 ~ {{ cost[1] }} 만원 까지</p>
    <button>완료</button>
  </div>
</template>

<script>
import Slider from "@vueform/slider";

export default {
  components: { Slider },
  data: () => ({
    cost: [10, 90],
  }),
  methods: {
    onCostChange(costs) {
      console.log("InputExpense.vue cost 수정");
      this.$emit("costChange", costs);
    },
  },
};
</script>

<style src="@vueform/slider/themes/default.css"></style>
