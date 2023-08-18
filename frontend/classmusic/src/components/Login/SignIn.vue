<template>
  <div class="container">
    <div v-if="showmode == 'login'">
      <div class="login__container">
        <h1>로그인</h1>
        <div class="login__form">
          <input
            class="login__form__input"
            type="text"
            placeholder="아이디"
            v-model="loginid"
            @keyup.enter="loginclick"
          />
          <input
            class="login__form__input"
            type="password"
            placeholder="비밀번호"
            v-model="password"
            @keyup.enter="loginclick"
          />
          <div class="login__form__button">
            <button class="login-btn" @click="loginclick">로그인</button>
          </div>
          <div class="login__form__buttons">
            <span @click="movePage('/login/signup')">회원가입</span>
            <span>|</span>
            <span @click="showIdFinder">아이디 찾기</span>
            <span>|</span>
            <span @click="showPwFinder">비밀번호 찾기</span>
          </div>
        </div>
      </div>
    </div>
    <div v-if="showmode == 'id'">
      <div class="login__container">
        <h1>아이디 찾기</h1>
        <div class="login__form">
          <div class="login__form__label">
            <label for="findId-name">이름을 알려주세요.</label>
          </div>
          <input
            id="findId-name"
            type="text"
            placeholder="이름"
            v-model="name"
          />
          <div class="login__form__label">
            <label for="findId-email">가입한 이메일을 알려주세요.</label>
          </div>
          <input
            id="findId-email"
            type="text"
            placeholder="이메일"
            v-model="registered_email"
          />
          <div class="login__form__buttons">
            <button @click="showmode = 'login'">취소</button>
            <button @click="findUserId" :disabled="isLoading">
              아이디 찾기
            </button>
          </div>
        </div>
      </div>
    </div>
    <div v-if="showmode == 'pw'">
      <div class="login__container">
        <h1>비밀번호 찾기</h1>
        <div class="login__form">
          <div class="login__form__label">
            <label for="findPw-id">가입한 아이디를 알려주세요.</label>
          </div>
          <input
            id="findPw-id"
            type="text"
            placeholder="아이디"
            v-model="registered_id"
          />
          <div class="login__form__label">
            <label for="findPw-email">가입한 이메일을 알려주세요.</label>
          </div>
          <input
            id="findPw-email"
            type="text"
            placeholder="이메일"
            v-model="registered_email"
          />
          <div class="login__form__buttons">
            <button @click="showmode = 'login'">취소</button>
            <button @click="findPassword" :disabled="isLoading">
              비밀번호 찾기
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import axios from '@/api/axios.js';
import { mapActions } from "vuex";
// import { mapState } from 'vuex';

export default {
  data() {
    return {
      loginid: "",
      password: "",
      registered_id: "",
      registered_email: "",
      showmode: "login",
      name: "",
      isLoading: false,
    };
  },
  computed: {},

  methods: {
    ...mapActions(["login", "findId", "findPw"]),
    async loginclick() {
      try {
        await this.login({
          id: this.loginid,
          password: this.password,
        });
        this.$router.push("/main");
      } catch (error) {
        console.error(error);
        this.$router.push("/login/signin");
      }
    },
    movePage(path) {
      this.$router.push(path);
    },
    showIdFinder() {
      this.showmode = "id";
    },
    async findUserId() {
      this.isLoading = true;
      try {
        await this.findId({
          name: this.name,
          email: this.registered_email,
        });
        alert(`당신의 이메일로 메일을 전송했습니다.`);
        this.$router.push("/login/signin");
      } catch (error) {
        console.error(error);
        alert(`정보가 잘못 되었습니다.`);
        return;
      } finally {
        this.isLoading = false;
      }
    },
    showPwFinder() {
      this.showmode = "pw";
    },
    async findPassword() {
      this.isLoading = true;
      try {
        await this.findPw({
          id: this.registered_id,
          email: this.registered_email,
        });
        alert("임시 비밀번호를 발급했습니다. 이메일을 확인하세요.");
        this.$router.push("/login/signin");
      } catch (error) {
        console.error(error);
        alert(`정보가 잘못 되었습니다.`);
        return;
      } finally {
        this.isLoading = false;
      }
    },

    showLogin() {
      this.showmode = "login";
    },
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/login.scss";
</style>
