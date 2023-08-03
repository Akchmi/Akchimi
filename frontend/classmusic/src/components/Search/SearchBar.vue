<template>
  <div class="search-bar">
    <!-- 검색바 -->
    <!-- 성별 필터 -->
    <select v-model="searchParams.gender">
      <option v-for="(value, gender) in genders" :key="value" :value="value">
        {{ gender }}
      </option>
    </select>

    <!-- 악기 필터 -->
    <select v-model="searchParams.instrument">
      <option
        v-for="instrument in instruments"
        :key="instrument"
        :value="instrument"
      >
        {{ instrument }}
      </option>
    </select>

    <!-- <div class="dropdown">
      <button id="instrumentButton" @click="toggleInputDropdown('instrument')">
        악기
      </button>
      <div
        id="instrumentDropdown"
        class="dropdown-content"
        v-if="isDisplaySearchInputs.instrument"
      >
      // 시간 남으면 악기 이미지 radio 버튼으로 악기 종류 선택
      
      </div>
    </div> -->

    <!-- 경력 필터 -->
    <div class="dropdown">
      <button id="careerButton" @click="toggleInputDropdown('career')">
        경력
      </button>
      <div
        id="careerDropdown"
        class="dropdown-content"
        v-if="isDisplaySearchInputs.career"
      >
        <InputCareer
          :career="[searchParams.startCareer, searchParams.endCareer]"
          @careerChange="setCareer"
        />
      </div>
    </div>

    <!-- 비용 필터 -->
    <div class="dropdown">
      <button id="expenseButton" @click="toggleInputDropdown('cost')">
        비용
      </button>
      <div
        id="expenseDropdown"
        class="dropdown-content"
        v-if="isDisplaySearchInputs.cost"
      >
        <InputCost
          :cost="[searchParams.startCost, searchParams.endCost]"
          @costChange="setCost"
        />
      </div>
    </div>

    <!-- 시간 필터 -->
    <div class="dropdown">
      <button id="timeButton" @click="toggleInputDropdown('time')">시간</button>
      <div
        id="timeDropdown"
        class="dropdown-content"
        v-if="isDisplaySearchInputs.time"
      >
        <InputTime
          :days="days"
          :time="[searchParams.startTime, searchParams.endTime]"
          @timeChange="setTime"
          @dayChange="setClassDay"
        />
      </div>
    </div>

    <!-- 키워드 검색 -->
    <select v-model="searchParams.orderBy" @change="searchTeachers">
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
      v-model="searchParams.keyword"
    />
    <button @click="searchTeachers">검색</button>
  </div>
</template>

<script>
import InputCareer from "./InputCareer.vue";
import InputCost from "./InputCost.vue";
import InputTime from "./InputTime.vue";
import { apiSearchTeachers } from "@/api/search";

export default {
  components: {
    InputCareer,
    InputCost,
    InputTime,
  },
  data() {
    return {
      showInstrumentDropdown: false,
      showInputCareer: false,
      showInputExpense: false,
      showInputTime: false,

      isDisplaySearchInputs: {
        instrument: false,
        career: false,
        cost: false,
        time: false,
      },

      genders: { 성별: 0, 남: 1, 여: 2 },
      searchCategory: ["최신순", "별점순", "매칭순"],
      instruments: [
        "악기종류",
        "피아노",
        "기타",
        "드럼",
        "트럼펫",
        "콘트라베이스",
        "파이프오르간",
      ],
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
        startCareer: 10,
        endCareer: 90,
        startCost: 0,
        endCost: 10,
        startTime: 0,
        endTime: 23,
        classDay: 0,
        instrument: "악기종류",
        keyword: "",
        orderBy: "최신순",
        page: 1,
        gender: "0",
      },
    };
  },
  methods: {
    toggleInputDropdown(targetSearchInput) {
      console.log("toggle clicked", targetSearchInput);
      for (const searchInput in this.isDisplaySearchInputs) {
        if (searchInput != targetSearchInput) {
          this.isDisplaySearchInputs[searchInput] = false;
        }
      }
      this.isDisplaySearchInputs[targetSearchInput] =
        !this.isDisplaySearchInputs[targetSearchInput];
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
      this.searchParams.endTime = time[1];
    },

    setClassDay(value, day) {
      console.log("요일 필터 수정", value, day);
      this.days[day] = value;
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

    async searchTeachers() {
      this.searchParams.classDay = this.convertDaysToBitMask();
      const teachers = await apiSearchTeachers({
        ...this.searchParams,
        startCost: this.searchParams.startCost * 10000,
        endCost: this.searchParams.endCost * 10000,
        gender: this.searchParams.gender == 0 ? "" : this.searchParams.gender,
      });

      this.$emit("searchTeachers", teachers);
      console.log(teachers);
    },
  },
};
</script>

<style scoped>
@import "@/assets/scss/search.scss";

.column {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: left;
}
</style>
