<template>
	<div class="out__container">
		<div class="container">
			<nav class="navbar">
				<div class="navbar__logo">
					<img
						class="mainLogo"
						src="@/assets/images/home/mainLogo2.png"
						@click="clickLogo"
						alt=""
					/>
				</div>

				<ul class="navbar__menu">
					<li :class="{ navColor: isNoticeRoute }" @click="clickNotice">공지사항</li>
					<li :class="{ navColor: isArticleRoute }" @click="clickArticle">자유게시판</li>
					<li :class="{ navColor: isLectureRoute }" @click="clickLecture">강의실</li>
					<li :class="{ navColor: isSearchRoute }" @click="clickSearch">강사검색</li>
					<li
						:class="{ navColor: isProfileRoute }"
						v-if="isLoggedIn"
						@click="clickMypage"
					>
						마이페이지
					</li>
					<div v-if="isLoggedIn" class="alarm-container">
						<img
							class="alarmLogo"
							src="@/assets/images/bell.png"
							@click="toggleDropdown"
						/>
						<div
							v-if="alarmCount"
							class="notification-count"
							@click.stop="toggleDropdown"
						>
							{{ alarmCount }}
						</div>
						<div v-if="showDropdown" class="dropdown">
							<ul>
								<li
									v-for="alarm in alarms"
									:key="alarm.id"
									@click="navigateByType(alarm.type)"
									class="alarm__message"
								>
									{{ alarm.message }}
									<div>
										{{ toLocalTimeStamp(alarm.time).substr(0, 10) }}
										<button @click.stop="deleteAlarm(alarm.eventId)">
											확인
										</button>
									</div>
								</li>
								<li
									class="alarm__message alarm__message__nope"
									v-if="alarms.length === 0"
								>
									알림이 없습니다.
								</li>
							</ul>
						</div>
					</div>
				</ul>
				<ul class="navbar__menu">
					<li v-if="isLoggedIn" @click="logOut">로그아웃</li>
					<li :class="{ navColor: isLoginRoute }" v-else @click="clickLogin">로그인</li>
				</ul>
			</nav>
		</div>
	</div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
import { apiEventAlarm, apiDeleteEventAlarm } from "@/api/auth.js";
import utils from "@/common/utils";
import router from "@/router";

export default {
	data() {
		return {
			showDropdown: false,
			alarms: [],
			routeMap: {
				1: "/lecture/studentongoing",
				2: "/lecture/studentwaiting",
				3: "/lecture/studentfinish",
				4: "/lecture/teacherongoing",
				5: "/lecture/teacherwaiting",
				6: "/lecture/teacherfinish",
				7: "/profile/myprofile",
			},
		};
	},
	computed: {
		...mapGetters({ isLoggedIn: "getIsLogin" }),
		...mapGetters({ nowNavPage: "getNowNavPage" }),
		isNoticeRoute() {
			return this.$route.path.includes("/notice");
		},

		isArticleRoute() {
			return this.$route.path.includes("/article");
		},

		isLectureRoute() {
			return this.$route.path.includes("/lecture");
		},

		isSearchRoute() {
			return this.$route.path.includes("/search");
		},
		isLoginRoute() {
			return this.$route.path.includes("/login");
		},
		isProfileRoute() {
			return this.$route.path.includes("/profile");
		},
		alarmCount() {
			if (this.alarms.length > 9) return "+";
			return this.alarms.length;
		},
	},

	mounted() {
		this.fetchAlarms();
	},

	setup() {
		const vuexState = JSON.parse(localStorage.getItem("vuex"));
		let userId = null;
		if (vuexState != null) {
			userId = vuexState.common.userId;
		}
		return { userId };
	},

	methods: {
		toLocalTimeStamp(unixTimeStamp) {
			return utils.unixTimeStampToLocalTimeStamp(unixTimeStamp);
		},

		async deleteAlarm(eventId) {
			try {
				await apiDeleteEventAlarm(eventId);
				this.alarms = this.alarms.filter((alarm) => alarm.id !== eventId);
				router.go(0);
			} catch (error) {
				console.log("Error deleting alarm:", error);
			}
		},

		navigateByType(type) {
			const routePath = this.routeMap[type];

			if (routePath) {
				this.$router.push(routePath);
			} else {
				console.error("Invalid type or no route defined for type:", type);
			}
		},

		async fetchAlarms() {
			try {
				const response = await apiEventAlarm(this.userId);
				if (response && response.data) {
					this.alarms = response.data.reverse();
				}
			} catch (error) {
				console.log("Error fetching alarms:", error);
			}
		},

		logOut() {
			this.$store.commit("LOGOUT");
			this.changeNavPage("main");
			this.$router.push("/main");
		},

		toggleDropdown() {
			this.showDropdown = !this.showDropdown;
		},

		...mapActions([
			"commitInstrument",
			"commitGender",
			"commitKeyword",
			"commitClassDay",
			"commitCost",
			"commitTime",
			"commitCareer",
			"searchTeacher",
			"commitOrderBy",
			"changeNavPage",
		]),

		clickLogo() {
			this.changeNavPage("main");
			this.$router.push("/main");
		},
		clickNotice() {
			this.changeNavPage("notice");
			this.$router.push("/notice/list");
		},
		clickArticle() {
			this.changeNavPage("article");
			this.$router.push("/article/list");
		},
		clickLecture() {
			if (!this.$store.getters.getIsLogin) {
				alert("로그인이 필요한 서비스입니다.");
				this.changeNavPage("login");
				this.$router.push("/login/signin");
				return;
			}
			this.changeNavPage("lecture");
			this.$router.push("/lecture/studentongoing");
		},
		clickSearch() {
			this.changeNavPage("search");
			this.commitInstrument("악기종류");
			this.commitGender("");
			this.commitKeyword("");
			this.commitClassDay("1111111");
			this.commitCost([0, 100]);
			this.commitTime([0, 100]);
			this.commitCareer([0, 100]);
			this.commitOrderBy("최신순");
			this.searchTeacher();

			this.$router.push("/search");
		},
		clickMypage() {
			this.changeNavPage("mypage");
			this.$router.push("/profile/myprofile");
		},
		clickLogin() {
			this.changeNavPage("login");
			this.$router.push("/login/signin");
		},
	},
};
</script>

