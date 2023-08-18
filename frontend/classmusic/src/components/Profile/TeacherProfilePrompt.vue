<template>
  <div class="teacher-registration">
    <div class="teacher-registration-box">
      <h3 class="teacher-registration-message">
        아직 강사 등록을 하지 않으셨군요?
      </h3>
      <button @click="this.$router.push('/profile/teacherprofilecreate')">
        강사 등록하기
      </button>
      <br>
      <button @click="this.$router.push('/profile/myprofile')">
        다음에 등록하기
      </button>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.teacher-registration {
  display: flex;
  align-items: center;
  justify-content: center;
}

.teacher-registration-message {
  margin-bottom: 20px;
}

.teacher-registration-box {
  width: 800px;
  height: 300px;
  border: 1.4px solid #edd9b7;
  border-radius: 0.25rem;
  padding: 20px;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
</style>
