<template>
  <div class="teacher-profile-container">
    <div class="teacher-profile">
      <div class="top-section">
        <div class="img-container">
          <img
            :src="userInfo.userProfileImage"
            alt="Teacher profile picture"
            class="teacher-image"
          />
        </div>
        <div class="info-container">
          <div class="name-container">
            <h2>강사 이름 : {{ userInfo.name }}</h2>
          </div>
          <div
            class="teacher-profile-form__instruments teacher-profile-form__input"
          >
            <label>악기 : </label>
            <button
              v-for="(checked, instrument) in instruments"
              :key="instrument"
              :class="checked ? '' : 'unselected-instrument'"
              @click="toggleInstrument(instrument)"
            >
              {{ instrument }}
            </button>
          </div>
          <div class="teacher-profile-form__row">
            <div class="teacher-profile-form__input">
              <label for="years">경력 : </label>
              <input
                id="years"
                v-model.number="career"
                type="number"
                :max="maxCareer"
                min="0"
                @input="filterInput"
              />
              년
            </div>
            <div class="teacher-profile-form__input">
              <label for="cost">시간당 비용 : </label>
              <input
                id="cost"
                v-model.number="cost"
                type="number"
                :max="maxCost"
                min="0"
                @input="filterInput"
              />
              만원
            </div>
          </div>
          <div class="teacher-profile-form__input">
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
          <div class="teacher-profile-form__row">
            <div class="teacher-profile-form__input">
              <label for="start">시작 시간 :</label>
              <select v-model.number="startTime">
                <option
                  v-for="starttime in [
                    0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
                    17, 18, 19, 20, 21, 22, 23,
                  ]"
                  :key="starttime.id"
                >
                  {{ starttime }}
                </option>
              </select>
              <!-- <input
                id="start"
                v-model.number="startTime"
                type="number"
                :max="maxStartTime"
                min="0"
                @input="filterInput"
              /> -->
            </div>
            <div class="teacher-profile-form__input">
              <label for="end">종료 시간 :</label>
              <select v-model.number="endTime">
                <option
                  v-for="endtime in [
                    1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
                    18, 19, 20, 21, 22, 23, 24,
                  ]"
                  :key="endtime.id"
                >
                  {{ endtime }}
                </option>
              </select>
              <!-- <input
                id="end"
                v-model.number="endTime"
                type="number"
                :max="maxEndTime"
                min="1"
                @input="filterInput"
              /> -->
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
        <div class="file__header">
          <h2>파일 첨부</h2>
          <button @click="triggerFileUpload">첨부 파일 추가</button>
        </div>
        <div class="file__images">
          <div
            v-for="(image, index) in attachedFiles"
            :key="index"
            class="image-container"
          >
            <img
              :src="image.preview"
              alt="Attached file"
              class="attach-image"
            />
            <button class="image-remove" @click="removeAttachedFile(index)">
              삭제
            </button>
          </div>
        </div>
        <input
          type="file"
          multiple
          ref="fileUploadInput"
          @change="handleFileUpload"
          style="display: none"
        />
      </div>
      <div class="profile__submit-button">
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
    // image: { type: String, default: "https://via.placeholder.com/280" },
  },
  data() {
    return {
      formValid: true,
      maxCareer: 100,
      maxCost: 100,
      maxStartTime: 23,
      maxEndTime: 24,
      userInfo: {},
      instruments: {
        피아노: false,
        기타: false,
        드럼: false,
        바이올린: false,
        색소폰: false,
      },
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
      startTime: 0,
      endTime: 24,
      description: "",
      career: 0,
      cost: 0,
      selectedDays: [],
      userProfileImage: "",
      attachedFiles: [],
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

  watch: {
    career(value) {
      if (value > this.maxCareer) {
        this.career = this.maxCareer;
      }
    },
    cost(value) {
      if (value > this.maxCost) {
        this.cost = this.maxCost;
      }
    },
    startTime(value) {
      if (value > this.maxStartTime) {
        this.startTime = this.maxStartTime;
      } else if (value >= this.endTime) {
        this.startTime = this.endTime - 1;
      }
    },
    endTime(value) {
      if (value > this.maxEndTime) {
        this.endTime = this.maxEndTime;
      } else if (value <= this.startTime) {
        this.endTime = this.startTime + 1;
      }
    },
  },

  methods: {
    checkFormValidity() {
      let missingFields = [];

      if (!this.career) {
        missingFields.push("경력");
      }
      if (!this.cost) {
        missingFields.push("시간당 비용");
      }
      if (!this.description.trim()) {
        missingFields.push("자기 소개");
      }
      if (this.startTime >= this.endTime) {
        missingFields.push("시간");
      }
      if (!this.selectedDaysString.includes("1")) {
        missingFields.push("요일");
      }
      if (this.selectedInstruments.length === 0) {
        missingFields.push("악기 선택");
      }
      if (missingFields.length) {
        this.formValid = false;
        alert("모든 정보를 입력해주세요.");
        // console.log("누락된 정보:", missingFields.join(", "));
        // console.log('아긱', this.selectedInstruments)
        return false;
      }

      this.formValid = true;
      return true;
    },

    filterInput(event) {
      const validNumber = /^[0-9]*$/;
      if (!validNumber.test(event.target.value)) {
        event.target.value = event.target.value.replace(/[^0-9]/g, "");
      }
      event.target.value = String(Number(event.target.value));
    },
    toggleInstrument(instrument) {
      this.instruments[instrument] = !this.instruments[instrument];
      this.saveToselectedInsruments();
    },

    removeAttachedFile(index) {
      this.attachedFiles.splice(index, 1);
    },
    triggerFileUpload() {
      this.$refs.fileUploadInput.click();
    },
    handleFileUpload() {
      const allowedExtensions = ["jpg", "jpeg", "png", "gif", "webp", "jfif"];
      const maxFileSize = 30 * 1024 * 1024; // 30MB in bytes
      const selectedFiles = this.$refs.fileUploadInput.files;

      for (let i = 0; i < selectedFiles.length; i++) {
        const file = selectedFiles[i];
        const fileExtension = file.name.split(".").pop().toLowerCase();

        if (!allowedExtensions.includes(fileExtension)) {
          alert(
            `${file.name}는 파일 형식이 잘못되었습니다."jfif", "jpg", "jpeg", "png", "gif", "webp" 형식의 파일만 가능합니다.`
          );
          continue;
        }

        if (file.size > maxFileSize) {
          alert(
            `${file.name}의 파일 용량이 너무 큽니다. 30MB 이하만 가능합니다.`
          );
          continue;
        }

        const fileReader = new FileReader();

        fileReader.onload = (e) => {
          this.attachedFiles.push({
            preview: e.target.result,
            file: file,
          });
        };

        fileReader.readAsDataURL(file);
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
      if (!this.checkFormValidity()) {
        return;
      }
      this.saveToselectedInsruments();
      const data = {
        career: this.career,
        cost: this.cost,
        introduce: this.description,
        startTime: this.startTime,
        endTime: this.endTime,
        classDay: this.convertDaysToBitMask(),
        instruments: this.selectedInstruments,
      };
      try {
        await this.postTeacherProfileCreate(data);
        const teacherId = JSON.parse(localStorage.getItem("vuex")).common
          .teacherId;
        if (this.attachedFiles.length > 0) {
          await this.submitImages(teacherId);
        }
        this.updateUserType(1);
        this.$router.push(`/profile/teacherprofile/${teacherId}`);
      } catch (error) {
        console.error("Error while submitting the form:", error);
      }
    },

    async submitImages(teacherId) {
      let formData = new FormData();

      //  for (let i = 0; i < this.$refs.fileUploadInput.files.length; i++) {
      //    formData.append("image", this.$refs.fileUploadInput.files[i]);
      //  }
      this.attachedFiles.forEach((item) => {
        formData.append("image", item.file);
      });

      try {
        await axios.post(`/teachers/${teacherId}/images`, formData);
      } catch (error) {
        console.log(error);
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
      this.selectedInstruments = [];
      for (const instrument in this.instruments) {
        if (this.instruments[instrument]) {
          this.selectedInstruments.push(instrument);
        }
      }
    },
  },
  created() {
    this.getUserInfo();
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/teacherprofileupdate.scss";
@import "@/assets/scss/teacherprofileform.scss";
</style>
