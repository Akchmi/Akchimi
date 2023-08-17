<template>
  <div class="main__container">
    <div>
      <div class="lectureButtonTop">
        <!-- 수업 정보 버튼-->
        <div>
          <button
            class="lectureSelectButton"
            @click="$router.push(`/lecture/studentongoing`)"
          >
            배우기
          </button>

          <button
            class="lectureSelectButton buttonLecture"
            @click="$router.push(`/lecture/teacherongoing`)"
          >
            가르치기
          </button>
        </div>
        <div>
          <button
            class="lectureSelectButton"
            @click="$router.push(`/lecture/teacherongoing`)"
          >
            진행 중
          </button>

          <button
            class="lectureSelectButton"
            @click="$router.push(`/lecture/teacherwaiting`)"
          >
            대기 중
          </button>

          <button
            class="buttonFinish lectureSelectButton"
            @click="$router.push(`/lecture/teacherfinish`)"
          >
            완료
          </button>
        </div>
      </div>
      <div>
        <div class="out__container">
          <div class="container">
            <div v-if="lectureList.length == 0" class="noSearchLecture">
              <h2>아직 완료된 강의가 없습니다.</h2>
            </div>
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
                      class="profileImage_teacher"
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
                    내게 쓴 리뷰 보기
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
                <div v-if="!review.content">
                  <div>
                    <h4>아직 학생이 리뷰를 남기지 않았습니다.</h4>
                  </div>
                </div>
                <div v-if="review.content">
                  <div>
                    <div class="review-box">
                      {{ review.content }}
                    </div>
                    <div style="margin: 10px">
                      <p>평점 : {{ review.rating }}점</p>
                    </div>
                  </div>
                </div>
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
        type: 1,
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
  },
  setup() {
    const store = useStore();
    const teacherId = store.getters.getTeacherId;

    onMounted(() => {
      store.dispatch("getLectureList", {
        id: teacherId,
        state: 2,
        type: 1,
      });
    });
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/lecture.scss";
</style>
