<template>
	<div>
		<!-- 남이 보는 강사 프로필 -->
		<div class="teacher-profile-container">
			<div class="teacher-profile">
				<div class="top-section">
					<img
						:src="userProfileImage"
						alt="Teacher profile picture"
						class="teacher-image"
					/>
					<div class="teacher-name-gender-info">
						<button
							v-if="this.localteacherId == this.teacherId && this.visible == 1"
							class="active-info"
						>
							활성화
						</button>
						<button
							v-if="this.localteacherId == this.teacherId && this.visible == 0"
							class="active-info inactive-profile"
						>
							비활성화
						</button>

						<div class="name-gender-row">
							<div class="name-box">
								<h3 class="label">강사이름</h3>
								<span class="box">{{ name }}</span>
							</div>
							<div class="gender-box">
								<h3 class="label">성별</h3>
								<span class="box">{{ genderText }}</span>
							</div>
						</div>
						<div class="teacher-info-container">
							<h3 class="label">강사 정보</h3>
							<div class="teacher-info-box">
								<!-- <p class="info-box">악기 : {{ instrument }}</p> -->
								<span
									>악기 :
									<span class="inst" v-for="inst in instrument" :key="inst.index"
										>{{ inst }}
									</span>
								</span>
								<p class="info-box">경력 : {{ career }}년</p>
								<p class="info-box">가격 : 시간당 {{ cost }}만원</p>
								<p class="info-box">
									가능 요일 :
									{{ classDay !== undefined ? parseDays(classDay) : "" }}
								</p>
								<p class="info-box">
									가능 시간 : {{ startTime }}시 ~ {{ endTime }}시
								</p>
							</div>
						</div>
					</div>
				</div>
				<div class="teacher-details">
					<h3 class="label">강사 소개</h3>
					<div class="description-box">
						{{ introduce }}
					</div>
				</div>

				<div class="attach-file">
					<h3 class="label">첨부파일</h3>
					<div>
						<img
							v-for="(image, index) in attachedFiles"
							:src="image"
							:key="index"
							alt="Attached file"
							class="attach-image"
						/>
					</div>
				</div>
				<div class="button-group">
					<div v-if="Number(localteacherId) === Number(teacherId)">
						<button class="teacher-bottom-button" @click="goToTeacherProfileUpdate">
							강사 정보 수정
						</button>
						<button
							v-if="this.visible == 1"
							style="margin-left: 10px; width: 125px"
							@click="toggleTeacherProfile"
						>
							강사 정보 비활성화
						</button>
						<button
							v-if="this.visible == 0"
							style="margin-left: 10px; width: 125px"
							@click="toggleTeacherProfile"
						>
							강사 정보 활성화
						</button>
					</div>
					<div v-if="Number(localteacherId) != Number(teacherId) && userId">
						<button
							v-if="likeId === null"
							class="teacher-bottom-button"
							@click="likeTeacherUpdate"
						>
							강사 즐겨찾기
						</button>
						<button
							v-else
							class="teacher-bottom-button"
							@click="deleteLikeTeacher(likeId)"
						>
							즐겨찾기 취소
						</button>
						<button class="teacher-bottom-button" @click="registerLecture(teacherId)">
							강의 신청
						</button>
					</div>
				</div>
			</div>
			<div class="teacher-reviews">
				<div class="review-header">
					<h2 class="review-title">강사 리뷰</h2>
					
					<div class="avg-rating">
						평균 별점:
						<span v-for="star in Math.floor(avgRating)" :key="star">
							<i class="fa-solid fa-star" style="color: #ffdd00;"></i>
						</span>
						<span v-if="25<=avgRating*100%100 && avgRating*100%100<75">
							<i class="fa-solid fa-star-half" style="color: #ffdd00;"></i>
						</span>
						( {{ avgRating }} )
					</div>
				</div>
				<TeacherReview
					v-for="review in reviews"
					:key="review.review"
					:review="review"
					image="https://via.placeholder.com/280"
				/>
			</div>
		</div>
	</div>
</template>

<script>
import TeacherReview from "./TeacherReview.vue";
import {
	apiDetailTeacherInfo,
	apiGetReview,
	apiDeleteLIkeTeacher,
	apiToggleTeacherVisible,
} from "@/api/profiles.js";
import router from "@/router";
import { mapActions } from "vuex";
// import { useRoute } from "vue-router";
// import axios from "@/api/imageAxios.js";

