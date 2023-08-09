<template>
  <div class="search-bar">
    <!-- 검색바 -->
    <div>
      <!-- 키워드 검색 -->
      <!-- 검색 버튼 -->
      <input
        placeholder="검색어를 입력하세요."
        type="text"
        v-model="searchParams.keyword"
        @change="setKeyword"
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
          <InputCareer @careerChange="setCareer" @closeDropdown="closeCareerDropdown" />
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
          <InputCost @costChange="setCost" />
        </div>
      </div>

      <!-- 시간 필터 -->
      <div class="dropdown">
        <button id="timeButton" @click="toggleInputDropdown('time')">
          시간
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
          />
        </div>
      </div>

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
    </div>
  </div>
</template>

<script>
import InputCareer from "./InputCareer.vue";
import InputCost from "./InputCost.vue";
import InputTime from "./InputTime.vue";
import { mapActions } from "vuex";

export default {
  components: {
    InputCareer,
    InputCost,
    InputTime,
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
        instrument: "악기종류",
        keyword: "",
        orderBy: "최신순",
        gender: "0",
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

    closeCareerDropdown() {
      this.isDisplaySearchInputs.career = false;
      console.log("닫경", this.isDisplaySearchInputs.career)

    },


    onChangeOrderBy() {
      this.commitOrderBy(this.searchParams.orderBy);
      this.searchTeacher();
    },

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
      this.commitCareer(career);
    },

    setCost(cost) {
      console.log("비용 필터 수정", cost);
      this.commitCost(cost);
    },

    setTime(time) {
      console.log("시간 필터 수정", time);
      this.commitTime(time);
    },

    setClassDay(value, day) {
      console.log("요일 필터 수정", value, day);

      this.days[day] = value;
      const daysBitMask = this.convertDaysToBitMask();

      this.commitClassDay(daysBitMask);
    },

    setInstrument() {
      console.log("악기 종류 수정", this.searchParams.instrument);

      this.commitInstrument(this.searchParams.instrument);
    },

    setGender() {
      console.log("성별 수정", this.searchParams.gender);

      this.commitGender(
        this.searchParams.gender == 0 ? "" : this.searchParams.gender
      );
    },

    setOrderBy() {
      console.log("정렬 수정", this.searchParams.orderBy);

      this.commitOrderBy(this.searchParams.orderBy);
    },

    setKeyword() {
      console.log("검색 키워드 수정", this.searchParams.keyword);

      this.commitKeyword(this.searchParams.keyword);
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
      return bitMaskedDays.toString(2);
    },
  },
};
</script>

<style scoped>
@import "@/assets/scss/search.scss";
</style>
