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
            class="buttonOngoing lectureSelectButton"
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
            class="lectureSelectButton"
            @click="$router.push(`/lecture/studentfinish`)"
          >
            완료
          </button>
        </div>
      </div>

      <!-- 강의 리스트-->
      <div class="out__container">
        <div class="container">
          <div v-if="lectureList.length == 0" class="noSearchLecture">
            <h2>수강중인 강의가 없습니다.</h2>
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
                  <textarea
                    v-if="nowUpdateMemoId == lecture.contactId"
                    class="memoInput"
                    type="text"
                    v-model="nowUpdateMemo"
                    maxlength="200"
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
                  @click="$router.push(`/chats/${lecture.contactId}?type=0`)"
                  style="margin-left: 5px"
                >
                  채팅입장
                </button>
                <button
                  @click="
                    $router.push(`/livemeeting/${lecture.contactId}?type=0`)
                  "
                  style="margin-left: 5px"
                >
                  강의실입장
                </button>
                <button
                  v-if="!lecture.memo"
                  @click="runUpdateMemo(lecture.contactId, lecture.memo)"
                  style="margin-left: 5px"
                >
                  메모하기
                </button>
                <button
                  v-if="lecture.memo"
                  @click="runUpdateMemo(lecture.contactId, lecture.memo)"
                  style="margin-left: 5px"
                >
                  메모수정
                </button>
                <button
                  @click="finishLecture(lecture.contactId)"
                  style="margin-left: 5px"
                >
                  강의완료
                </button>
              </div>
            </div>
            <div
              class="ongoing__container__button"
              v-if="nowUpdateMemoId == lecture.contactId"
            >
              <button @click="updateMemo(lecture.contactId)">완료</button>
              <button @click="cancleUpdateMemo" style="margin-left: 5px">
                취소
              </button>
            </div>
          </div>
        </div>
      </div>
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
    const teacherId = store.getters.getTeacherId;
    onMounted(() => {
      store.dispatch("getLectureList", {
        id: userId,
        state: 1,
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
