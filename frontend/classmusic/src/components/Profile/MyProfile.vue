<template>
  <div class="myprofile-container">
    <div class="myprofile">
      <div class="top-section">
        <div class="img-container">
          <img
            :src="userInfo.userProfileImage"
            :key="userInfo.userProfileImage"
            alt="User profile picture"
            class="user-image"
          />
          <input type="file" ref="fileInput" @change="handleImageUpload" style="display: none"/>
          <div class="img-button-container">
            <button @click="triggerFileInput">사진 수정</button>
            <button @click="deleteMyprofileImage(this.id)">사진 삭제</button>
          </div>
        </div>
        
        <div class="info-container">
          <!-- <div class="id-container">  -->
          <div class="info-field"> 
            <label>ID:</label>
            <div class="info-content">{{ userInfo.id }}</div>
          </div>
          <!-- <div class="name-container"> -->
            <div class="info-field">
              <label>Name:</label>
              <div class="info-content">{{ userInfo.name }}</div>
            </div>
          <!-- <div class="email-container"> -->
          <div class="info-field">
            <label>Email:</label>
            <div class="info-content">{{ userInfo.email }}</div>
          </div>
          <!-- <div class="gender-container"> -->
          <div class="info-field">
            <label>Gender:</label>
            <div class="info-content">{{ userInfo.gender === 1 ? '남자' : '여자' }}</div>
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
            <label>현재 비밀번호 : </label>
            <input type="password" v-model="currentPassword" placeholder="Current Password">
            <br>
            <label>새 비밀번호 :   </label>
            <input type="password" v-model="newPassword" placeholder="New Password">
          </div>

          <div class="modal-footer">
            <button @click="changePassword">확인</button>
          </div>
        </div>  
      </div>
    </div>

    
    <div class="favorites">
      <br /><hr /> <br />
      <div class="favorite-title">{{ userInfo.name }} 님이 즐겨찾기한 강사</div>
      <div class="teacher-list">
        <LikeTeacherCard
          v-for="teacher in liketeachers"
          :key="teacher.teacherId"
          :teacher="teacher"
          image="https://via.placeholder.com/280"
          @deleteLikeTeacher="deleteLikeTeacher(teacher.teacherId)"
        />
      </div>   
    </div>
  </div>
</template>

<script>

import { apiGetUserInfo, apiLikeTeacher,  apiChangePw, apiDeleteLIkeTeacher } from "@/api/profiles.js";
import LikeTeacherCard from "./LikeTeacherCard.vue";
import { mapGetters, mapActions, } from "vuex";
import axios from "@/api/imageAxios.js";
import router from "@/router/index";

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
    ...mapGetters({ teachers: "getLikeTeacherList",  })
  },
  methods: {
    ...mapActions(["deleteMyprofileImage"]),

    

    deleteLikeTeacher(teacherId) {
      apiDeleteLIkeTeacher(teacherId)
      .then(response => {
        if (response.success) {
          this.liketeachers = this.liketeachers.filter(teacher => teacher.teacherId !== teacherId);
      } else {
        alert("즐겨찾기 제거 중 오류 발생");
      }
    }).catch(error => {
      console.log(error);
    });
  },

    async changePassword() {
      const data = {
        oldPassword: this.currentPassword,
        newPassword: this.newPassword,
        id: this.id,
      };
      const response = await apiChangePw(data);
  
      if (response.success) {
        alert("비밀번호가 변경되었습니다.");
        this.currentPassword = '';
        this.newPassword = '';
        this.showChangePasswordModal = false;
      } else {
        alert("비밀번호 변경 중 오류가 발생했습니다. 현재 비밀번호를 확인하세요.");
        this.currentPassword = '';
        this.newPassword = '';
      }
    },      

 
 

    async handleImageUpload() {
    const selectedFile = this.$refs.fileInput.files[0];
    let formData = new FormData();
    formData.append('image', selectedFile);

    try {          
      let response = await axios.post(`/users/${this.id}/profileImage`, formData);
      
      if (response.data && response.data.userProfileImage) { 
          this.userInfo.userProfileImage = response.data.userProfileImage;

      }
      router.go(0)
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
    },
  },

  
  created() {  
    this.getUserInfo();
    this.likeTeachers();
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/profile.scss";

</style>

