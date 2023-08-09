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
      <button @click="$router.push(`/lecture/studentongoing`)">진행 중</button>
      |
      <button
        class="buttonWaiting"
        @click="$router.push(`/lecture/studentwaiting`)"
      >
        대기 중
      </button>
      |
      <button @click="$router.push(`/lecture/studentfinish`)">완료</button>
    </div>

    <div class="out__container">
      <div class="container">
        <div>
          <div v-if="lectureList.length == 0">
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
              <button>강사 정보보기</button>
              <button
                @click="$router.push(`/chats/${lecture.contactId}?type=0`)"
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
