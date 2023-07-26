<template>
  <div>
    teacherprofile
    <button>
      <router-link to="/profile/TeacherProfileUpdate" class="button">강사 정보 수정</router-link>
    </button>
    <!-- 남이 보는 강사 프로필 -->
    <div class="teacher-profile-container">
      <div class="teacher-profile">
        <div class="top-section">
          <img :src="image" alt="Teacher profile picture" class="teacher-image" />
          <div class="teacher-name-gender-info">
            <div class="name-gender-row">
              <div class="name-box">
                <p class="label">강사이름</p>
                <span class="box">{{ name }}</span>
              </div>
              <div class="gender-box">
                <p class="label">성별</p>
                <span class="box">{{ gender }}</span>
              </div>
            </div>
            <div class="info-container">
              <p class="info-box">악기 : {{ information.instrument }}</p>
              <p class="info-box">경력 : {{ information.career }}</p>
              <p class="info-box">가격 : {{ information.expense }}</p>
              <p class="info-box">시간 : {{ information.time }}</p>
            </div>
          </div>
        </div>
        <div class="teacher-details">
          <h2>자기 소개</h2>
          <div class="description-box">
            <p>{{ description }}</p>
          </div>
        </div>
        <div class="attach-file">
          <img :src="image" alt="Attached file" class="attach-image" />
          <img :src="image" alt="Attached file" class="attach-image" />
          <img :src="image" alt="Attached file" class="attach-image" />
        </div>
        <div class="button-group">
          <router-link to="/" class="button">강사 즐겨찾기</router-link>
          <router-link to="/lecture/studentwaiting" class="button">강의 신청</router-link>
        </div>
      </div>
      <TeacherReview></TeacherReview>
    </div>
 
  </div>
</template>

<script>

import TeacherReview from './TeacherReview.vue'

export default {
  components: {
    TeacherReview
  },
  props: {
    image: { type: String, default: "https://via.placeholder.com/280" },
    name: { type: String, default: "박한샘" },
    gender: { type: String, default: "respect" },
    information: {
      type: Object,
      default: () => ({
        instrument: "기타",
        career: "10년",
        expense: "5만원",
        time: "1시간",
      }),
    },
    description: { type: String, default: "이대나온여자" },
    attachedFile: { type: String, default: "https://via.placeholder.com/280" },
  },
};
</script>

<style scoped>

.teacher-profile-container {
  display: flex;
  flex-direction: column;
  justify-content: center; 
  align-items: center; 
  
}

.button-group {
  position: absolute;
  bottom: 10px;
  right: 10px;
}

.button {
  background: none;
  border: none;
  color: inherit;
  text-align: inherit;
  cursor: pointer;
  padding: 0;
  font: inherit;
  outline: inherit;
  text-decoration: none; 
  margin-right: 10px; 
}


.attach-image {
  width:170px;
  height:220px;
  object-fit: cover;
}

.description-box {
  border: 1px solid black;
  padding: 10px;
  height: auto; 
  min-height: 6em;
  max-height: auto; 
  overflow: auto; 
  line-height: 1.2em; 
  margin-bottom: 20px;
  border-radius: 10px;
}

.introduce {
  border: 1px solid black;
  width: 30px;
}

.teacher-profile {
  box-sizing: border-box;
  position: relative;
  display: flex;
  flex-direction: column;
  width: 800px;
  height: 800px;
  border: 1px solid black;
  margin-bottom: 20px;
  border-radius: 8px;
  overflow: hidden;
  padding: 20px;
}

.top-section {
  display: flex;
}

.teacher-image {
  width: 170px;
  height: 220px;
  object-fit: cover;
}

.teacher-name-gender-info {
  padding-left: 20px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  height: 200px;
  flex-grow: 1;
}

.name-gender-row {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.name-box,
.gender-box {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  flex-basis: 30%;
  text-align: center;
}

.box {
  display: flex;
  font-size: 16px;
  border: 1px solid black;
  width: 70px; 
  height: 24px;
  margin: 1px;
  border-radius: 10px;
  justify-content: center;
  align-items: center;
}

.info-container {
  border: 1px solid black;
  border-radius: 10px;
  padding: 10px;
  height: 140px; 
  justify-content: center;
  align-items: center;
}

.info-box {
  margin-bottom: 5px;
  text-align: left;
  height: 15px;
  display: flex;
  align-items: center;
}

.label {
  line-height: 1; 
  margin: 0; 
  padding: 0; 
}
</style>