export default {
	components: {
		TeacherReview,
	},
	data() {
		return {
			name: "",
			gender: "",
			userProfileImage: "",
			career: 0,
			cost: 0,
			introduce: "",
			startTime: 0,
			endTime: 0,
			classDay: "",
			instrument: "",
			attachedFiles: [],
			reviews: [],
			avgRating: 0,
			contactCnt: 0,
			visible: 0,
			userId: JSON.parse(localStorage.getItem("vuex")).common.userId,

			id: JSON.parse(localStorage.getItem("vuex")).common.id,
			teacherId: "",
			localteacherId: JSON.parse(localStorage.getItem("vuex")).common.teacherId,
			likeId: "",
		};
	},

	async created() {
		// const route = useRoute();
		const teacherId = this.$route.params.id;
		const res = await apiDetailTeacherInfo(teacherId);
		this.teacherId = teacherId;
		this.name = res.name;
		this.gender = res.gender;
		this.userProfileImage = res.userProfileImage;
		this.career = res.career;
		this.cost = res.cost;
		this.introduce = res.introduce;
		this.startTime = res.startTime;
		this.endTime = res.endTime;
		this.classDay = res.classDay;
    
		this.instrument = res.instruments.join(" ");
		// this.instrument = res.instruments;
		this.attachedFiles = res.images;
		this.avgRating = res.avgRating;
		this.contactCnt = res.contactCnt;
		this.likeId = res.likeId;
		this.visible = res.visible;
		this.getReview();
	},
	computed: {
		genderText() {
			return this.gender === 1 ? "남자" : "여자";
		},
	},
	methods: {
		...mapActions(["postLikeTeacherUpdate"]),

		goToTeacherProfileUpdate() {
			this.$router.push("/profile/TeacherProfileUpdate");
		},

		...mapActions(["postMachingCreate"]),
		registerLecture(teacherId) {
			this.postMachingCreate({
				teacherId: teacherId,
				studentId: this.userId,
				mode: "registerLecture",
			});
		},

		deleteLikeTeacher(likeId) {
			const data = {
				id: this.id,
				likeId: likeId,
			};
			apiDeleteLIkeTeacher(data)
				.then(() => {
					// this.liketeachers = this.liketeachers.filter(teacher => teacher.likeId !== likeId);
					router.go(0);
				})
				.catch((error) => {
					console.log(error);
				});
		},

		parseDays(classDay) {
			const days = ["월", "화", "수", "목", "금", "토", "일"];
			return classDay
				.toString(2)
				.padStart(7, "0")
				.split("")
				.reverse()
				.map((day, index) => (day === "1" ? days[index] : null))
				.filter(Boolean)
				.join(", ");
		},
		likeTeacherUpdate() {
			const data = {
				id: JSON.parse(localStorage.getItem("vuex")).common.id,
				teacherId: this.$route.params.id,
			};
			this.postLikeTeacherUpdate(data)
				.then(() => {
					alert("즐겨찾기에 성공하였습니다");
					this.$router.push(`/profile/myprofile`);
				})
				.catch((error) => {
					console.log("즐찾실패", error);
				});
		},
		async getReview() {
			try {
				const reviewData = await apiGetReview(this.teacherId);

				if (reviewData) {
					this.reviews = reviewData;
					console.log("리뷰", this.reviews);
				}
			} catch (error) {
				console.log("리뷰에러", error);
			}
		},

		// ...mapActions(["deleteTeacher"]),
		// deleteTeacherProfile() {
		//   this.deleteTeacher(this.teacherId);
		// },
		toggleTeacherProfile() {
			apiToggleTeacherVisible(this.teacherId);
			if (this.visible == 0) {
				this.visible = 1;
			} else {
				this.visible = 0;
			}
		},
	},
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/teacherprofile.scss";
.active-info {
	width: 75px;
	position: absolute;
	top: 5px;
	right: 5px;
	cursor: default;
}

.inactive-profile {
	background-color: transparent;
	border: solid #edd9b7 2px;
	color: #e6b96b;
}
</style>
