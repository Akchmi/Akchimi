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
.teacher-card {
  display: flex;
  width: 800px;
  height: 140px;
  border: 1px solid #e8c68c;
  margin-bottom: 20px;
  border-radius: 10px;
  padding: 10px;
}

.teacher-card__image {
  width: 140px;
  height: 140px;
  margin-right: 20px;
  border-radius: 10px;
  object-fit: cover;
}

.teacher-card__info {
  display: flex;
  flex-direction: column;
  margin-top: 5px;
  font-size: 16px;
  flex-grow: 1;
}

.teacher-card__info__grade {
  display: flex;
  flex-direction: row;
}

.teacher-card__info__title {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.teacher-card__info__sub {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: right;
}

.teacher-card__instrument {
  font-size: 16px;
  margin-right: 10px;
}

.teacher-career {
  width: 80px;
  margin-left: 10px;
  font-size: 16px;
}

.teacher-avg-rating {
  margin-right: 10px;
  font-size: 16px;
}

.contact-count {
  width: 80px;
  margin-left: 10px;
  font-size: 16px;
}

.teacher-name {
  margin-bottom: 10px;
  font-size: 24px;
}

.teacher-description {
  font-size: 16px;
  margin: 5px 0px;
  height: 50px;
  width: 640px;

  text-overflow: ellipsis;
  overflow: hidden;
  word-break: break-word;

  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}

.teacher-card__buttons {
  display: flex;
  flex-direction: row;
  justify-content: right;
  align-items: center;
}

.teacher-card__button {
  margin-right: 20px;
}
</style>
