<template>
  <div class="container">
    <div v-if="showmode == 'login'">
      <div class="login__container">
        <h1>로그인</h1>
        <div class="login__content">
          <div>
            <p><input type="text" placeholder="아이디" v-model="loginid" /></p>
          </div>
          <div>
            <p><input type="password" placeholder="비밀번호" v-model="password" /></p>
          </div>
          <div>
            <button @click="loginclick">로그인</button>
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
            <p>이름을 알려주세요.</p>
            <p>
              <input type="text" placeholder="이름" v-model="name" />
            </p>
          </div>
            <p>가입한 이메일을 알려주세요.</p>
            <p>
              <input type="text" placeholder="이메일" v-model="registered_email" />
            </p>
          <div>
            <button @click="showLogin">로그인</button>
            <button @click="findId">아이디 찾기</button>
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
              <input type="text" placeholder="이메일" v-model="registered_email" />
            </p>
          </div>
          <div>
            <button @click="showLogin">로그인</button>
            <button @click="findPassword">비밀번호 찾기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from '@/api/axios.js';
import { mapActions } from 'vuex';

export default {
  data() {
    return {
      loginid: "",
      password: "",
      registered_id: "",
      registered_email: "",
      showmode: "login",
      name: "",
    };
  },
  methods: {
    ...mapActions(['login', 'setToken']),
    async loginclick() {
      try {
        this.login({
          id: this.loginid,
          password: this.password,
        })

        this.$router.push('/');
        // this.setToken()
        
      } catch (error) {
        console.error(error);
        this.$router.push('login/signin');
      }
    },
    showIdFinder() {
      this.showmode = "id";
    },
    async findId() {
      try {
        const response = await axios.post('/auth/find-id', {
          email: this.registered_email,
        });

        alert(`당신의 아이디는 ${response.data.id} 입니다.`);
      } catch (error) {
        console.error(error);
      }
    },
    showPwFinder() {
      this.showmode = "pw";
    },
    async findPassword() {
      try {
        await axios.put('/auth/temporary-password', {
          id: this.registered_id,
          email: this.registered_email,
        });

        alert('임시 비밀번호를 발급했습니다. 이메일을 확인하세요.');
      } catch (error) {
        console.error(error);
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
