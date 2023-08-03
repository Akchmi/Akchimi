<template>
  <div>
    <div v-for="lecture in lectures" :key="lecture.order">
      <div class="student-ongoing-container">
        <div class="student-ongoing">
          <div class="top-section">
            <img
              :src="lecture.userProfileImage"
              alt="Teacher profile picture"
              class="teacher-image"
            />
            <div class="info-box">
              <h3>{{ lecture.name }}</h3>
              <div class="memo-box">
                <!-- <p>{{ savedMemo }}</p> -->
                <p>{{ lecture.memo }}</p>
              </div>
            </div>
          </div>
          <div class="button-group">
            <button>채팅입장</button>
            <button @click="moveLivemeeting">강의실입장</button>
            <button @click="showInput = !showInput">
              {{ showInput ? "취소" : "메모하기" }}
            </button>
            <button @click="completeLecture(lecture.contactId)">강의완료</button>
          </div>
        </div>
        <div v-if="showInput" class="input-field">
          <textarea v-model="lecture.memo" rows="10" cols="100"></textarea>
          <button @click="saveMemo(lecture.contactId, lecture.memo)">저장하기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/api/axios";
import { useStore } from 'vuex';

export default {
  props: {
    image: { type: String, default: "https://via.placeholder.com/280" },
    name: { type: String, default: "박한샘" },
  },
  data() {
    return {
      memo: "",
      savedMemo: "",
      showInput: false,
      lectures: [],
    };
  },
  methods: {
    saveMemo(contactId, lmemo) {
      this.savedMemo=lmemo;
      this.showInput = false;
      axios.put(`http://localhost:8080/contacts/${contactId}/memo`
      ,JSON.stringify({"type": 0, "memo": this.savedMemo}))
      
    },
    moveLivemeeting() {
      this.$router.push("/livemeeting");
    },
    completeLecture(contactId){
      console.log(contactId);
      const checkComplete = confirm(" 강의 종료?");
      if(checkComplete){
        axios
        .put(`http://localhost:8080/contacts/${contactId}/state`
        ,JSON.stringify({"state" : 2}))
        .then((data) =>{
          console.log(data);
          
        })
      }
    }
  },
  created(){
    const store=useStore();
    const userId = store.getters.getUserId;
    console.log(this.$data);
    axios
      .get(`http://localhost:8080/contacts?id=${userId}&state=1&type=0`)
      .then((data) => {
        this.$data.lectures=data.data;
      })
  },
};
</script>

<style scoped>
.student-ongoing-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.student-ongoing {
  box-sizing: border-box;
  position: relative;
  display: flex;
  flex-direction: column;
  width: 800px;
  height: 300px;
  border: 1px solid black;
  margin-bottom: 20px;
  border-radius: 10px;
  overflow: hidden;
  padding: 20px;
}

.top-section {
  display: flex;
}

.teacher-image {
  width: 170px;
  height: 220px;
  object-fit: cover;
  margin-right: 1em;
}

.info-box {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  height: 200px;
  flex-grow: 1;
}

.memo-box {
}

.button-group {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}

.input-field {
  width: 800px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  margin-bottom: 20px;
}
</style>
