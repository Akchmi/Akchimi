<template>
  <div>

    <!-- 남이 보는 강사 프로필 -->
    <div class="teacher-profile-container">
      <div class="teacher-profile">
        <div class="top-section">
          <img
            :src="userProfileImage"
            alt="Teacher profile picture"
            class="teacher-image"
          />
          <div class="teacher-name-gender-info">
            <div class="name-gender-row">
              <div class="name-box">
                <p class="label">강사이름</p>
                <span class="box">{{ name }}</span>
              </div>
              <div class="gender-box">
                <p class="label">성별</p>
                <span class="box">{{ genderText }}</span>
              </div>
            </div>
            <div class="teacher-info-container">
              <p class="info-box">악기 : {{ instrument }}</p>
              <p class="info-box">경력 : {{ career }}년</p>
              <p class="info-box">가격 : {{ cost }}원</p>
              <p class="info-box">
                시간 : {{ classDay !== undefined ? parseDays(classDay) : "" }},
                {{ startTime }} - {{ endTime }}
              </p>
            </div>
          </div>
        </div>
        <div class="teacher-details">
          <h2>자기 소개</h2>
          <div class="description-box">
            <p>{{ introduce }}</p>
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
         
        </div>
        <div class="button-group">
          <button v-if="Number(localteacherId) === Number(teacherId)">
            <router-link to="/profile/TeacherProfileUpdate" class="button"
              >강사 정보 수정</router-link
            >
          </button>
          <div v-else>
            <button @click="likeTeacherUpdate">강사 즐겨찾기</button>
            <button>
              <router-link to="/lecture/studentwaiting" class="button"
                >강의 신청</router-link
              >
            </button>
          </div>
        </div>
      </div>
      <div class="review-header">
        <h3 class="review-title">강사 리뷰</h3>
        <p class="avg-rating">평균 별점: {{ avgRating }}</p>
      </div>
      <TeacherReview
        v-for="review in reviews"
        :key="review.review"
        :review="review"
        image="https://via.placeholder.com/280"        
      />
    </div>
  </div>
</template>

<script>
import TeacherReview from "./TeacherReview.vue";
import { apiDetailTeacherInfo, apiGetReview } from "@/api/profiles.js";
import { mapActions } from "vuex";
import { useRoute } from "vue-router";
import axios from "@/api/imageAxios.js";

export default {
  components: {
    TeacherReview,
  },
  data() {
    return {
      name: "",
      gender: "",
      userProfileImage: "",
      career: 0,
      cost: 0,
      introduce: "",
      startTime: 0,
      endTime: 0,
      classDay: "",
      instrument: "",
      attachedFiles: [],
      reviews : [],
      avgRating : 0,
      contactCnt : 0,
      // userId : JSON.parse(localStorage.getItem("vuex")).common.userId,
      id : JSON.parse(localStorage.getItem("vuex")).common.id,
      teacherId : '',
      localteacherId: JSON.parse(localStorage.getItem("vuex")).common.teacherId,
    };
  },
  async created() {
    const route = useRoute();
    const teacherId = route.params.id;
    const res = await apiDetailTeacherInfo(teacherId);
    this.teacherId = teacherId;
    this.name = res.name;
    this.gender = res.gender;
    this.userProfileImage = res.userProfileImage;
    this.career = res.career;
    this.cost = res.cost;
    this.introduce = res.introduce;
    this.startTime = res.startTime;
    this.endTime = res.endTime;
    this.classDay = res.classDay;
    this.instrument = res.instruments;
    this.attachedFiles = res.images;
    this.avgRating = res.avgRating;
    this.contactCnt = res.contactCnt;
    this.getReview();
   
  },
  computed: {
    genderText() {
      return this.gender === 1 ? "남자" : "여자";
    },
  },
  methods: {
    ...mapActions(["postLikeTeacherUpdate"]),
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
          }
        } catch (error) {
          console.log(error);
        }
      }
    },

    parseDays(classDay) {
      const days = ["월", "화", "수", "목", "금", "토", "일"];
      return classDay
        .toString(2)
        .padStart(7, "0")
        .split("")
        .reverse()
        .map((day, index) => (day === "1" ? days[index] : null))
        .filter(Boolean)
        .join(", ");
    },
    likeTeacherUpdate() {
      const data = {
        id: JSON.parse(localStorage.getItem("vuex")).common.id,
        teacherId: this.$route.params.id,
       
      };
      this.postLikeTeacherUpdate(data)
        .then(() => {
          alert("즐겨찾기에 성공하였습니다");
          this.$router.push(`/profile/myprofile`);
        })
        .catch((error) => {
          console.log("즐찾실패", error);
        });
    },
    async getReview() {      
      try {
        const reviewData = await apiGetReview(this.teacherId);
        
        if (reviewData) {
          this.reviews = reviewData
          console.log('리뷰', this.reviews)
        }
      } catch (error) {
        console.log("리뷰에러", error);
      }
    },
  },
};
</script>

<style scoped>
@import "@/assets/scss/teacherprofile.scss";

</style>
