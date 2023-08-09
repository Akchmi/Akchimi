<template>
    <div class="teacher-listcard">
      <img :src="teacher.userProfileImage" alt="Teacher profile picture" class="teacher-image" />
      <div class="teacher-info">
        <h2 class="teacher-name">{{ teacher.name }}</h2>
        <span
          v-for="(instrument, index) in teacher.instruments"
          :key="index"
          class="teacher-instrument"
          >{{ instrument }}</span
        >|<span class="teacher-career">{{ teacher.career }}</span>
        <br />
        <span class="teacher-avg-rating">{{ teacher.avgRating }}점</span>|<span
          class="contact-count"
          >{{ teacher.contactCnt }}회</span
        >
        <p class="teacher-description">{{ teacher.introduce }}</p>
        <div class="btn">
          <button @click="goToProfile(teacher.teacherId)">자세히 보기</button>
          <button @click="goToLecture">강의 신청</button>
        </div>
      </div>     
    </div>
  </template>
  
  <script>
  export default {

    props: {
      teacher: Object,      
    },
    methods: {
      goToProfile(teacherId) {
        this.$router.push(`/profile/teacherprofile/${teacherId}`);
      },
      goToLecture() {
        this.$router.push("/lecture/studentwaiting");
      },
    },

  };
  </script>
  
  <style scoped>
  .teacher-listcard {
    display: flex;
    width: 800px;
    height: 200px;
    border: 1px solid black;
    margin-bottom: 20px;
    border-radius: 8px;
    overflow: hidden;
  }
  .teacher-info {
    padding: 20px;
    font-size: 16px;
    flex-grow: 1;
    position: relatvie;
  }
  
  .teacher-image {
    width: 140px;
    height: 200px;
    object-fit: cover;
  }
  
  .teacher-instrument {
    padding: 20px;
    font-size: 16px;
  }
  
  .teacher-career {
    padding: 20px;
    font-size: 16px;
  }
  
  .teacher-avg-rating {
    padding: 20px;
    font-size: 16px;
  }
  
  .contact-count {
    padding: 20px;
    font-size: 16px;
  }
  
  .teacher-name {
    margin: 0;
    margin-bottom: 10px;
    font-size: 24px;
  }
  
  .teacher-description {
    margin: 0;
    font-size: 16px;
    height: 3em;
  }
  
  .btn {
    display: flex;
    justify-content: flex-end;
  }
  </style>
  