<template>
  <div class="search-bar">
    <div class="dropdown">
      <button id="instrumentButton" @click="toggleInstrumentDropdown">
        악기
      </button>
      <div id="instrumentDropdown" class="dropdown-content" v-if="showInstrumentDropdown">
        <button @click="selectInstrument('피아노')">피아노</button>
        <button @click="selectInstrument('기타')">기타</button>
        <button @click="selectInstrument('드럼')">드럼</button>
      </div>
    </div>

    <div class="dropdown">
      <button id="careerButton" @click="toggleInputCareer">
        경력
      </button>
      <div id="careerDropdown" class="dropdown-content" v-if="showInputCareer">
        <InputCareer @onComplete="handleCompleteCareer" />
      </div>
    </div>

    <div class="dropdown">
      <button id="expenseButton" @click="toggleInputExpense">
        비용
      </button>
      <div id="expenseDropdown" class="dropdown-content" v-if="showInputExpense">
        <InputExpense @onComplete="handleCompleteExpense" />
      </div>
    </div>

    <div class="dropdown">
      <button id="timeButton" @click="toggleInputTime">
        시간
      </button>
      <div id="timeDropdown" class="dropdown-content" v-if="showInputTime">
        <InputTime @onComplete="handleCompleteTime" />
      </div>
    </div>
  </div>
</template>

<script>
import InputCareer from './InputCareer.vue'
import InputExpense from './InputExpense.vue'
import InputTime from './InputTime.vue'

export default {
  components: {
    InputCareer,
    InputExpense,
    InputTime,
  },
  data() {
    return {
      showInstrumentDropdown: false,
      selectedInstrument: null,
      showInputCareer: false,
      selectedCareer: null,
      showInputExpense: false,
      selectedExpense: null,
      showInputTime: false,
      selectedTime: null
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
    handleCompleteCareer(value) {
      this.selectedCareer = value;
      this.showInputCareer = false;
    },
    handleCompleteExpense(value) {
      this.selectedExpense = value;
      this.showInputExpense = false;
    },
    handleCompleteTime(value) {
      this.selectedTime = value;
      this.showInputTime = false;
    }
  }
};
</script>

<style scoped>
@import "@/assets/scss/search.scss";
</style>
