<template>
  <div class="main__container">
    <div>
      <div class="lectureButtonTop">
        <!-- 수업 정보 버튼-->
        <div>
          <button
            class="buttonLecture lectureSelectButton"
            @click="$router.push(`/lecture/studentongoing`)"
          >
            배우기
          </button>

          <button
            class="lectureSelectButton"
            @click="$router.push(`/lecture/teacherongoing`)"
            v-if="teacherId"
          >
            가르치기
          </button>
        </div>
        <div>
          <button
            class="lectureSelectButton"
            @click="$router.push(`/lecture/studentongoing`)"
          >
            진행 중
          </button>

          <button
            class="lectureSelectButton"
            @click="$router.push(`/lecture/studentwaiting`)"
          >
            대기 중
          </button>

          <button
            class="buttonFinish lectureSelectButton"
            @click="$router.push(`/lecture/studentfinish`)"
          >
            완료
          </button>
        </div>
      </div>

      <div>
        <div class="out__container">
          <div class="container">
            <div v-if="lectureList.length == 0" class="noSearchLecture">
              <h2>수강 완료한 강의가 없습니다.</h2>
            </div>
            <!-- 강사 리스트-->
            <div
              class="ongoing__container"
              v-for="lecture in lectureList"
              :key="lecture.id"
            >
              <div class="ongoing__container__box">
                <div class="ongoing__container__left">
                  <div style="display: flex; justify-content: center">
                    <img
                      :src="lecture.userProfileImage"
                      alt="Teacher profile picture"
                      class="profileImage_student"
                      @click="
                        $router.push(
                          `/profile/teacherprofile/${lecture.matchingUserId}`
                        )
                      "
                    />
                  </div>
                  <div class="name">{{ lecture.name }}</div>
                </div>
                <div class="info-box">
                  <div class="memo-box">
                    <div v-if="nowUpdateMemoId != lecture.contactId">
                      <div v-if="!lecture.memo">
                        <p>메모를 입력해주세요</p>
                      </div>
                      <div v-else>
                        {{ lecture.memo }}
                      </div>
                    </div>
                    <!-- 메모 수정-->
                    <textarea
                      v-if="nowUpdateMemoId == lecture.contactId"
                      class="memoInput"
                      type="text"
                      v-model="nowUpdateMemo"
                    />
                  </div>
                </div>
              </div>
              <div
                class="ongoing__container__button"
                v-if="nowUpdateMemoId != lecture.contactId"
              >
                <div>
                  <button
                    v-if="!lecture.memo"
                    @click="runUpdateMemo(lecture.contactId, lecture.memo)"
                  >
                    메모하기
                  </button>
                  <button
                    v-if="lecture.memo"
                    @click="runUpdateMemo(lecture.contactId, lecture.memo)"
                  >
                    메모수정
                  </button>
                  <button
                    @click="viewReview(lecture.contactId)"
                    style="margin-left: 10px"
                  >
                    내가 쓴 리뷰 보기
                  </button>
                </div>
              </div>
              <div
                class="ongoing__container__button"
                v-if="nowUpdateMemoId == lecture.contactId"
              >
                <button @click="updateMemo(lecture.contactId)">완료</button>
                <button @click="cancleUpdateMemo" style="margin-left: 10px">
                  취소
                </button>
              </div>
              <div
                class="review__container__box"
                v-if="lecture.contactId == nowReviewId"
              >
                <!-- 리뷰 작성-->

                <div v-if="!review.content">
                  <textarea
                    placeholder="리뷰를 작성해주세요."
                    class="memoInput"
                    v-model="nowUpdateReview"
                    style="margin: 10px"
                  ></textarea>
                  <div>
                    <select v-model="nowUpdateRating" style="margin: 10px">
                      <option
                        v-for="score in [1, 2, 3, 4, 5]"
                        :key="score.id"
                        :value="score"
                      >
                        {{ score }}
                      </option>
                    </select>
                    <button @click="runPostReview(lecture.contactId)">
                      리뷰 제출
                    </button>
                  </div>
                </div>
                <!-- <div v-if="review.content "> -->
                <!-- 리뷰 확인 -->
                <div v-if="review.content && nowUpdateReviewId == -1">
                  <div class="review-box">
                    {{ review.content }}
                  </div>
                  <div style="margin: 10px">
                    <p>평점 : {{ review.rating }}점</p>
                  </div>
                  <div style="display: flex; justify-content: right">
                    <button
                      @click="
                        runUpdateReview(
                          review.reviewId,
                          review.content,
                          review.rating,
                          lecture.contactId
                        )
                      "
                    >
                      리뷰수정
                    </button>
                    <button
                      @click="runDeleteReview(review.reviewId)"
                      style="margin-left: 10px"
                    >
                      리뷰삭제
                    </button>
                  </div>
                </div>
                <!--리뷰 수정 -->

                <div v-if="review.content && nowUpdateReviewId != -1">
                  <textarea
                    class="memoInput"
                    type="text"
                    v-model="nowUpdateReview"
                  >
                  </textarea>
                  <div style="display: flex; justify-content: space-between">
                    <div>
                      평점 :
                      <select v-model="nowUpdateRating" style="margin: 10px">
                        <option
                          v-for="score in [1, 2, 3, 4, 5]"
                          :key="score.id"
                          :value="score"
                        >
                          {{ score }}
                        </option>
                      </select>
                    </div>
                    <div style="margin: 10px">
                      <button
                        style="margin-right: 10px"
                        @click="
                          runUpdateReview(
                            review.reviewId,
                            nowUpdateReview,
                            nowUpdateRating,
                            lecture.contactId
                          )
                        "
                      >
                        수정완료
                      </button>
                      <button @click="cancleUpdateReview">취소</button>
                    </div>
                  </div>
                </div>
                <!-- </div> -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore, mapGetters, mapActions } from "vuex";
