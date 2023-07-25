<template>
  <div class="teacher-review-container">
    <div class="review-header">
      <h3 class="review-title">강사 리뷰</h3>
      <p class="avg-rating">평균 별점: {{ avgRating }}</p>
    </div>

    <div class="review-box">
      <img :src="teacherImage" alt="Teacher profile picture" class="teacher-image" />
      <div class="review-content">
        <div class="student-info">
          <p>학생 이름: {{ studentName }}</p>
          <p>별점: {{ studentRating }}</p>
          <p>수강 기간: {{ courseDuration }}</p>
        </div>
        <div class="review-text">
          <p>{{ reviewText }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    teacherImage: {
      type: String,
      default: "https://via.placeholder.com/50",
    },
    avgRating: {
      type: String,
      default: "4.5",
    },
    studentName: {
      type: String,
      default: "박한샘",
    },
    studentRating: {
      type: String,
      default: "5",
    },
    courseDuration: {
      type: String,
      default: "6개월",
    },
    reviewText: {
      type: String,
      default: "최고의 강사입니다!",
    },
  },
};
</script>

<style scoped>
.teacher-review-container {
  box-sizing: border-box;
  width: 800px;
  margin: 0 auto;
}

.review-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.review-box {
  display: flex;
  margin-top: 20px;
  border: 1px solid black;
  padding: 10px;
  border-radius: 8px;
}

.teacher-image {
  width: 50px;
  height: 50px;
  object-fit: cover;
}

.review-content {
  margin-left: 20px;
}

.student-info p {
  margin: 5px 0;
}

.review-text {
  margin-top: 10px;
}
</style>
