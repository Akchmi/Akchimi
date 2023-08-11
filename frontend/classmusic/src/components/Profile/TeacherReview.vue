<template>
  <div class="teacher-review-container">
    <div class="review-box">
      <img :src= "review.userProfileImage" alt="user profile picture" class="teacher-image" />
      <div class="review-content">
        <div class="student-info">
          <span class="student-name">{{ review.name }}</span>
          <span class="rating-star">별점: 
            <span v-if="0<=review.rating && review.rating<=0.5"> </span>
            <span v-else-if="0.5<review.rating && review.rating<1.5"> ⭐</span>
            <span v-if="1.5<=review.rating && review.rating<2.5"> ⭐⭐</span>
            <span v-if="2.5<=review.rating && review.rating<3.5"> ⭐⭐⭐</span>
            <span v-if="3.5<=review.rating && review.rating<4.5"> ⭐⭐⭐⭐</span>
            <span v-if="4.5<=review.rating && review.rating<=5"> ⭐⭐⭐⭐⭐</span>
          </span>          
          <span class="course-duration">수강 기간: {{ toLocalTimeStamp(review.startTime) }} ~ {{ toLocalTimeStamp(review.endTime) }}</span>
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
  methods:  {    
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
  border: solid 1px #edd9b7 ;
  padding: 10px;
  border-radius: 8px;
}

.teacher-image {
  width: 50px;
  height: 50px;
  object-fit: cover;
}

img{
  border-radius: 100%;
  border: solid 1px gray;
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
.rating-star{
  width: 25%;
}
.course-duration {
  width: 50%;
  margin-left: auto;
}
.review-text {
  margin-top: 10px;
  background-color: #f5f5dc;
  padding:10px;
}

</style>