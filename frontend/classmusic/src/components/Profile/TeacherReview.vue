<template>
  <div class="teacher-review-container">
    <div class="review-box">
      <img
        :src="review.userProfileImage"
        alt="user profile picture"
        class="teacher-image"
      />
      <div class="review-content">
        <div class="student-info">
          <span class="student-name">{{ review.name }}</span>
          <span class="rating-star"
            >별점:
            <span v-for="star in review.rating" :key="star">
              <i class="fa-solid fa-star" style="color: #ffdd00;"></i>
            </span>
          </span>
          <span class="course-duration"
            >수강 기간: {{ toLocalTimeStamp(review.startTime).substr(0, 10) }} ~
            {{ toLocalTimeStamp(review.endTime).substr(0, 10) }}</span
          >
        </div>
        <div class="review-text">
          <p>{{ review.content }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import utils from "@/common/utils";

export default {
  props: {
    review: Object,
  },
  methods: {
    toLocalTimeStamp(unixTimeStamp) {
      return utils.unixTimeStampToLocalTimeStamp(unixTimeStamp);
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

.review-box {
  display: flex;
  margin-top: 20px;
  border: solid 1px #edd9b7;
  padding: 10px;
  border-radius: 8px;
}

.teacher-image {
  width: 50px;
  height: 50px;
  object-fit: cover;
}

img {
  border-radius: 100%;
  border: solid 1px gray;
}

.review-content {
  padding-left: 20px;
  flex-grow: 1;
}

.student-info {
  display: flex;
  width: 100%;
}

.student-name {
  width: 30%;
}
.rating-star {
  width: 30%;
}
.course-duration {
  width: 40%;
  margin-left: auto;
}
.review-text {
  margin-top: 10px;
  background-color: #f5f5dc;
  padding: 10px;
}
</style>
