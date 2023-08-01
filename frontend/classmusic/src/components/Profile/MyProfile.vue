<template>
  <div class="myprofile-container">
    <div class="myprofile">
      <div class="top-section">
        <div class="img-container">
          <img :src="userProfileImage" alt="User profile picture" class="user-image" />
          <button>사진 추가</button>
        </div>
        <div class="info-container">
          <div class="id-container">
            <label>ID:</label>
            <div class="info-field">{{ id }}</div>
          </div>
          <div class="email-container">
            <label>Email:</label>
            <div class="info-field">{{ email }}</div>
          </div>
          <div class="name-container">
            <label>Name:</label>
            <div class="info-field">{{ name }}</div>
          </div>
          <div class="gender-container">
            <label>Gender:</label>
            <div class="info-field">{{ gender }}</div>
          </div>
        </div>
      </div>
      <div class="button">
        <button>저장</button>
        <button>비밀번호변경</button>
        <button>회원탈퇴</button>
      </div>
    </div>
    <div class="favorites">
      <h3>{{name}} 님이 즐겨찾기한 강사</h3>
      <div v-if="favoriteTeachers.length">
        <TeacherCard v-for="teacher in favoriteTeachers" :key="teacher.id" :teacher="teacher"></TeacherCard>
     </div>
     <p v-else>아직 즐겨찾기한 강사가 없습니다.</p>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import TeacherCard from '../Search/TeacherCard.vue';


export default {
  components: {
    TeacherCard,
  },
  computed: {
    ...mapGetters([
      'loginid',
      'name',
      'email',
      'userProfileImage',
      'gender',
      'favoriteTeachers',
    ]),
  },
  methods: {
    ...mapActions([
      'getUser',
      'getFavoriteTeachers',

    ]),
  },
  created() {
    this.getUser();
    this.getFavoriteTeachers();
  }
};
</script>

<style scoped>

.user-image {
  width: 180px;
  height: 220px;
}

.myprofile-container {
  display: flex;
  flex-direction: column;
  justify-content: center; 
  align-items: center; 

}

.myprofile {
  width: 800px;
  display: flex;
  flex-direction: column;
  border: 1px solid black;
  padding: 10px;
  align-items: center;
  border-radius:10px;
}

.top-section {
  display: flex;
  width: 100%;
  justify-content: center;
}

.img-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.teacher-image {
  width: 170px;
  height: 220px;
  object-fit: cover;
}

.info-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  gap: 30px;
  width: 100%;
  margin-left: 20px;
}

.id-container,
.email-container,
.name-container,
.gender-container {
  padding: 10px;
  display: flex;
  flex-direction: column;
}

.info-field {
  border: 1px solid black;
  padding: 10px;
  margin-top: 5px;
}

.button {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 20px;
}

.button button {
  width: 100px;
}
</style>