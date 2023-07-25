<template>
  <div>
    teacherprofile
    <br />
    <button>
      <router-link to="/profile/teacherprofileupdate"
        >프로필 수정하기</router-link
      >
    </button>
    <br />
    <button>
      <router-link to="/profile/teacherreview">리뷰보기</router-link>
    </button>

    <!-- 남이 보는 강사 프로필 -->
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
        <div>
          <p>{{ description }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
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
  },
};
</script>

<style scoped>
.introduce {
  border: 1px solid black;
  width: 30px;
}

.teacher-profile {
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
  flex-basis: 30%; /* from 40% to 30% */
  text-align: center;
}

.box {
  display: flex;
  font-size: 16px;
  border: 1px solid black;
  width: 70px; /* from 70px to 50px */
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
  height: 140px; /* adjusted the height */
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
  line-height: 1; /* 줄간격을 조절 */
  margin: 0; /* 마진을 조절 */
  padding: 0; /* 패딩을 조절 */
}
</style>
