  <template>
    <div class="search-bar">
      <div class="dropdown">
        <button @click="toggleInstrumentDropdown">
          악기
        </button>
        <div class="dropdown-content" v-if="showInstrumentDropdown">
          <button @click="selectInstrument('피아노')">피아노</button>
          <button @click="selectInstrument('기타')">기타</button>
          <button @click="selectInstrument('드럼')">드럼</button>
        </div>
      </div>

      <div class="dropdown">
        <button @click="toggleInputCareer">
          경력
        </button>
        <InputCareer v-if="showInputCareer" @onComplete="handleCompleteCareer" />
      </div>

      <div class="dropdown">
        <button @click="toggleInputExpense">
          비용
        </button>
        <InputExpense v-if="showInputExpense" @onComplete="handleCompleteExpense" />
      </div>

      <div class="dropdown">
        <button @click="toggleInputTime">
          시간
        </button>
        <InputTime v-if="showInputTime" @onComplete="handleCompleteTime" />
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
        console.log(`Selected instrument: ${this.selectedInstrument}`);
      },
      handleCompleteCareer(value) {
        console.log(`Selected career: ${value}`);
        this.selectedCareer = value;
        this.showInputCareer = false;
      },
      handleCompleteExpense(value) {
        console.log(`Selected expense: ${value}`);
        this.selectedExpense = value;
        this.showInputExpense = false;
      },
      handleCompleteTime(value) {
        console.log(`Selected time: ${value}`);
        this.selectedTime = value;
        this.showInputTime = false;
      }
    }
  };
  </script>

  <style scoped>
 
@import "@/assets/scss/search.scss"
  </style>
