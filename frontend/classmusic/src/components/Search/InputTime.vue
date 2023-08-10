<template>
  <div>
    <h3>Select Days</h3>

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
    <br />
    <h3>Select Time</h3>
    <br />
    <Slider @change="onTimeChange" :value="time" :max="24" />
    <br />
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
      console.log("InputTime.vue time 수정");
      this.$emit("timeChange", value);
    },
    onDaysChange(event, day) {
      console.log("InputTime.vue days 수정");
      this.$emit("dayChange", event.target.checked, day);
    },
    closeFilter() {
      this.$emit("closeDropdown");
    },
  },
};
</script>

<style src="@vueform/slider/themes/default.css">
.day-checkbox:checked {
  color: #edd9b7;
}
</style>
