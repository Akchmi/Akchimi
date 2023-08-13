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
				<input class="signup__form__input" type="text" v-model="name" placeholder="이름" />
				<input
					class="signup__form__input"
					type="email"
					v-model="email"
					placeholder="이메일"
				/>
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
							checked
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
			gender: "2",
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
			if (!this.loginid || !this.password || !this.name || !this.email || !this.gender) {
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
