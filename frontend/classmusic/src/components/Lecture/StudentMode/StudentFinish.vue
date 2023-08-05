<template>
  <div>
    <div class="out__container">
      <div class="container">
        <div class="ongoing__container">
          <div class="ongoing__container__box">
            <img
              :src="image"
              alt="Teacher profile picture"
              class="profileImage"
            />
            <div class="info-box">
              <div class="name">강사이름</div>
              <div class="memo-box">
                <p>강사 악기</p>
                <p>강사 별점</p>
                <p>강사 자기소개</p>
              </div>
            </div>
          </div>
          <div class="review__container__box">
            <div>
              <textarea
                v-model="review"
                placeholder="리뷰를 작성해주세요."
                class="memoInput"
              ></textarea>
              <div>
                <input
                  type="number"
                  min="1"
                  max="5"
                  v-model.number="inputRating"
                />
                <button>리뷰 제출</button>
              </div>
            </div>
            <div>
              <div class="review-box">
                리뷰한 내용리뷰한 내용리뷰한 내용리뷰한 내용리뷰한 내용리뷰한
                내용리뷰한 내용리뷰한 내용리뷰한 내용리뷰한 내용리뷰한
                내용리뷰한 내용리뷰한 내용리뷰한 내용
              </div>
              <textarea class="memoInput" type="text">
                리뷰 수정 textarea
              </textarea>
              <div>
                <input type="number" min="1" max="5" />
                <button>리뷰수정</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  mounted() {},
  methods: {},
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/lecture.scss";
</style>
