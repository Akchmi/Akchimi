<template>
  <div class="cost-filter">
    <h3>비용</h3>
    <Slider class="cost-filter__slider" :value="cost" @change="onCostChange" />
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
<style lang="scss" scoped>
h3 {
  font-size: 20px;
  font-weight: bold;
}

.cost-filter {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  height: 180px;
}

.cost-filter__slider {
  margin-top: 35px;
}
</style>
