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
            class="buttonWaiting lectureSelectButton"
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

      <div class="out__container">
        <div class="container">
          <div>
            <div v-if="lectureList.length == 0" class="noSearchLecture">
              <h2>신청한 강의가 없습니다.</h2>
            </div>
            <div
              class="wating__listbox"
              v-for="lecture in lectureList"
              :key="lecture.id"
            >
              <div class="wating__info">
                <img
                  :src="lecture.userProfileImage"
                  alt="Teacher profile picture"
                  class="watingProfileImage"
                />
                <span class="watingProfileName">{{ lecture.name }}</span>
              </div>

              <div>
                <button
                  @click="
                    $router.push(
                      `/profile/teacherprofile/${lecture.matchingUserId}`
                    )
                  "
                >
                  프로필 보기
                </button>
                <button
                  @click="$router.push(`/chats/${lecture.contactId}?type=0`)"
                  style="margin-left: 10px"
                >
                  채팅하기
                </button>
              </div>
            </div>
            <div
              class="wating__listbox"
              v-for="lecture in refusedLectureList"
              :key="lecture.id"
            >
              <div class="wating__info">
                <img
                  :src="lecture.userProfileImage"
                  alt="Teacher profile picture"
                  class="watingProfileImage"
                />
                <span class="watingProfileName">{{ lecture.name }}</span>
              </div>
              <div>수업요청이 거절되었습니다.</div>

              <button @click="runDeleteContact(lecture.contactId)">
                삭제하기
              </button>
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
      lectures: [],
    };
  },
  computed: {
    ...mapGetters({ lectureList: "getlectureList" }),
    ...mapGetters({ refusedLectureList: "getRefusedLectureList" }),
  },
  methods: {
    ...mapActions(["deleteContact"]),
    runDeleteContact(contactId) {
      this.deleteContact(contactId);
    },
  },

  setup() {
    const store = useStore();
    const userId = store.getters.getUserId;

    onMounted(() => {
      store.dispatch("getLectureList", {
        id: userId,
        state: 0,
        type: 0,
      });

      store.dispatch("getRefusedLectureList", {
        id: userId,
        state: 3,
        type: 0,
      });
    });
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/lecture.scss";
</style>
