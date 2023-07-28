<template>
  <div class="container">
    <div v-if="showmode == 'login'">
      <!-- 로그인 창-->
      <div class="login__container">
        <h1>로그인</h1>
        <div class="login__content">
          <div>
            <p><input type="text" placeholder="아이디" v-model="id" /></p>
          </div>
          <div>
            <p><input type="text" placeholder="비밀번호" v-model="pw" /></p>
          </div>
          <div>
            <button>로그인</button>
            <button>
              <router-link to="/login/signup">회원가입</router-link>
            </button>
          </div>
          <div>
            <button @click="showIdFinder">아이디 찾기</button>
            <button @click="showPwFinder">비밀번호 찾기</button>
          </div>
        </div>
      </div>
    </div>
    <div v-if="showmode == 'id'">
      <div class="login__container">
        <h1>아이디 찾기</h1>
        <div class="login__content">
          <div>
            <p>가입한 이름을 알려주세요.</p>
            <p>
              <input type="text" placeholder="이름" v-model="registered_name" />
            </p>
          </div>
          <div>
            <p>가입한 이메일을 알려주세요.</p>
            <p>
              <input
                type="text"
                placeholder="이메일"
                v-model="registered_email"
              />
            </p>
          </div>
          <div>
            <button @click="showLogin">로그인</button>
            <button>아이디 찾기</button>
          </div>
        </div>
      </div>
    </div>
    <div v-if="showmode == 'pw'">
      <div class="login__container">
        <h1>비밀번호 찾기</h1>
        <div class="login__content">
          <div>
            <p>가입한 아이디를 알려주세요.</p>
            <p>
              <input type="text" placeholder="아이디" v-model="registered_id" />
            </p>
          </div>
          <div>
            <p>가입한 이메일을 알려주세요.</p>
            <p>
              <input
                type="text"
                placeholder="이메일"
                v-model="registered_email"
              />
            </p>
          </div>
          <div>
            <button @click="showLogin">로그인</button>
            <button>비밀번호 찾기</button>
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
      id: "",
      pw: "",
      registered_id: "",
      registered_email: "",
      registered_name: "",
      showmode: "login",
    };
  },
  setup() {},
  methods: {
    showLogin() {
      this.showmode = "login";
    },
    showIdFinder() {
      this.showmode = "id";
    },
    showPwFinder() {
      this.showmode = "pw";
    },
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/login.scss";
</style>
