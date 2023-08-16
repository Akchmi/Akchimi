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
            <h2>강사 이름 : {{ name }}</h2>
          </div>
            <div class="teacher-profile-update__instruments teacher-profile-form__update">
              <label>악기 : </label>
              <button
                v-for="(checked, instrument) in instruments"
                :key="instrument"
                :class="checked ? 'update-selected-instrument' : 'update-unselected-instrument'"
                :v-model="selectedInstruments[instrument]"
                @click="toggleInstrument(instrument)"
              >
                {{ instrument }}
              </button>
            </div>
            
            <div class="teacher-profile-form__row">
              <div class="teacher-profile-form__update">
                <label for="years">경력 : </label>
                <input id="years" v-model.number="career" type="number" :max="maxCareer" min="0" @input="filterInput" />
                년
              </div>
              <div class="teacher-profile-form__update">
                <label for="cost">시간당 비용 : </label>
                <input id="cost" v-model.number="cost" type="number" :max="maxCost" min="0" @input="filterInput" />
                만원
              </div>
            </div>
            <div class="teacher-profile-form__update">
              <label>요일:</label>
              <div class="days-container" v-for="(checked, day) in days" :key="day">
                <input type="checkbox" :id="day" v-model="days[day]" />
                <label :for="day">{{ day }}</label>
              </div>
            </div>
            <div class="teacher-profile-form__row">
              <div class="teacher-profile-form__update">
                <label for="start">시작 시간 :</label>
                <input id="start" v-model.number="startTime" type="number" :max="maxStartTime" min="0" @input="filterInput" />

              </div>
              <div class="teacher-profile-form__update">
                <label for="end">종료 시간 :</label>
                <input id="end" v-model.number="endTime" type="number" :max="maxEndTime" min="1" @input="filterInput" />
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
          <h3>강사 자랑</h3>
          <button @click="triggerFileUpload">첨부 파일 추가</button>
        </div>
        <div class="file__images">
          <div
            v-for="(image, index) in attachedFiles"
            :key="index"
            class="image-container"
          >
            <img :src="image" alt="Attached file" class="attach-image" />

            <button class="image-remove" @click="removeAttachedFile(index)">삭제</button>
        
          </div>
          <div
            v-for="(image, index) in newAttachedFiles"
            :key="index"
            class="image-container"
          >
            <img :src="image.preview" alt="Attached file" class="attach-image" />

            <button class="image-remove" @click="removeNewAttachedFile(index)">삭제</button>
           
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
      <div class="update-button">
        <button class="update-submit" @click="submitForm">강사 수정하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import {
  apiDetailTeacherInfo,
  apiDeleteAttachedImage,
} from "@/api/profiles.js";
import axios from "@/api/imageAxios.js";

export default {
  props: {
    // image: { type: String, default: "https://via.placeholder.com/280" },
  },
  data() {
    return {
      userInfo: {},
      // selectedInstrument: null,
      instruments: {
				피아노: false,
				기타: false,
				드럼: false,
				바이올린: false,
				트럼펫: false,
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
      name: "",
      selectedDays: [],
      attachedFiles: [],
      userProfileImage: "",
      id: JSON.parse(localStorage.getItem("vuex")).common.id,
      userId: JSON.parse(localStorage.getItem("vuex")).common.userId,
      teacherId: JSON.parse(localStorage.getItem("vuex")).common.teacherId,
      newAttachedFiles: [],
      deleteAttachedFiles: [],
      maxCareer: 100,
      maxCost: 100,
      maxStartTime: 23,
      maxEndTime: 24,
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
    }
  },
  


  methods: {
    filterInput(event) {
      const validNumber = /^[0-9]*$/; 
      if (!validNumber.test(event.target.value)) {
        event.target.value = event.target.value.replace(/[^0-9]/g, ''); 
      }
      event.target.value = String(Number(event.target.value));
    },
    removeAttachedFile(index) {
      this.deleteAttachedFiles.push(this.attachedFiles[index]);
      this.attachedFiles.splice(index, 1);
    },
    removeNewAttachedFile(index) {
      this.newAttachedFiles.splice(index, 1);
    },
    triggerFileUpload() {
      this.$refs.fileUploadInput.click();
    },
    handleFileUpload() {
      const allowedExtensions = ['jpg', 'jpeg', 'png', 'gif', 'webp'];
      const selectedFiles = this.$refs.fileUploadInput.files;

      for (let i = 0; i < selectedFiles.length; i++) {
        const file = selectedFiles[i];
        const fileExtension = file.name.split('.').pop().toLowerCase();

        if (allowedExtensions.includes(fileExtension)) {
          const fileReader = new FileReader();

          fileReader.onload = (e) => {
            this.newAttachedFiles.push({
              preview: e.target.result,
              file: file,
            });
          };

          fileReader.readAsDataURL(file);
        } else {
          alert(`${file.name}는 송희도 같은 파일입니다.`);
        }
      }
    },

    toggleInstrument(instrument) {
			this.instruments[instrument] = !this.instruments[instrument];
      console.log(this.instruments);
		},

    ...mapActions(["putTeacherProfileUpdate"]),

    async submitImages() {
      let formData = new FormData();

      this.newAttachedFiles.forEach((item) => {
        formData.append("image", item.file);
      });

      try {
        await axios.post(`/teachers/${this.teacherId}/images`, formData);
      } catch (error) {
        console.log("폼폼", error);
      }
    },

    async submitAttachedFilesToDelete() {
      try {
        await apiDeleteAttachedImage(this.teacherId, {
          images: this.deleteAttachedFiles,
        });
        console.log("첨부 파일 삭제 성공");
      } catch (error) {
        console.log("첨부 파일 삭제 실패", error);
      }
    },

    async submitForm() {
      await this.submitImages();
      await this.submitAttachedFilesToDelete();
      await this.saveToselectedInsruments();
      

      const data = {
        career: this.career,
        cost: this.cost,
        introduce: this.description,
        startTime: this.startTime,
        endTime: this.endTime,
        classDay: this.convertDaysToBitMask(),
        instruments: [...this.selectedInstruments],
        teacherId: this.teacherId,
      };
      await this.putTeacherProfileUpdate(data); 
      const teacherId = JSON.parse(localStorage.getItem("vuex")).common.teacherId;
      this.$router.push(`/profile/teacherprofile/${teacherId}`);
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
      for(const inst in this.instruments){
        if(this.instruments[inst]){
          this.selectedInstruments.push(inst);
        }
      }
    },
  },
  async created() {
    const teacherId = JSON.parse(localStorage.getItem("vuex")).common.teacherId;
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

    res.classDay = res.classDay.toString().split('').reverse().join('');
    
    let classDayBinary;
    if (res.classDay == 0 || res.classDay === '0') {
      classDayBinary = '0000000';
    } else {
      const diff = 7 - res.classDay.toString().length;
      if (diff > 0) {
        classDayBinary = res.classDay + '0'.repeat(diff);
      } else {
        classDayBinary = res.classDay;
      }
    }

    Object.keys(this.days).forEach((day, index) => {
        this.days[day] = classDayBinary[index] === "1";
    });
  

    for(let i = 0; i < this.selectedInstruments.length; i++) {
      for(const inst in this.instruments) {
          if(inst === this.selectedInstruments[i]) {
            this.instruments[inst] = true;
        }
      }
    }
  }

};
</script>

<style scoped>
@import "@/assets/scss/teacherprofileupdate.scss";
@import "@/assets/scss/teacherprofileform.scss";
</style>
