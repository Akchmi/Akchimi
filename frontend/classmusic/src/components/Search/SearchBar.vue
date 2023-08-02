<template>
  <div class="search-bar">
    <div class="dropdown">
      <button id="instrumentButton" @click="toggleInstrumentDropdown">
        악기
      </button>
      <div
        id="instrumentDropdown"
        class="dropdown-content"
        v-if="showInstrumentDropdown"
      >
        <button @click="selectInstrument('피아노')">피아노</button>
        <button @click="selectInstrument('기타')">기타</button>
        <button @click="selectInstrument('드럼')">드럼</button>
      </div>
    </div>

    <div class="dropdown">
      <button id="careerButton" @click="toggleInputCareer">경력</button>
      <div id="careerDropdown" class="dropdown-content" v-if="showInputCareer">
        <InputCareer @careerChange="setCareer" />
      </div>
    </div>

    <div class="dropdown">
      <button id="expenseButton" @click="toggleInputExpense">비용</button>
      <div
        id="expenseDropdown"
        class="dropdown-content"
        v-if="showInputExpense"
      >
        <InputExpense @costChange="setCost" />
      </div>
    </div>

    <div class="dropdown">
      <button id="timeButton" @click="toggleInputTime">시간</button>
      <div id="timeDropdown" class="dropdown-content" v-if="showInputTime">
        <InputTime @timeChange="setTime" @daysChange="setClassDay" />
      </div>
    </div>
  </div>
</template>

<script>
import InputCareer from "./InputCareer.vue";
import InputExpense from "./InputExpense.vue";
import InputTime from "./InputTime.vue";

export default {
  components: {
    InputCareer,
    InputExpense,
    InputTime,
  },
  data() {
    return {
      showInstrumentDropdown: false,
      showInputCareer: false,
      showInputExpense: false,
      showInputTime: false,

      searchParams: {
        startCareer: 10,
        endCareer: 90,
        startCost: 0,
        endCost: 10,
        startTime: 0,
        endTime: 23,
        classDay: 0,
      },
      selectedInstrument: null,
      selectedCareer: null,
      selectedExpense: null,
      selectedTime: null,
    };
  },
  methods: {
    toggleInstrumentDropdown() {
      this.showInstrumentDropdown = !this.showInstrumentDropdown;
      this.showInputCareer = false;
      this.showInputExpense = false;
      this.showInputTime = false;
    },
    toggleInputCareer() {
      this.showInputCareer = !this.showInputCareer;
      this.showInstrumentDropdown = false;
      this.showInputExpense = false;
      this.showInputTime = false;
    },
    toggleInputExpense() {
      this.showInputExpense = !this.showInputExpense;
      this.showInstrumentDropdown = false;
      this.showInputCareer = false;
      this.showInputTime = false;
    },
    toggleInputTime() {
      this.showInputTime = !this.showInputTime;
      this.showInstrumentDropdown = false;
      this.showInputCareer = false;
      this.showInputExpense = false;
    },
    selectInstrument(instrument) {
      this.selectedInstrument = instrument;
      this.showInstrumentDropdown = false;
    },

    setCareer(career) {
      console.log("경력 필터 수정", career);
      this.searchParams.startCareer = career[0];
      this.searchParams.endCareer = career[1];
    },

    setCost(cost) {
      console.log("비용 필터 수정", cost);
      this.searchParams.startCost = cost[0];
      this.searchParams.endCost = cost[1];
    },

    setTime(time) {
      console.log("시간 필터 수정", time);
      this.searchParams.startTime = time[0];
      this.searchParams.endTIme = time[1];
    },

    setDays(bitMaskedDays) {
      console.log("요일 필터 수정", bitMaskedDays);
      this.searchParams.classDay = bitMaskedDays;
    },
  },
};
</script>

<style scoped>
@import "@/assets/scss/search.scss";
</style>
