<template>
  <div class="time-filter">
    <h3>요일</h3>

    <div>
      <label v-for="(value, day) in days" :key="day">
        <input
          type="checkbox"
          class="day-checkbox"
          :checked="value"
          @change="onDaysChange($event, day)"
        />
        {{ day }}
      </label>
    </div>

    <h3>시간</h3>

    <Slider
      class="time-filter__slider"
      @change="onTimeChange"
      :value="time"
      :max="24"
    />

    <button @click="closeFilter">완료</button>
  </div>
</template>

<script>
import Slider from "@vueform/slider";
import { mapGetters } from "vuex";

export default {
  components: { Slider },
  props: {
    days: Object,
  },
  computed: {
    ...mapGetters({
      time: "getSearchParamsTime",
    }),
  },
  data: () => ({}),
  methods: {
    onTimeChange(value) {
      this.$emit("timeChange", value);
    },
    onDaysChange(event, day) {
      this.$emit("dayChange", event.target.checked, day);
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

.time-filter {
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  height: 200px;
}

.time-filter__slider {
  margin-top: 35px;
}

.day-checkbox:checked {
  background-color: #edd9b7;
}
</style>
