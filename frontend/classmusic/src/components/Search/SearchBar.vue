<template>
  <div class="search-bar">
    <!-- 검색바 -->
    <div>
      <!-- 키워드 검색 -->
      <!-- 정렬 -->
      <select v-model="searchParams.orderBy" @change="onChangeOrderBy">
        <option
          v-for="category in searchCategory"
          :key="category"
          :value="category"
        >
          {{ category }}
        </option>
      </select>
      <!-- 검색 버튼 -->
      <input
        placeholder="검색어를 입력하세요."
        type="text"
        class="search-bar__keyword"
        v-model="searchParams.keyword"
        @change="setKeyword"
        @keyup.enter="searchTeacher"
      />
      <button @click="searchTeacher">검색</button>
    </div>
    <!-- 필터들 -->
    <div class="filters">
      <!-- 성별 필터 -->
      <select v-model="searchParams.gender" @change="setGender">
        <option v-for="(value, gender) in genders" :key="value" :value="value">
          {{ gender }}
        </option>
      </select>

      <!-- 악기 필터 -->
      <select v-model="searchParams.instrument" @change="setInstrument">
        <option
          v-for="instrument in instruments"
          :key="instrument"
          :value="instrument"
        >
          {{ instrument }}
        </option>
      </select>

      <!-- 경력 필터 -->
      <div class="dropdown">
        <button id="careerButton" @click="toggleInputDropdown('career')">
          경력: {{ searchParams.career[0] }}년 ~ {{ searchParams.career[1] }}년
        </button>
        <div
          id="careerDropdown"
          class="dropdown-content"
          v-if="isDisplaySearchInputs.career"
        >
          <InputCareer
            @careerChange="setCareer"
            @closeDropdown="closeDropdown('career')"
          />
        </div>
      </div>

      <!-- 비용 필터 -->
      <div class="dropdown">
        <button id="expenseButton" @click="toggleInputDropdown('cost')">
          비용: {{ searchParams.cost[0] }}만원 ~ {{ searchParams.cost[1] }}만원
        </button>
        <div
          id="expenseDropdown"
          class="dropdown-content"
          v-if="isDisplaySearchInputs.cost"
        >
          <InputCost
            @costChange="setCost"
            @closeDropdown="closeDropdown('cost')"
          />
        </div>
      </div>

      <!-- 시간 필터 -->
      <div class="dropdown">
        <button id="timeButton" @click="toggleInputDropdown('time')">
          시간 :{{ searchParams.time[0] }}시 ~ {{ searchParams.time[1] }}시
          <!-- <br> -->
          <!-- 요일: <span v-for="day in filteredDays" :key="day">{{ day }} </span> -->
        </button>
        <div
          id="timeDropdown"
          class="dropdown-content"
          v-if="isDisplaySearchInputs.time"
        >
          <InputTime
            :days="days"
            @timeChange="setTime"
            @dayChange="setClassDay"
            @closeDropdown="closeDropdown('time')"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import InputCareer from "./InputCareer.vue";
import InputCost from "./InputCost.vue";
import InputTime from "./InputTime.vue";
import { mapActions } from "vuex";
// import { useStore } from "vuex";
import { onMounted } from "vue";
export default {
  components: {
    InputCareer,
    InputCost,
    InputTime,
  },
  computed: {
    filteredDays() {
      return Object.entries(this.searchParams.days)
        .filter(([, value]) => value)
        .map(([key]) => key);
    },
  },

  data() {
    return {
      isDisplaySearchInputs: {
        instrument: false,
        career: false,
        cost: false,
        time: false,
      },

      genders: { 성별: 0, 남: 1, 여: 2 },
      searchCategory: ["최신순", "별점순", "매칭순"],
      instruments: ["악기종류", "피아노", "기타", "드럼", "바이올린", "색소폰"],
      days: {
        월: true,
        화: true,
        수: true,
        목: true,
        금: true,
        토: true,
        일: true,
      },

      searchParams: {
        instrument: "악기종류",
        keyword: "",
        orderBy: "최신순",
        gender: "0",
        career: [0, 100],
        cost: [0, 100],
        time: [0, 24],
      },
    };
  },
  methods: {
    ...mapActions([
      "searchTeacher",
      "commitCareer",
      "commitCost",
      "commitTime",
      "commitClassDay",
      "commitInstrument",
      "commitGender",
      "commitOrderBy",
      "commitKeyword",
    ]),

    closeDropdown(targetDropDown) {
      // console.log(
      //   targetDropDown,
      //   " 드롭다운 닫기 ",
      //   this.isDisplaySearchInputs[targetDropDown]
      // );
      this.isDisplaySearchInputs[targetDropDown] = false;
    },

    onChangeOrderBy() {
      this.commitOrderBy(this.searchParams.orderBy);
      this.searchTeacher();
    },

    toggleInputDropdown(targetSearchInput) {
      // console.log("toggle clicked", targetSearchInput);

      for (const searchInput in this.isDisplaySearchInputs) {
        if (searchInput != targetSearchInput) {
          this.isDisplaySearchInputs[searchInput] = false;
        }
      }
      this.isDisplaySearchInputs[targetSearchInput] =
        !this.isDisplaySearchInputs[targetSearchInput];
    },

    setCareer(career) {
      // console.log("경력 필터 수정", career);
      this.searchParams.career = career;
      this.commitCareer(career);
    },

    setCost(cost) {
      // console.log("비용 필터 수정", cost);
      this.searchParams.cost = cost;
      this.commitCost(cost);
    },

    setTime(time) {
      // console.log("시간 필터 수정", time);
      this.searchParams.time = time;
      this.commitTime(time);
    },

    setClassDay(value, day) {
      // console.log("요일 필터 수정", value, day);

      this.days[day] = value;
      const daysBitMask = this.convertDaysToBitMask();

      this.commitClassDay(daysBitMask);
    },

    setInstrument() {
      // console.log("악기 종류 수정", this.searchParams.instrument);

      this.commitInstrument(this.searchParams.instrument);
    },

    setGender() {
      // console.log("성별 수정", this.searchParams.gender);

      this.commitGender(
        this.searchParams.gender == 0 ? "" : this.searchParams.gender
      );
    },

    setOrderBy() {
      // console.log("정렬 수정", this.searchParams.orderBy);

      this.commitOrderBy(this.searchParams.orderBy);
    },

    setKeyword() {
      // console.log("검색 키워드 수정", this.searchParams.keyword);

      this.commitKeyword(this.searchParams.keyword);
    },

    convertDaysToBitMask() {
      // console.log("ConvertDaysToBitMask");
      let index = 0,
        bitMaskedDays = 0;

      for (const day in this.days) {
        if (this.days[day]) {
          bitMaskedDays ^= 1 << index;
        }
        index++;
      }

      return bitMaskedDays.toString(2);
    },
  },
  setup() {
    // const store = useStore();

    onMounted(() => {
      // store.dispatch("commitInstrument", "악기종류");
      // store.dispatch("commitGender", "");
      // store.dispatch("commitKeyword", "");
      // store.dispatch("commitClassDay", "1111111");
      // store.dispatch("searchTeacher");
      // store.dispatch("commitCost", [0, 100]);
      // store.dispatch("commitTime", [0, 100]);
      // store.dispatch("commitCareer", [0, 100]);
      // 모든 요일 설정
      // store.dispatch("commitClassDay", (1 << 8) - 1);
    });
  },
};
</script>

<style scoped>
@import "@/assets/scss/search.scss";
</style>
