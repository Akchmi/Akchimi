<template>
  <div class="container">
    <div class="signup__container">
      <h1 style="margin-bottom: 50px">회원가입</h1>
      <form @submit.prevent="register" style="padding-left: 20px">
        <input type="text" v-model="loginid" placeholder="아이디" />
        <button @click.prevent="checkId">아이디 중복확인</button>
        <input type="password" v-model="password" placeholder="비밀번호" />
        <input
          type="password"
          v-model="confirmPassword"
          placeholder="비밀번호 확인"
        />
        <input type="text" v-model="name" placeholder="이름" />
        <input type="email" v-model="email" placeholder="이메일" />
        <br />
        <select v-model="gender">
          <option disabled value="">성별 선택</option>
          <option value="1">남자</option>
          <option value="2">여자</option>
        </select>
        <br />
        <div style="display: flex; justify-content: center; margin-top: 20px">
          <button type="submit">회원가입</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
// import axios from "@/api/axios.js"
import { apiCheckId, apiRegister } from "@/api/auth.js";
import { mapActions } from "vuex";
import router from "@/router";

export default {
  data() {
    return {
      loginid: "",
      password: "",
      confirmPassword: "",
      name: "",
      email: "",
      gender: "",
      isIdChecked: false,
      checkedId: "",
    };
  },
  methods: {
    ...mapActions(["setToken"]),
    async checkId() {
      this.checkedId = this.loginid;
      try {
        const { data } = await apiCheckId(this.loginid);
        if (data) {
          console.log(data);
          this.loginid = "";
          alert("이미 가입한 아이디입니다.");
          this.isIdChecked = false;
        } else {
          alert("회원가입이 가능한 아이디입니다.");
          this.isIdChecked = true;
        }
      } catch (error) {
        console.error(error);
      }
    },
    async register() {
      if (this.checkedId != this.loginid) {
        alert("중복체크한 아이디와 일치하지 않습니다.");
        return;
      }
      if (
        !this.loginid ||
        !this.password ||
        !this.name ||
        !this.email ||
        !this.gender
      ) {
        alert("모든 필드를 입력해주세요.");
        return;
      }

      if (!this.isIdChecked) {
        alert("아이디 중복확인을 해 주세요.");
        return;
      }

      if (this.password !== this.confirmPassword) {
        alert("비밀번호가 일치하지 않습니다.");
        this.password = "";
        this.confirmPassword = "";
        return;
      }

      try {
        const response = await apiRegister({
          id: this.loginid,
          password: this.password,
          name: this.name,
          email: this.email,
          gender: this.gender,
        });

        this.setToken(response.data.token);
        router.push("/login/signin");
      } catch (error) {
        console.error(error);
      }
    },
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/login.scss";
</style>
