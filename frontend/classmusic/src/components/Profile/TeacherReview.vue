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
          <span>학생 이름: {{ studentName }}</span>
          <span>별점: {{ studentRating }}</span>
          <span class="course-duration">수강 기간: {{ courseDuration }}</span>
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
      default: "김상혁",
    },
    studentRating: {
      type: String,
      default: "5",
    },
    courseDuration: {
      type: String,
      default: "2023.01.04~2023.12.27",
    },
    reviewText: {
      type: String,
      default: "터치가 좀 그렇네요!",
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
  padding-left: 20px;
  flex-grow: 1;
}

.student-info {
  display: flex;
  gap: 100px;
  width:100%;
}
.course-duration {
  margin-left: auto;
}
.review-text {
  margin-top: 10px;
  background-color: #f5f5dc;
  padding:10px;
  

}

</style>