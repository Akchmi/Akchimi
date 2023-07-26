<template>
  <div>
    lectureview <br />
    <button @click="currentTab = 'student'; studentSubTab = 'ongoing';">
      수업
    </button>
    |
    <button @click="currentTab = 'teacher'; teacherSubTab = 'ongoing';">
      강의
    </button>

   
    <div v-show="currentTab === 'student'">
      <button @click="studentSubTab = 'ongoing'">진행 중</button>
      <button @click="studentSubTab = 'waiting'">대기 중</button>
      <button @click="studentSubTab = 'finish'">완료</button>

      <StudentFinish v-show="studentSubTab === 'finish'"></StudentFinish>
      <StudentOngoing v-show="studentSubTab === 'ongoing'"></StudentOngoing>
      <StudentWaiting v-show="studentSubTab === 'waiting'"></StudentWaiting>
    </div>

    
    <div v-show="currentTab === 'teacher'">
      <button @click="teacherSubTab = 'ongoing'">진행 중</button>
      <button @click="teacherSubTab = 'waiting'">대기 중</button>
      <button @click="teacherSubTab = 'finish'">완료</button>

      <TeacherFinish v-show="teacherSubTab === 'finish'"></TeacherFinish>
      <TeacherOngoing v-show="teacherSubTab === 'ongoing'"></TeacherOngoing>
      <TeacherWaiting v-show="teacherSubTab === 'waiting'"></TeacherWaiting>
    </div>
  </div>
</template>

<script>
import StudentFinish from '../components/Lecture/StudentMode/StudentFinish.vue';
import StudentOngoing from '../components/Lecture/StudentMode/StudentOngoing.vue';
import StudentWaiting from '../components/Lecture/StudentMode/StudentWaiting.vue';
import TeacherFinish from '../components/Lecture/TeacherMode/TeacherFinish.vue';
import TeacherOngoing from '../components/Lecture/TeacherMode/TeacherOngoing.vue';
import TeacherWaiting from '../components/Lecture/TeacherMode/TeacherWaiting.vue';

export default {
  components: {
    StudentFinish,
    StudentOngoing,
    StudentWaiting,
    TeacherFinish,
    TeacherOngoing,
    TeacherWaiting,
  },
  data() {
    return {
      currentTab: 'student', 
      studentSubTab: 'ongoing', 
      teacherSubTab: '',
    };
  },
};
</script>

<style scoped>



</style>