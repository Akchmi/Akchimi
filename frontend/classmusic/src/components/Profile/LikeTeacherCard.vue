<template>
  <div class="teacher-card">
    <img
      :src="teacher.userProfileImage"
      alt="Teacher profile picture"
      class="teacher-card__image"
    />
    <div class="teacher-card__info">
      <div class="teacher-card__info__title">
        <h2 class="teacher-name">{{ teacher.name }}</h2>
        <div class="teacher-card__info__grade">
          <span class="teacher-avg-rating"
            >평점 : {{ teacher.avgRating }}점</span
          >|
          <div class="contact-count">수업 : {{ teacher.contactCnt }}회</div>
        </div>
      </div>
      <div class="teacher-card__info__sub">
        <span
          v-for="(instrument, index) in teacher.instruments"
          :key="index"
          class="teacher-card__instrument"
          >{{ instrument }}</span
        >|
        <div class="teacher-career">경력 : {{ teacher.career }}년</div>
      </div>

      <div>
        <div class="teacher-description">{{ teacher.introduce }}</div>
      </div>

      <div class="teacher-card__buttons">
        <button class="teacher-card__button" @click="deleteLikeTeacher">
          즐겨찾기 취소
        </button>
        <button
          class="teacher-card__button"
          @click="goToProfile(teacher.teacherId)"
        >
          자세히 보기
        </button>
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
    deleteLikeTeacher() {
      this.$emit("deleteLikeTeacher", this.teacher.teacherId);
    },
  },
};
</script>

<style scoped>
@import "@/assets/scss/teachercard.scss";
</style>
