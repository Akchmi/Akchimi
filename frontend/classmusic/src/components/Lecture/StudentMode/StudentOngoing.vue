<template>
  <div>
    <div class="student-ongoing-container">
      <div class="student-ongoing">
        <div class="top-section">
          <img
            :src="image"
            alt="Teacher profile picture"
            class="teacher-image"
          />
          <div class="info-box">
            <h3>{{ name }}</h3>
            <div class="memo-box">
              <p>{{ savedMemo }}</p>
            </div>
          </div>
        </div>
        <div class="button-group">
          <button>채팅입장</button>
          <button @click="$router.push('/livemeeting')">강의실입장</button>
          <button @click="showInput = !showInput">
            {{ showInput ? "취소" : "메모하기" }}
          </button>
          <button>강의완료</button>
        </div>
      </div>
      <div v-if="showInput" class="input-field">
        <textarea v-model="memo" rows="10" cols="100"></textarea>
        <button @click="saveMemo">저장하기</button>
      </div>
    </div>
  </div>
</template>

<script>
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
    };
  },
  methods: {
    saveMemo() {
      this.savedMemo = this.memo;
      this.showInput = false;
    },
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
