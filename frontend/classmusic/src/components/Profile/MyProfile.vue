<template>
  <div class="myprofile-container">
    <div class="myprofile">
      <div class="top-section">
        <div class="img-container">
          <img
            :src="userProfileImage"
            alt="User profile picture"
            class="user-image"
          />
          <button>사진 추가</button>
        </div>
        <div class="info-container">
          <div class="id-container">
            <label>ID:</label>
            <div class="info-field">{{ userInfo.id }}</div>
          </div>
          <div class="email-container">
            <label>Email:</label>
            <div class="info-field">{{ userInfo.email }}</div>
          </div>
          <div class="name-container">
            <label>Name:</label>
            <div class="info-field">{{ userInfo.name }}</div>
          </div>
          <div class="gender-container">
            <label>Gender:</label>
            <div class="info-field">{{ userInfo.gender === 1 ? '남자' : '여자' }}</div>
          </div>
        </div>
      </div>
      <div class="button">
        <button>비밀번호변경</button>
        <button>회원탈퇴</button>
      </div>
    </div>
    <div class="favorites">
      <!-- <h3>{{ userInfo.name }} 님이 즐겨찾기한 강사</h3> -->
      <!-- <div v-if="favoriteTeachers.length">
        <TeacherCard v-for="teacher in favoriteTeachers" :key="teacher.id" :teacher="teacher"></TeacherCard>
     </div>
     <p v-else>아직 즐겨찾기한 강사가 없습니다.</p> -->
    </div>
    {{ id }}
  </div>
</template>

<script>
// import { onMounted } from "vue";
import { apiGetUserInfo } from "@/api/profiles.js";
// import { useStore } from "vuex";

export default {
  data() {
    return {
      // userId : JSON.parse(localStorage.getItem("vuex")).common.userId,
      userInfo : {},
      id : JSON.parse(localStorage.getItem("vuex")).common.id,
    };
  },

  methods: {
    async getUserInfo() {
  
      try {
        const data = await apiGetUserInfo(this.id);
        console.log(this.id)
        console.log(data)
        if (data) {
          this.userInfo = data
          console.log(data)
        }
      } catch (error) {
        console.log(error)
      }
    },
  },
  created() {  
    this.getUserInfo();
  },
};
</script>

<style scoped>
@import "@/assets/scss/profile.scss";
</style>