<style lang="scss" scoped>
.notification-count {
	position: absolute;
	top: 3px;
	right: 0;
	background-color: red;
	color: white;
	width: 16px;
	height: 16px;
	display: flex;
	justify-content: center;
	align-items: center;
	border-radius: 50%;
	font-size: 14px;
	line-height: 1;
	border: 2px solid white;
}

.dropdown {
	position: absolute;
	background-color: #f9f9f9;
	width: 300px;
	max-height: 500px;
	overflow-y: auto;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.3);
	z-index: 9999;
	top: 100%;
	left: 50%;
	transform: translateX(-50%);
	border-bottom-left-radius: 5px;
	border-bottom-right-radius: 5px;
}

.dropdown ul {
	list-style-type: none;
}

.dropdown li {
	padding: 8px 12px;
}

.dropdown li:hover {
	// background-color: #ddd;
	background-color: #dddddd;
}

.navbar {
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.navbar__logo {
	padding-left: 0;
}

.navbar__menu {
	list-style: none;
	display: flex;
	position: relative;
}

.navbar__menu li {
	padding: 8px 30px;
}

.container {
	width: 1200px;
}

.out__container {
	display: flex;
	justify-content: center;
}

li {
	cursor: pointer;
	margin: 10px;
	font-size: 20px;
}

li:hover {
	cursor: pointer;
	/* font-size: 22px;
	margin: 6.7px; */
	color: #edd9b7;
}

.mainLogo {
	height: 70px;
	cursor: pointer;
}

.navColor {
	color: #edd9b7;
}

.alarm-container {
	position: relative;
	display: flex;
	justify-content: center;
	align-items: center;
}

.alarmLogo {
	width: 30px;
}

.alarmLogo:hover {
	cursor: pointer;
}

.alarm__message {
	font-size: 16px;
	padding: 5px 10px !important;
	margin: 5px;
	height: fit-content;
	border-radius: 5px;
	display: flex;
	flex-direction: column;
	align-items: left;
	justify-content: center;
	min-height: 80px;

	div {
		width: 100%;
		display: flex;
		flex-direction: row;
		align-items: center;
		justify-content: space-between;
		margin-top: 5px;
	}
}
.alarm__message:hover {
	color: black;
}
.alarm__message__nope {
	font-size: 20px;
	align-items: center;
}
</style>
