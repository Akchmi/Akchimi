<template>
  <div>
    <div class="container">
      <div class="signup__container">
        <h1>회원가입</h1>
        <div class="login__content">
          <div>
            <p>
              <input type="text" placeholder="이름" v-model="name" />
            </p>
          </div>
          <div>
            <p>
              <input type="text" placeholder="아이디" v-model="id" />
            </p>
          </div>
          <div>
            <p>
              <input type="text" placeholder="비밀번호" v-model="pw" />
            </p>
          </div>
          <div>
            <p>
              <input type="text" placeholder="비밀번호 확인" v-model="pw2" />
            </p>
          </div>
          <div>
            <p class="pwCheck" v-if="pw == pw2">비밀번호가 일치합니다.</p>
            <p v-else>비밀번호가 일치하지 않습니다.</p>
          </div>
          <div>
            <p>
              <input type="text" placeholder="이메일" v-model="email" />
            </p>
          </div>
          <div>
            <button>회원가입</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      name: "",
      id: "",
      pw: "",
      pw2: "",
      email: "",
    };
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/login.scss";
</style>
