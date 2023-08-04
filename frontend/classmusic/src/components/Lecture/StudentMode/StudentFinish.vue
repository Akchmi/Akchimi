<template>
  <div>
    <div v-for="lecture in lectures" :key="lecture.contactId">
      {{ lecture }}
      <div class="student-finish-container">
        <div class="student-finish">
          <div class="top-section">
            <img
              :src="image"
              alt="Teacher profile picture"
              class="teacher-image"
            />
            <div class="info-box">
              <h3>{{ lecture.name }}</h3>
              <!-- <p>{{instrument}} | {{career}}</p> -->
              <p>{{ lecture.teacherInfo.name }}</p>
              <p>{{ lecture.teacherInfo.career }} 년</p>
              <p>별점 : {{ lecture.teacherInfo.avgRating }}</p>
              <p>강사소개 : {{ lecture.teacherInfo.introduce }}</p>
            </div>
          </div>
          <div class="button-group">
            <button @click="toggleInput">
              {{ showInput ? "취소" : "리뷰 작성하기" }}
            </button>
            <button @click="toggleView">작성한 리뷰 보기</button>
          </div>
        </div>
        <div v-if="showInput" class="input-area">
          <textarea
            v-model="review"
            placeholder="리뷰를 작성해주세요."
            class="review-input"
          ></textarea>
          <div class="rating-submit">
            <input type="number" min="1" max="5" v-model.number="inputRating" />
            <button @click="saveReview">리뷰 제출</button>
          </div>
        </div>
        <div v-if="showReview" class="view-area">
          <!-- <p>{{review}}</p> -->
          <!-- <p>별점 : {{localRating}}</p> -->
          <p>{{ lecture.myreview.content }}</p>
          <p>별점 : {{ lecture.myreview.rating }}</p>
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
    // instrument: {type:String, default: '드럼,피아노'},
    // career: {type: String, default: '카이스트 리코더 박사'},
    // rating: {type: Number, default: 0},
  },
  data() {
    return {
      showInput: false,
      showReview: false,
      review: "",
      inputRating: 5,
      localRating: 0,
      lectures: [],
    };
  },
  mounted() {
    this.localRating = this.rating;
  },
  methods: {
    toggleInput() {
      this.showInput = !this.showInput;
      this.showReview = false;
    },
    toggleView() {
      this.showReview = !this.showReview;
      this.showInput = false;
    },
    saveReview() {
      this.localRating = this.inputRating;
      this.showInput = false;
    },
  },
};
</script>

<style scoped>
.student-finish-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.student-finish {
  box-sizing: border-box;
  position: relative;
  display: flex;
  flex-direction: column;
  width: 800px;
  height: 300px;
  border: 1px solid black;
  margin-bottom: 20px;
  border-radius: 10px;
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
  margin-right: 1em;
}

.info-box {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  height: 200px;
  flex-grow: 1;
}

.button-group {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}

.input-area {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  margin-bottom: 20px;
}

.review-input {
  width: 800px;
  margin-bottom: 10px;
}

.rating-submit {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  width: 800px;
}
</style>
