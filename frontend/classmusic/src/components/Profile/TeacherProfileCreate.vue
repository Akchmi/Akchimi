<template>
  <div class="teacher-profile-container">
    <div class="teacher-profile">
      <div class="top-section">
        <div class="img-container">
          <img
            :src="image"
            alt="Teacher profile picture"
            class="teacher-image"
          />
          <button>이미지 수정</button>
        </div>
        <div class="info-container">
          <div class="name-container">
            <h3>강사 이름</h3>
            <p>1 :{{ userInfo.name }}</p>
          </div>
          <div class="teacher-profile-update-container">
            <div class="left-field">
              <div class="input-field">
                <label for="instrument">악기 : </label>
                <select
                  id="instrument"
                  v-model="selectedInstrument"
                  @change="saveToselectedInsruments"
                >
                  <option
                    v-for="(instrument, index) in instruments"
                    :value="instrument"
                    :key="index"
                  >
                    {{ instrument }}
                  </option>
                </select>
                <br />
                선택된 악기:
               <div v-for="(instrument, index) in selectedInstruments" :key="index">
                {{ instrument }}
                <button @click="removeSelectedInstrument(index)">제거</button>
              </div>
                <!-- <div
                  v-for="(instrument, index) in selectedInstruments"
                  :key="index"
                >
                  {{ instrument }}
                </div> -->
              </div>
              <div class="input-field">
                <label for="years">경력 : </label>
                <input
                  id="years"
                  v-model.number="career"
                  type="number"
                  min="1"
                />
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
                <div
                  class="days-container"
                  v-for="(checked, day) in days"
                  :key="day"
                >
                  <input type="checkbox" :id="day" v-model="days[day]" />
                  <label :for="day">{{ day }}</label>
                </div>
              </div>
              <div class="input-field">
                <label for="start">시작 시간 :</label>
                <input
                  id="start"
                  v-model.number="startTime"
                  type="number"
                  min="0"
                  max="23"
                />
              </div>
              <div class="input-field">
                <label for="end">종료 시간 :</label>
                <input
                  id="end"
                  v-model.number="endTime"
                  type="number"
                  min="0"
                  max="23"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="teacher-details">
        <h2>자기 소개</h2>
        <div class="description-box">
          <textarea
            class="description-input"
            v-model="description"
            placeholder="자기자신을 잘 소개할 수 있는 문구를 작성해주세요. 휴대전화 번호 공개를 권장하지 않습니다."
          ></textarea>
        </div>
      </div>
      <div class="attach-file">
          <h3>파일 첨부</h3>
          <div>
            <img
              v-for="(image, index) in attachedFiles"
              :src="image"
              :key="index"
              alt="Attached file"
              class="attach-image"
            />
          </div>
          <input
            type="file"
            multiple
            ref="fileUploadInput"
            @change="handleFileUpload"
            style="display: none"
          />
          <button @click="triggerFileUpload">첨부 파일 추가</button>
        </div>
      <div class="save-button">
        <!-- <button @click="submitForm">저장</button> -->
        <button @click="submitForm">강사 등록하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import { apiGetUserInfo } from "@/api/profiles.js";
import axios from "@/api/imageAxios.js";



export default {
  props: {
    image: { type: String, default: "https://via.placeholder.com/280" },
  },
  data() {
    return {
      userInfo: {},
      selectedInstrument: null,
      instruments: ["피아노", "기타", "드럼", "바이올린", "트럼펫"],
      selectedInstruments: [],
      days: {
        월: false,
        화: false,
        수: false,
        목: false,
        금: false,
        토: false,
        일: false,
      },
      startTime: "00",
      endTime: "00",
      description: "",
      career: 0,
      cost: 0,
      selectedDays: [],
      id: JSON.parse(localStorage.getItem("vuex")).common.id,
    };
  },
  computed: {
    selectedDaysString() {
      return Object.keys(this.days)
        .map((day) => (this.days[day] ? "1" : "0"))
        .join("");
    },
  },

  methods: {
    triggerFileUpload() {
      this.$refs.fileUploadInput.click();
    },
    async handleFileUpload() {
      const selectedFiles = this.$refs.fileUploadInput.files;

      let formData = new FormData();
      for (let i = 0; i < selectedFiles.length; i++) {
        formData.append("image", selectedFiles[i]);

        try {
          let response = await axios.post(
            `/teachers/${this.teacherId}/images`,
            formData
          );

          if (response.data && response.data.image) {
            this.attachedFiles.push(response.data.image);
            console.log(222);
          }
        } catch (error) {
          console.log(error);
        }
      }
    },
    async getUserInfo() {
      try {
        const data = await apiGetUserInfo(this.id);
        if (data) {
          this.userInfo = data;
        }
      } catch (error) {
        console.log(error);
      }
    },

    ...mapActions(["postTeacherProfileCreate", "updateUserType"]),

    async submitForm() {
      const data = {
        userId: this.userInfo.userId,
        career: this.career,
        cost: this.cost,
        introduce: this.description,
        startTime: this.startTime,
        endTime: this.endTime,
        classDay: this.convertDaysToBitMask(),
        instruments: [...this.selectedInstruments],        
      };

      const teacherId = await this.postTeacherProfileCreate(data);
      if (teacherId != null) {
        this.updateUserType(1);
        this.$router.push(`/profile/teacherprofile/${teacherId}`);
      } else {
        alert("강사 등록 중 문제 발생!");
      }
    },
    convertDaysToBitMask() {
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

    saveToselectedInsruments() {
      const selectedInstrument = this.selectedInstrument;
      for (let i = 0; i < this.selectedInstruments.length; i++) {
        if (this.selectedInstruments[i] == selectedInstrument) {
          return;
        }
      }
      this.selectedInstruments.push(selectedInstrument);
    },
    removeSelectedInstrument(index) {
      this.selectedInstruments.splice(index, 1);
    },
  },
  created() {
    this.getUserInfo();
  },
};
</script>

<style scoped>
@import "@/assets/scss/teacherprofileupdate.scss";
</style>