import { onMounted } from "vue";

export default {
  data() {
    return {
      nowUpdateMemo: "",
      nowUpdateMemoId: null,
      nowReviewId: null,
      nowUpdateReview: "",
      nowUpdateReviewId: -1,
      nowUpdateRating: 5,
      lastReviewcontent: "",
    };
  },
  computed: {
    ...mapGetters({ lectureList: "getlectureList" }),
    ...mapGetters({ review: "getReview" }),
  },
  methods: {
    runUpdateMemo(contactId, memo) {
      this.nowUpdateMemo = memo;
      this.nowUpdateMemoId = contactId;
    },

    ...mapActions(["putUpdateMemo"]),

    updateMemo(contactId) {
      this.putUpdateMemo({
        contactId: contactId,
        memo: this.nowUpdateMemo,
        type: 0,
      });

      this.nowUpdateMemo = "";
      this.nowUpdateMemoId = null;
    },

    cancleUpdateMemo() {
      this.nowUpdateMemo = "";
      this.nowUpdateMemoId = null;
    },

    ...mapActions(["getReview"]),
    viewReview(contactId) {
      if (contactId != this.nowReviewId) {
        this.getReview({ contactId: contactId }).then(() => {
          this.nowReviewId = contactId;
        });
      } else {
        this.nowReviewId = null;
      }
    },

    ...mapActions(["postReview"]),
    runPostReview(contactId) {
      if (this.nowUpdateReview.length == 0) {
        alert("리뷰를 작성해주세요.");
        return;
      }
      this.postReview({
        contactId: contactId,
        rating: this.nowUpdateRating,
        content: this.nowUpdateReview,
      });
      this.nowUpdateRating = 5;
      this.nowUpdateReview = "";
    },

    ...mapActions(["putReviewUpdate"]),
    runUpdateReview(reviewId, content, rating, contactId) {
      if (this.nowUpdateReviewId != reviewId) {
        this.lastReviewcontent = content;
        this.nowUpdateReviewId = reviewId;
        this.nowUpdateReview = this.review.content;
        this.nowUpdateRating = this.review.rating;
        return;
      } else {
        if (this.nowUpdateReview.length == 0) {
          alert("리뷰를 작성해주세요.");
          return;
        }

        this.putReviewUpdate({
          reviewId: reviewId,
          content: content,
          rating: rating,
          contactId: contactId,
        });
      }
      this.nowUpdateReviewId = -1;
      this.nowUpdateReview = "";
      this.nowUpdateRating = 5;
    },

    cancleUpdateReview() {
      this.review.content = this.lastReviewcontent;
      this.nowUpdateReviewId = -1;
    },

    ...mapActions(["deleteReview"]),
    runDeleteReview(reviewId) {
      this.deleteReview(reviewId);
    },
  },
  setup() {
    const store = useStore();
    const userId = store.getters.getUserId;
    const teacherId = store.getters.getTeacherId;
    onMounted(() => {
      store.dispatch("getLectureList", {
        id: userId,
        state: 2,
        type: 0,
      });
    });

    return { teacherId };
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/lecture.scss";
</style>
