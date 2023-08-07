<template>
  <div>
    <div>
      <h2>강의실</h2>
      <br />
      <button @click="$router.push(`/lecture/studentongoing`)">수업</button>
      |
      <button @click="$router.push(`/lecture/teacherongoing`)">강의</button>
      <hr />
      <br />
      <br />
    </div>

    <div>
      <button @click="$router.push(`/lecture/teacherongoing`)">진행 중</button>
      |
      <button @click="$router.push(`/lecture/teacherwaiting`)">대기 중</button>
      |
      <button
        class="buttonFinish"
        @click="$router.push(`/lecture/teacherfinish`)"
      >
        완료
      </button>
    </div>
    <div>
      {{ lectureList }}
      <div class="out__container">
        <div class="container">
          <div
            class="ongoing__container"
            v-for="lecture in lectureList"
            :key="lecture.id"
          >
            <div class="ongoing__container__box">
              <img
                :src="lecture.userProfileImage"
                alt="Teacher profile picture"
                class="profileImage"
              />
              <div class="info-box">
                <div class="name">{{ lecture.name }}</div>
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
                <button @click="viewReview(lecture.contactId)">
                  내게 쓴 리뷰 보기
                </button>
              </div>
            </div>
            <div
              class="ongoing__container__button"
              v-if="nowUpdateMemoId == lecture.contactId"
            >
              <button @click="updateMemo(lecture.contactId)">완료</button>
              <button @click="cancleUpdateMemo">취소</button>
            </div>
            <div
              class="review__container__box"
              v-if="lecture.contactId == nowReviewId"
            >
              <div v-if="!review.content">
                리뷰:{{ review }}
                <div>
                  <h4>아직 학생이 리뷰를 남기지 않았습니다.</h4>
                </div>
              </div>
              <div v-if="review.content">
                {{ review }}
                <div>
                  <div class="review-box">
                    {{ review.content }}
                  </div>
                  <div>
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
    const userId = store.getters.getUserId;

    onMounted(() => {
      store.dispatch("getLectureList", {
        id: userId,
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
