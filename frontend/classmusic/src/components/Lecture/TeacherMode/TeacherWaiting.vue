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
      <button
        class="buttonWaiting"
        @click="$router.push(`/lecture/teacherwaiting`)"
      >
        대기 중
      </button>
      |
      <button @click="$router.push(`/lecture/teacherfinish`)">완료</button>
    </div>

    <div class="out__container">
      <div class="container">
        <div v-if="lectureList.length == 0">
          <h2>아직 등록된 신청이 없습니다.</h2>
        </div>

        <div>
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
              <button @click="acceptLecture(lecture.contactId)">
                수락하기
              </button>
              <button @click="refuseLecture(lecture.contactId)">
                거절하기
              </button>
              <button>채팅하기</button>
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
  },
  methods: {
    ...mapActions(["deleteContact"]),
    runDeleteContact(contactId) {
      this.deleteContact(contactId);
    },

    ...mapActions(["changeLectureState"]),
    acceptLecture(contactId) {
      this.changeLectureState({
        contactId: contactId,
        state: 1,
        mode: "acceptLecture",
      });
    },

    refuseLecture(contactId) {
      this.changeLectureState({
        contactId: contactId,
        state: 3,
        mode: "refuseLecture",
      });
    },
  },

  setup() {
    const store = useStore();

    const teacherId = store.getters.getTeacherId;

    onMounted(() => {
      store.dispatch("getLectureList", {
        id: teacherId,
        state: 0,
        type: 1,
      });
    });
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/lecture.scss";
</style>
