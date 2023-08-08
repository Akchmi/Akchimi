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
      <button
        class="buttonOngoing"
        @click="$router.push(`/lecture/studentongoing`)"
      >
        진행 중
      </button>
      |
      <button @click="$router.push(`/lecture/studentwaiting`)">대기 중</button>
      |
      <button @click="$router.push(`/lecture/studentfinish`)">완료</button>
    </div>
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
        </div>
        <div
          class="ongoing__container__button"
          v-if="nowUpdateMemoId != lecture.contactId"
        >
          <div
            v-if="!lecture.memo"
            @click="runUpdateMemo(lecture.contactId, lecture.memo)"
          >
            <button @click="$router.push(`/chats/${lecture.contactId}?type=0`)">
              채팅입장
            </button>
            <button
              @click="$router.push(`/livemeeting/${lecture.contactId}?type=0`)"
            >
              강의실입장
            </button>
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
            <button @click="finishLecture(lecture.contactId)">강의완료</button>
          </div>
          <div
            class="ongoing__container__button"
            v-if="nowUpdateMemoId == lecture.contactId"
          >
            <button @click="updateMemo(lecture.contactId)">완료</button>
            <button @click="cancleUpdateMemo">취소</button>
          </div>
        </div>
      </div>
      {{ lectureList }}
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters, useStore } from "vuex";
import { onMounted } from "vue";

export default {
  data() {
    return {
      nowUpdateMemo: "",
      nowUpdateMemoId: null,
    };
  },
  computed: {
    ...mapGetters({ lectureList: "getlectureList" }),
  },
  methods: {
    ...mapActions(["putUpdateMemo"]),

    runUpdateMemo(contactId, memo) {
      this.nowUpdateMemo = memo;
      this.nowUpdateMemoId = contactId;
    },

    moveChat() {},

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

    ...mapActions(["changeLectureState"]),
    finishLecture(contactId) {
      this.changeLectureState({
        contactId: contactId,
        state: 2,
        mode: "finishLecture_student",
      });
    },
  },
  setup() {
    const store = useStore();
    const userId = store.getters.getUserId;

    onMounted(() => {
      store.dispatch("getLectureList", {
        id: userId,
        state: 1,
        type: 0,
      });
    });
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/lecture.scss";
</style>
