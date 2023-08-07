<template>
  <div class="search-list">
    <!-- 강사 카드 컴포넌트 -->
    <div class="teacher-list">
      <TeacherCard
        v-for="teacher in teachers"
        :key="teacher.teacherId"
        :teacher="teacher"
        image="https://via.placeholder.com/280"
      />
    </div>
    <button @click="searchMoreTeacher">더 보기</button>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import TeacherCard from "./TeacherCard.vue";

export default {
  components: { TeacherCard },
  data() {
    return {};
  },
  computed: {
    ...mapGetters({ teachers: "getTeacherList" }),
  },
  methods: {
    ...mapActions(["searchMoreTeacher"]),
  },
};
</script>

<style scoped>
.search-list {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.teacher-list {
  padding-top: 30px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
/* .container {
    padding-top: 30px;
    display: flex;
    justify-content: flex-end;
  } */
</style>
