<template>
  <div class="teacher-profile-container">
    <div class="teacher-profile">
      <div class="top-section">
        <div class="img-container">
          <img
            :src="userProfileImage"
            alt="Teacher profile picture"
            class="teacher-image"
          />         
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
                <div
    v-for="(instrument, index) in selectedInstruments"
    :key="index"
  >
    {{ instrument }}
    <button @click="removeInstrument(index)">제거</button>
  </div>
            
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
        <button @click="submitForm">강사 수정하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import {  apiDetailTeacherInfo } from "@/api/profiles.js";
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
      startTime: "",
      endTime: "",
      description: "",
      career: 0,
      cost: 0,
      name: "",
      selectedDays: [],
      attachedFiles: [],
      userProfileImage:"",
      id: JSON.parse(localStorage.getItem("vuex")).common.id,
      userId : JSON.parse(localStorage.getItem("vuex")).common.userId,
      teacherId : JSON.parse(localStorage.getItem("vuex")).common.teacherId,
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
     handleFileUpload() {
      const selectedFiles = this.$refs.fileUploadInput.files;

    this.attachedFiles = [];

      for (let i = 0; i < selectedFiles.length; i++) {
        const fileReader = new FileReader();

        fileReader.onload = (e) => {
          this.attachedFiles.push(e.target.result);  
        };
        
        fileReader.readAsDataURL(selectedFiles[i]);
      }
    },    
    async submitImages() {
      let formData = new FormData();

      for (let i = 0; i < this.$refs.fileUploadInput.files.length; i++) {
        formData.append("image", this.$refs.fileUploadInput.files[i]);
      }

      try {
        await axios.post(`/teachers/${this.teacherId}/images`, formData);
      } catch (error) {
        console.log(error);
      }
    },

    removeInstrument(index) {
    this.selectedInstruments.splice(index, 1);
    },
    ...mapActions(['putTeacherProfileUpdate']),

    async submitForm() {
      await this.submitImages();
      const data = {       
        career: this.career,
        cost: this.cost,
        introduce: this.description,
        startTime: this.startTime,
        endTime: this.endTime,
        classDay: this.convertDaysToBitMask(),
        instruments: [...this.selectedInstruments], 
        teacherId : this.teacherId       
      };
      this.putTeacherProfileUpdate(data)            
        .then(response => {    
          const teacherId = JSON.parse(localStorage.getItem("vuex")).common.teacherId
          this.$store.commit('updateTeacherProfile', response);
          this.$router.push(`/profile/teacherprofile/${teacherId}`);         
          console.log(this.teacherId, response)
        });

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
  },
  async created() {
    const teacherId = JSON.parse(localStorage.getItem("vuex")).common.teacherId
    const res = await apiDetailTeacherInfo(teacherId);
    this.name = res.name;
    this.selectedInstruments = res.instruments;
    this.description = res.introduce;
    this.startTime = res.startTime;
    this.endTime = res.endTime;
    this.cost = res.cost;
    this.career = res.career;
    this.classDay = res.classDay;
    this.userProfileImage = res.userProfileImage;
    this.attachedFiles = res.images;

    const classDayBinary = parseInt(res.classDay, 2).toString(2).padStart(7, '0');
    Object.keys(this.days).forEach((day, index) => {
        this.days[day] = classDayBinary[index] === '1';
    });
  },
};
</script>

<style scoped>
@import "@/assets/scss/teacherprofileupdate.scss";
</style>
