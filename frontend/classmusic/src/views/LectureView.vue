<template>
  <div>
    <div>
      <NavBar></NavBar>
    </div>
    <div class="centered-div">
      <div>
        <h2>강의실</h2>
        <br />
        <button
          @click="
            currentTab = 'student';
            studentSubTab = 'ongoing';
          "
        >
          수업
        </button>
        |
        <button
          @click="
            currentTab = 'teacher';
            teacherSubTab = 'ongoing';
          "
        >
          강의
        </button>
        <hr />
        <br />
        <br />
      </div>

      <div v-if="currentTab === 'student'" style="margin: 10px">
        <button
          :class="{ ongoing: studentSubTab === 'ongoing' }"
          @click="studentSubTab = 'ongoing'"
        >
          진행 중
        </button>
        |
        <button
          :class="{ waiting: studentSubTab === 'waiting' }"
          @click="studentSubTab = 'waiting'"
        >
          대기 중
        </button>
        |
        <button
          :class="{ finish: studentSubTab === 'finish' }"
          @click="studentSubTab = 'finish'"
        >
          완료
        </button>
        <StudentOngoing v-if="studentSubTab === 'ongoing'"></StudentOngoing>
        <StudentWaiting v-if="studentSubTab === 'waiting'"></StudentWaiting>
        <StudentFinish v-if="studentSubTab === 'finish'"></StudentFinish>
      </div>

      <div v-if="currentTab === 'teacher'" style="margin: 10px">
        <button
          :class="{ ongoing: teacherSubTab === 'ongoing' }"
          @click="teacherSubTab = 'ongoing'"
        >
          진행 중
        </button>
        |
        <button
          :class="{ waiting: teacherSubTab === 'waiting' }"
          @click="teacherSubTab = 'waiting'"
        >
          대기 중
        </button>
        |
        <button
          :class="{ finish: teacherSubTab === 'finish' }"
          @click="teacherSubTab = 'finish'"
        >
          완료
        </button>

        <TeacherFinish v-if="teacherSubTab === 'finish'"></TeacherFinish>
        <TeacherOngoing v-if="teacherSubTab === 'ongoing'"></TeacherOngoing>
        <TeacherWaiting v-if="teacherSubTab === 'waiting'"></TeacherWaiting>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from "@/components/Nav/NavBar.vue";
import StudentFinish from "../components/Lecture/StudentMode/StudentFinish.vue";
import StudentOngoing from "../components/Lecture/StudentMode/StudentOngoing.vue";
import StudentWaiting from "../components/Lecture/StudentMode/StudentWaiting.vue";
import TeacherFinish from "../components/Lecture/TeacherMode/TeacherFinish.vue";
import TeacherOngoing from "../components/Lecture/TeacherMode/TeacherOngoing.vue";
import TeacherWaiting from "../components/Lecture/TeacherMode/TeacherWaiting.vue";

export default {
  components: {
    StudentFinish,
    StudentOngoing,
    StudentWaiting,
    TeacherFinish,
    TeacherOngoing,
    TeacherWaiting,
    NavBar,
  },
  data() {
    return {
      currentTab: "student",
      studentSubTab: "ongoing",
      teacherSubTab: "ongoing",
    };
  },
};
</script>

<style scoped>
@import "@/assets/scss/templates/common.scss";

.centered-div {
  width: 1024px;
  margin: 0 auto;
}

button {
  color: black;
}

button.ongoing {
  color: green;
}

button.waiting {
  color: yellow;
}

button.finish {
  color: red;
}
</style>

<style lang="scss" scoped>
@import "@/assets/scss/templates/common.scss";
</style>
