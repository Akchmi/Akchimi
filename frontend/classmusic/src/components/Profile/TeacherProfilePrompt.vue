<template>
  <div class="teacher-registration-box">
    <p>아직 강사 등록을 하지 않으셨군요?</p>
    <button>
      <router-link to="/profile/teacherprofilecreate"
        >강사 등록하러 가기</router-link
      >
    </button>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.teacher-registration-box {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 40%;
  height: 60%;
  transform: translate(-60%, -20%);
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
