<template>
  <div class="container">
    <div class="signup__container">
      <h1>회원가입</h1>
      <form class="signup__form" @submit.prevent="register">
        <div>
          <input
            class="signup__form__input"
            type="text"
            v-model="loginid"
            placeholder="아이디"
          />
          <button @click.prevent="checkId">중복확인</button>
        </div>
        <input
          class="signup__form__input"
          type="password"
          v-model="password"
          placeholder="비밀번호"
        />
        <input
          class="signup__form__input"
          type="password"
          v-model="confirmPassword"
          placeholder="비밀번호 확인"
        />
        <input
          class="signup__form__input"
          type="text"
          v-model="name"
          placeholder="이름"
        />
        <div>
          <input
            class="signup__form__input"
            type="email"
            v-model="email"
            placeholder="이메일"
          />
          <button @click.prevent="checkEmail">중복확인</button>
        </div>
        <br />

        <div class="signup__form__radios">
          <div>성별 :</div>
          <div>
            <input
              class="signup__form__radio"
              type="radio"
              name="gender"
              id="female"
              value="2"
              v-model="gender"
            />
            <label for="female">여성</label>
          </div>
          <div>
            <input
              class="signup__form__radio"
              type="radio"
              name="gender"
              id="male"
              value="1"
              v-model="gender"
            />
            <label for="male">남성</label>
          </div>
        </div>
        <!-- <select v-model="gender">
					<option disabled value="">성별 선택</option>
					<option value="1">남자</option>
					<option value="2">여자</option>
				</select> -->
        <br />
        <div class="signup__form__buttons">
          <button @click="this.$router.push('/login/signin')">취소</button>
          <button type="submit">회원가입</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { apiCheckId, apiCheckEmail, apiRegister } from "@/api/auth.js";
// import { mapActions } from "vuex";
import router from "@/router";

export default {
  data() {
    return {
      loginIdRegEx: /^[\w]+[\w0-9]{5,16}$/,
      emailRegEx: /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/,
      passwordRegEx: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,16}$/,
      loginid: "",
      password: "",
      confirmPassword: "",
      name: "",
      email: "",
      gender: null,
      isIdChecked: false,
      isEmailChecked: false,
      checkedId: "",
      checkedEmail: "",
    };
  },
  methods: {
    async checkId() {
      if (!this.loginIdRegEx.test(this.loginid)) {
        alert("아이디는 6~16자리의 영어 대소문자, 숫자여야 합니다.");
        return;
      }
      this.checkedId = this.loginid;
      try {
        const { data } = await apiCheckId(this.loginid);
        if (data) {
          // console.log(data);
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
    async checkEmail() {
      this.checkedEmail = this.email;
      if (!this.emailRegEx.test(this.email)) {
        alert("이메일 형식이 잘못 되었습니다.");
        return;
      }

      try {
        const { data } = await apiCheckEmail(this.email);
        if (data) {
          // console.log(data);
          this.email = "";
          alert("이미 가입한 이메일입니다.");
          this.isEmailChecked = false;
        } else {
          alert("사용가능한 이메일입니다.");
          this.isEmailChecked = true;
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
      if (!this.loginIdRegEx.test(this.loginid)) {
        alert("아이디는 6~16자리의 영어 대소문자, 숫자여야 합니다.");
        return;
      }
      if (!this.passwordRegEx.test(this.password)) {
        alert("비밀번호는 8~16자리의 영어 대소문자, 숫자여야 합니다.");
        return;
      }
      if (this.checkedEmail != this.email) {
        alert("중복체크한 이메일과 일치하지 않습니다.");
        return;
      }
      if (!this.emailRegEx.test(this.email)) {
        alert("이메일 형식이 잘못 되었습니다.");
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

        console.log(response);
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
