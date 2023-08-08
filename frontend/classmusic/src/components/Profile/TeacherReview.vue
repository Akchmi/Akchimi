<template>
  <div class="teacher-review-container">
 

    <div class="review-box">
      <img :src="teacherImage" alt="Teacher profile picture" class="teacher-image" />
      <div class="review-content">
        <div class="student-info">
          <span>학생 이름: {{ review.name }}</span>
          <span>별점: {{ review.rating }}</span>
          <span>매칭수 : {{ review.contactCnt }}</span>
          <span class="course-duration">수강 기간: {{ review.startTime }} ~ {{ review.endTime }}</span>
        </div>
        <div class="review-text">
          <p>{{ review.content }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    review: Object,
  },

};
</script>

<style scoped>
.teacher-review-container {
  box-sizing: border-box;
  width: 800px;
  margin: 0 auto;
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