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
        <button
          class="teacher-card__button"
          @click="goToProfile(teacher.teacherId)"
        >
          자세히 보기
        </button>
        <button @click="registerLecture(teacher.teacherId)">강의 신청</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  props: {
    teacher: Object,
  },
  computed: {
    ...mapGetters({ loginUserId: "getUserId" }),
  },

  methods: {
    ...mapActions(["postMachingCreate"]),
    registerLecture(teacherId) {
      this.postMachingCreate({
        teacherId: teacherId,
        studentId: this.loginUserId,
        mode: "registerLecture",
      });
    },
    goToProfile(teacherId) {
      this.$router.push(`/profile/teacherprofile/${teacherId}`);
    },
  },
};
</script>

<style scoped>
@import "@/assets/scss/teachercard.scss";
</style>
