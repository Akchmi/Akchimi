<template>
  <div class="myprofile-container">
    <div class="myprofile">
      <div class="top-section">
        <div class="img-container">
          <img
            :src="userInfo.userProfileImage"
            alt="User profile picture"
            class="user-image"
          />
          <input type="file" ref="fileInput" @change="handleImageUpload" style="display: none"/>
          <button @click="triggerFileInput">사진 수정</button>

        </div>
        <div class="info-container">
          <!-- <div class="id-container">  -->
            <div> 
            <label>ID:</label>
            <div class="info-field">{{ userInfo.id }}</div>
          </div>
          <!-- <div class="email-container"> -->
            <div>
            <label>Email:</label>
            <div class="info-field">{{ userInfo.email }}</div>
          </div>
          <!-- <div class="name-container"> -->
            <div>
            <label>Name:</label>
            <div class="info-field">{{ userInfo.name }}</div>
          </div>
          <div>
          <!-- <div class="gender-container"> -->
            <label>Gender:</label>
            <div class="info-field">{{ userInfo.gender === 1 ? '남자' : '여자' }}</div>
          </div>
        </div>
      </div>
      <div class="button">
        <button @click="showChangePasswordModal = true">비밀번호 변경</button>
        <button>회원탈퇴</button>
      </div>

      <div class="modal" v-if="showChangePasswordModal">
        <div class="modal-background" @click="showChangePasswordModal = false"></div>
        <div class="modal-content">
          <div class="modal-header">
            <h3>비밀번호 변경</h3>
            <button class="close-button" @click="showChangePasswordModal = false">X</button>
          </div>

          <div class="modal-body">
            <label>현재 비밀번호:</label>
            <input type="password" v-model="currentPassword" placeholder="Current Password">
            <br>
            <label>새 비밀번호:</label>
            <input type="password" v-model="newPassword" placeholder="New Password">
          </div>

          <div class="modal-footer">
            <button @click="changePassword">확인</button>
          </div>
        </div>  
      </div>
    </div>
    
    <div class="favorites">
      <h3>{{ userInfo.name }} 님이 즐겨찾기한 강사</h3>
      <div class="teacher-list">
        <LikeTeacherCard
        v-for="teacher in liketeachers"
        :key="teacher.teacherId"
        :teacher="teacher"
        image="https://via.placeholder.com/280"
      />
      </div>   
    </div>
  </div>
</template>

<script>

import { apiGetUserInfo, apiLikeTeacher  } from "@/api/profiles.js";
import LikeTeacherCard from "./LikeTeacherCard.vue";
import { mapGetters, } from "vuex";
import axios from "@/api/imageAxios.js";


export default {
  components : {
    LikeTeacherCard,
  },
  data() {
    return { 
      userInfo : {},
      id : JSON.parse(localStorage.getItem("vuex")).common.id,
      showChangePasswordModal: false,
      currentPassword: '',
      newPassword: '',
      teacherId : '',
      liketeachers: [],
      userProfileImage:'',
    };
  },
  computed: {
    ...mapGetters({ teachers: "getLikeTeacherList"})
  },
  methods: {
    async handleImageUpload() {
    const selectedFile = this.$refs.fileInput.files[0];

    let formData = new FormData();
    formData.append('image', selectedFile);

    try {          
      let response = await axios.post(`/users/${this.id}/profileImage`, formData);
      
      if (response.data && response.data.userProfileImage) { 
          this.userProfileImage = response.data.userProfileImage;
      }
    } catch (error) {
        console.log(error);
    }
  },



    triggerFileInput() {
      this.$refs.fileInput.click();
    },
    async getUserInfo() {  
      try {
        const data = await apiGetUserInfo(this.id); 
        if (data) {
          this.userInfo = data      
        }
      } catch (error) {
        console.log(error)
      }
    },
    async likeTeachers() {
      try {
        const teacherData = await apiLikeTeacher(this.id);
       
        if (teacherData ) {
          this.liketeachers = teacherData
          console.log('dd', this.liketeachers )
        }
      } catch(error) {        
        console.log('즐찾선생', error)
      }
    }


  },
  created() {  
    this.getUserInfo();
    this.likeTeachers();
  },
};
</script>

<style scoped>
@import "@/assets/scss/profile.scss";
.teacher-list {
  padding-top: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>

