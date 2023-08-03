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
    <button @click="this.$emit('searchMore')">더 보기</button>
  </div>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";
import TeacherCard from "./TeacherCard.vue";

export default {
  components: { TeacherCard },
  data() {
    return {};
  },
  props: {
    teachers: Array,
  },
  setup() {
    const store = useStore();
    const notices = computed(() => store.state.notices.notices);

    return { notices };
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

<!-- <template lang="">
  <div>
    <div class="container">

      <select v-model="selectedSearchCategory">
        <option v-for="(item, idx) in searchCategory" :key="idx" :value="item">
          {{ item }}
        </option>
      </select>
    </div>    

    <div>
      <TeacherCard
        v-for="(teacher, index) in teachers"
        :key="index"
        :image="teacher.image"
        :name="teacher.name"
        :description="teacher.description"
      />
    </div>
  </div>
</template>
<script>
import { computed } from "vue";
import { useStore } from "vuex";
import TeacherCard from "./TeacherCard.vue";


export default {
  components: {
    TeacherCard,
  },
  data() {
    return {
      searchQuery: "",
      selectedSearchCategory: "전체",
      teachers: [

      ],
    };
  },
  setup() {
    const store = useStore();
    const notices = computed(() => store.state.notices.notices);

    const searchCategory = ["전체", "별점", "매칭"];

    return { notices, searchCategory };
  },

}
</script>

<style scoped>
.container {
  padding-top: 30px;
  display: flex;
  justify-content: flex-end;
}

</style>  -->
