<template>
  <div>
    teacherprofile
    <button>
      <router-link to="/profile/TeacherProfileUpdate" class="button">강사 정보 수정</router-link>
    </button>
    <!-- <button v-if="userId === teacherId">
      <router-link to="/profile/TeacherProfileUpdate" class="button">강사 정보 수정</router-link>
   </button> -->
    <!-- 남이 보는 강사 프로필 -->
    <div class="teacher-profile-container">
      <div class="teacher-profile">
        <div class="top-section">
          <img :src="userProfileImage" alt="Teacher profile picture" class="teacher-image" />
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
            <div class="info-container">
              <p class="info-box">악기 : {{ instrument }}</p>
              <p class="info-box">경력 : {{ career }}년</p>
              <p class="info-box">가격 : {{ cost }}원</p>
              <p class="info-box">시간 : {{ classDay !== undefined ? parseDays(classDay) : '' }}, {{ startTime }} - {{ endTime }}</p>

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
      
          <img v-for="(file, index) in attachedFiles" :src="file" :key="index" alt="Attached file" class="attach-image" />

        </div>
        <div class="button-group">
          <button @click="likeTeacherUpdate">강사 즐겨찾기</button>
          <router-link to="/lecture/studentwaiting" class="button">강의 신청</router-link>
        </div>
      </div>
      <TeacherReview></TeacherReview>
    </div>
  </div>
</template>

<script>
import TeacherReview from './TeacherReview.vue'
import { apiDetailTeacherInfo, } from "@/api/profiles.js";  
import { mapActions, } from "vuex";

export default {
  components: {
    TeacherReview
  },
  data() {
    return {
      name: '',
      gender: '',
      userProfileImage: '',
      career: 0,
      cost: 0,
      introduce: '',
      startTime: 0,
      endTime: 0,
      classDay: '',
      instrument: '',
      attachedFiles: [],
      // userId : JSON.parse(localStorage.getItem("vuex")).common.userId,
      teacherId: JSON.parse(localStorage.getItem("vuex")).common.teacherId,
    }
  },
  async created() {
    const teacherId = JSON.parse(localStorage.getItem("vuex")).common.teacherId
    const res = await apiDetailTeacherInfo(teacherId); 
    this.teacherId = res.teacherId;
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
    this.attachedFiles = res.attachedFiles; 
  },
  computed: {
    genderText() {   
      return this.gender === 1 ? '남자' : '여자';
    }
  },
  methods: {
    ...mapActions(["postLikeTeacherUpdate"]),
    parseDays(classDay) {
      const days = ['월','화','수','목','금','토','일'];
      return classDay
        .toString(2)
        .padStart(7, '0')
        .split('')
        .reverse()
        .map((day, index) => day === '1' ? days[index] : null)
        .filter(Boolean)
        .join(', ');
    },
    likeTeacherUpdate() {
      const data = {
        teacherId : JSON.parse(localStorage.getItem("vuex")).common.teacherId
      }
      this.postLikeTeacherUpdate(data)   
        .then(response => {
          console.log(1,response)
          alert("즐겨찾기에 성공하였습니다")
          this.$router.push(`/profile/myprofile`)
        })
        .catch(error => {
          console.log('즐찾실패', error)
        })
    }
  },
};
</script>

<style scoped>
@import "@/assets/scss/teacherprofile.scss";
</style>
