<template>
    <div class="teacher-registration-box">
      <p >아직 강사 등록을 하지 않으셨군요?</p>
      <button>
        <router-link to="/profile/teacherprofilecreate">강사 프로필</router-link>
      </button>

    </div>
  </template>
  
  <script>

  
  export default {
  }

  </script>
  
  <style scoped>
  .teacher-registration-box {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 400px;
    height: 300px;
    transform: translate(-50%, -50%);
    border: 1px solid #ccc;
    padding: 20px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }
  </style>
  