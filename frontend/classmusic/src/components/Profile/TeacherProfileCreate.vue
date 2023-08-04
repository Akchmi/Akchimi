<template>
  <div class="teacher-profile-container">
    <div class="teacher-profile">
      <div class="top-section">
        <div class="img-container">
          <img :src="image" alt="Teacher profile picture" class="teacher-image" />
          <button>이미지 수정</button>
        </div>
        <div class="info-container">
          <div class="name-container">
            <h3>강사 이름</h3>
            <p>{{ name }}</p>
          </div>
          <div class="teacher-profile-update-container">
            <div class="left-field">
              <div class="input-field">
                <label for="instrument">악기 : </label>
                <select id="instrument" v-model="selectedInstrument">
                  <option v-for="(instrument, index) in instruments" :value="instrument" :key="index">{{ instrument }}</option>
                </select>
                <div v-for="(instrument, index) in selectedInstruments" :key="index">
                  {{ instrument }}
                </div>
              </div>
              <div class="input-field">
                <label for="years">경력 : </label>
                <input id="years" v-model.number="career" type="number" min="1" />
                년
              </div>
              <div class="input-field">
                <label for="cost">시간당 비용 : </label>
                <input id="cost" v-model.number="cost" type="number" min="0" />
                만원
              </div>
            </div>
            <div class="right-field">
              <div class="input-field">
                <label>요일:</label>
                <div class="days-container" v-for="(day, index) in days" :key="index">
                  <input type="checkbox" :id="day" v-model="selectedDays" :value="day">
                  <label :for="day">{{ day }}</label>
                </div>
              </div>
              <div class="input-field">
                <label for="start">시작 시간 :</label>
                <input id="start" v-model="startTime" type="time" />
              </div>
              <div class="input-field">
                <label for="end">종료 시간 :</label>
                <input id="end" v-model="endTime" type="time" />
              </div>
              <div class="input-field">
                <p>가능 시간 : {{ selectedDays.join(', ') }} {{ startTime }} ~ {{ endTime }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="teacher-details">
        <h2>자기 소개</h2>
        <div class="description-box">
          <textarea class="description-input" v-model="description" placeholder="자기자신을 잘 소개할 수 있는 문구를 작성해주세요.\n 휴대전화 번호 공개를 권장하지 않습니다."></textarea>
        </div>
      </div>
      <div class="attach-container">
        <div>
          <button>첨부 파일 추가</button>
        </div>
        첨부파일 추가 시 보여줄 공간
      </div>
      <div class="save-button">
        <button @click="submitForm">저장</button>
      </div>
    </div>
  </div>
</template>


<script>
import { mapGetters } from "vuex";
import { apiTeacherProfileCreate } from "@/api/profiles.js";

export default {
  props: {
    image: { type: String, default: "https://via.placeholder.com/280" },
  
  },
  data() {
    return {
      name: 1,
      selectedInstrument: null,
      instruments: ['피아노', '기타', '드럼', '바이올린', '트럼펫'],
      selectedInstruments: [],
      days: ['월', '화', '수', '목', '금', '토', '일'],
      selectedDays: [],
      startTime: '',
      endTime: '',
      description: "",
      career: 0,
      cost: 0,
    };
  },
  computed: {
    ...mapGetters(["getName"]),
  },
  methods: {
    submitForm() {
      const data = {
        userId: this.userId,
        career: this.career,
        cost: this.cost,
        introduce: this.description,
        startTime: this.convertTimeToMinutes(this.startTime),
        endTime: this.convertTimeToMinutes(this.endTime),
        classday: this.convertDaysToBitMask(),
        instruments: this.selectedInstruments,
      };

      apiTeacherProfileCreate(data)
        .then((response) => {
          this.$store.dispatch("saveTeacherId", response.teacherId);
        })
        .catch((error) => {
          console.error("등록 오류:", error);
        });
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
    }
  }
</script>

<style scoped>
@import "@/assets/scss/teacherprofile.scss";
</style>
