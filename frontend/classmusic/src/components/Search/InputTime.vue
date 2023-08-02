<template>
  <div>
    <h3>Select Days</h3>

    <div>
      <label v-for="(value, day) in days" :key="day">
        <input type="checkbox" v-model="days[day]" @change="onDaysChange()" />
        {{ day }}
      </label>
    </div>
    <br />
    <br />
    <Slider @change="onTimeChange" v-model="times" :max="24" />
    <br />
    <button @click="handleComplete">완료</button>
  </div>
</template>

<script>
import Slider from "@vueform/slider";

export default {
  components: { Slider },
  data: () => ({
    days: {
      월: false,
      화: false,
      수: false,
      목: false,
      금: false,
      토: false,
      일: false,
    },
    times: [0, 24],
  }),
  methods: {
    onTimeChange() {
      console.log("InputTime.vue time 수정");
      this.$emit("timeChange", this.times);
    },
    onDaysChange() {
      console.log("InputTime.vue days 수정");
      const bitMask = this.convertDaysToBitMask();
      this.$emit("dayChange", bitMask);
    },
    convertDaysToBitMask() {
      console.log("ConvertDaysToBitMask");
      let index = 0,
        bitMaskedDays = 0;

      for (const day in this.days) {
        if (this.days[day]) {
          bitMaskedDays ^= 1 << index;
        }
        index++;
      }

      console.log(bitMaskedDays.toString(2));
      return bitMaskedDays;
    },
  },
};
</script>

<style src="@vueform/slider/themes/default.css"></style>
