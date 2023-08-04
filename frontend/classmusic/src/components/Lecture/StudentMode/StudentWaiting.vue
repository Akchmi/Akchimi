<template>
  <div class="student-waiting-container">
    <div class="student-waiting">
      <div v-for="lecture in lectures" :key="lecture.order">
        <div class="list-box">
          <div class="info">
            <img :src="lecture.userProfileImage" alt="Teacher profile picture" class="teacher-image" />
            <span class="teacher-name">{{lecture.name}}</span>
          </div>
          <div class="button-group">
            <button>강사 정보보기</button>
            <button>채팅하기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import axios from 'axios';
// import { mapGetters  } from 'vuex';
// import { onMounted } from "vue";

export default {
  props: {
    image: { type: String, default:"https://via.placeholder.com/280"},
    name: { type: String, default: '박한샘'}
  },
  data(){
    return{
      lectures: [],
    }
  },
  // computed:{
  //   ...mapGetters({ lectureList : "getLectureList" }),
  // },
  created(){
    const store=useStore();
    const userId = store.getters.getUserId;
    console.log(this.$data);
    axios
      .get(`http://localhost:8080/contacts?id=${userId}&state=0&type=0`)
      .then((data) => {
        this.$data.lectures=data.data;
      })
  },
  // setup(){
  //   const store = useStore();
  //   const userId = store.getters.getUserId;
    
  //   onMounted(() => {
  //     const params = {
  //       id:userId,
  //       state: 0,
  //       type: 0
  //     }
  //     store.dispatch("getLectureList", params)
  //   });
  // },


};
</script>

<style scoped>
.student-waiting-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.student-waiting {
  box-sizing: border-box;
  position:relative;
  display:flex;
  flex-direction: column;
  width:800px;
  height:500px;
  border: 1px solid black;
  margin-bottom:20px;
  border-radius: 10px;
  overflow:hidden;
  padding:20px;
}

.list-box {
  flex-direction: row;
  width: 750px;
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border: 1px solid black;
  overflow: hidden;
  padding: 10px;
}

.info {
  display: flex;
  align-items: center;
}

.teacher-image {
  width: 30px;
  height: 30px;
  border-radius: 50%;
}

.teacher-name {
  margin-left: 10px;
}
</style>
