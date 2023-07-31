<template>
  <div class="teacher-profile-container">
    <div class="teacher-profile">
      <div class="top-section">
        <div class="img-container">
          <img :src="image" alt="Teacher profile picture" class="teacher-image" />
          <button>이미지 수정</button>
        </div>
        <div class="info-container">
          <div class="name-container">
            <label for="name" class="name-label">강사 이름</label>
            <input id="name" class="name-input" type="text" placeholder="이름을 입력해주세요" />
          </div>
          <div class="teacher-profile-update-container">
            <div class="left-field">
              <div class="input-field">
                <label for="instrument">악기 : </label>
                <select id="instrument" v-model="selectedInstrument">
                  <option v-for="(instrument, index) in instruments" :value="instrument" :key="index">{{ instrument }}</option>
                </select>
                <div v-for="(instrument, index) in selectedInstruments" :key="index">
                  {{ instrument }}
                </div>
              </div>
              <div class="input-field">
                <label for="years">경력 : </label>
                <input id="years" type="number" min="1" />
                년
              </div>
              <div class="input-field">
                <label for="cost">시간당 비용 : </label>
                <input id="cost" type="number" min="0" />
                만원
              </div>
            </div>
            <div class="right-field">
              <div class="input-field">
                <label>요일:</label>
                <div class="days-container" v-for="(day, index) in days" :key="index">
                  <input type="checkbox" :id="day" v-model="selectedDays" :value="day">
                  <label :for="day">{{ day }}</label>
                </div>
              </div>
              <div class="input-field">
                <label for="start">시작 시간 :</label>
                <input id="start" type="time" v-model="startTime" />
              </div>
              <div class="input-field">
                <label for="end">종료 시간 :</label>
                <input id="end" type="time" v-model="endTime" />
              </div>
              <div class="input-field">
                <p>가능 시간 : {{ selectedDays.join(', ') }} {{ startTime }} ~ {{ endTime }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="teacher-details">
        <h2>자기 소개</h2>
        <div class="description-box">
          <textarea class="description-input" v-model="description" placeholder="자기자신을 잘 소개할 수 있는 문구를 작성해주세요.\n 휴대전화 번호 공개를 권장하지 않습니다."></textarea>
        </div>
      </div>
      <div class="attach-container">
        <div>
          <button>첨부 파일 추가</button>
        </div>
        첨부파일 추가 시 보여줄 공간
      </div>
      <div class="save-button">
        <button>
          <router-link to="/profile/teacherprofile">저장</router-link>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    image: { type: String, default: "https://via.placeholder.com/280" },
  
  },
  data() {
    return {
      selectedInstrument: null,
      instruments: ['피아노', '기타', '드럼', '바이올린', '트럼펫'],
      selectedInstruments: [],
      days: ['월', '화', '수', '목', '금', '토', '일'],
      selectedDays: [],
      startTime: '',
      endTime: '',
      description: "",
    };
  },
  watch: {
    selectedInstrument(newVal) {
      if (newVal && !this.selectedInstruments.includes(newVal)) {
        this.selectedInstruments.push(newVal);
      }
    }
  }
};
</script>

<style scoped>

.save-button {
    display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;

}
.description-input {
  width: 100%; 
  border: 1px solid black;
  padding: 10px;
  height: auto; 
  min-height: 6em;
  overflow: auto; 
  overflow-x: hidden;
  line-height:1.2em;
  margin-bottom:20px;
  border-radius:10px;
  
}

.description-box {
  border: 1px solid black;
  padding: 10px;
  height: auto;
  min-height: 6em;
  overflow: auto;
  overflow-x :hidden;
  line-height:1.2em;
  margin-bottom:20px;
  border-radius:10px;
}

.description-box textarea {
  width: 100%;
  height: 100%;
  border: none;
  outline: none;
  resize: none;
}


.teacher-profile-container {
  display: flex;
  flex-direction: column;
  justify-content: center; 
  align-items: center; 
}

.teacher-profile {
  box-sizing: border-box;
  position: relative;
  display: flex;
  flex-direction: column;
  width: 800px;
  height: 800px;
  border: 1px solid black;
  margin-bottom: 20px;
  border-radius: 8px;
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
}

.img-container {
  display: flex;
  flex-direction: column;
  align-items: center;  
}

.info-container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
}

.name-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.name-label {
  flex: 1;
}

.name-input {
  flex: 4;
  width: 200px; 
}

.teacher-profile-update-container {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

.left-field, .right-field {
  display: flex;
  flex-direction: column;
  width: 45%;
}

.input-field {
  margin: 10px 0;
}

.teacher-profile-update-container {
  border: 1px solid black;
  padding: 10px;
  height: auto; 
  min-height: 6em;
  max-height: auto; 
  overflow: auto; 
  line-height: 1.2em; 
  margin-bottom: 20px;
  border-radius: 10px;  
}

.days-container {
  display: inline-block;
  margin-right: 10px;
}



</style>
