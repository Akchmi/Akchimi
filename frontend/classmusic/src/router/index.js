import { createRouter, createWebHistory } from "vue-router";
import { useStore } from "vuex";
import HomeView from "../views/HomeView.vue";
import NoticeView from "../views/NoticeView.vue";
import ArticleView from "../views/ArticleView.vue";
import LectureView from "../views/LectureView.vue";
import SearchView from "../views/SearchView.vue";
import ProfileView from "../views/ProfileView.vue";
import LoginView from "../views/LoginView.vue";
import Chatview from "../views/ChatView.vue";
import LiveMeetingView from "../views/LivemeetingView.vue";

import NoticeList from "../components/Notice/NoticeList.vue";
import NoticeCreate from "../components/Notice/NoticeCreate.vue";
import NoticeDetail from "../components/Notice/NoticeDetail.vue";
import NoticeUpdate from "../components/Notice/NoticeUpdate.vue";

import ArticleList from "../components/Article/ArticleList.vue";
import ArticleCreate from "../components/Article/ArticleCreate.vue";
import ArticleDetail from "../components/Article/ArticleDetail.vue";
import ArticleUpdate from "../components/Article/ArticleUpdate.vue";

import StudentWaiting from "../components/Lecture/StudentMode/StudentWaiting.vue";
import StudentOngoing from "../components/Lecture/StudentMode/StudentOngoing.vue";
import StudentFinish from "../components/Lecture/StudentMode/StudentFinish.vue";
import TeacherWaiting from "../components/Lecture/TeacherMode/TeacherWaiting.vue";
import TeacherOngoing from "../components/Lecture/TeacherMode/TeacherOngoing.vue";
import TeacherFinish from "../components/Lecture/TeacherMode/TeacherFinish.vue";

import SignIn from "../components/Login/SignIn.vue";
import SignUp from "../components/Login/SignUp.vue";

import MyProfile from "../components/Profile/MyProfile.vue";
import TeacherProfile from "../components/Profile/TeacherProfile.vue";
import TeacherProfileUpdate from "../components/Profile/TeacherProfileUpdate.vue";
import TeacherProfileCreate from "../components/Profile/TeacherProfileCreate.vue";
import TeacherProfilePrompt from "../components/Profile/TeacherProfilePrompt.vue";
import TeacherReview from "../components/Profile/TeacherReview.vue";

import ErrorView from "@/views/ErrorView.vue";
import ErrorPage from "../components/common/ErrorPage.vue";

// 로그인한 유저만 통과시키는 navigation guard
const onlyLoginUser = (to, from, next) => {
	const isLogin = JSON.parse(localStorage.getItem("vuex")).common.isLogin;
	// 로그인 여부 확인
	if (isLogin) {
		next();
	} else {
		alert("로그인이 필요한 서비스입니다.");
		router.push("/login/signin");
	}
};

// 관리자 유저만 통과시키는 navigation guard
const onlyAdminUser = (to, from, next) => {
	const store = useStore();
	const userType = store.getters["common/getUsertype"];
	// vuex에서 로그인 여부 확인
	if (userType != null && userType === 2) {
		next();
	} else {
		alert("관리자만 이용가능한 서비스입니다.");
		router.push("/main");
	}
};

const routes = [
	{
		path: "/main",
		name: "home",
		component: HomeView,
	},
	{
		path: "/notice",
		name: "notice",
		component: NoticeView,
		children: [
			{
				path: "/notice/list",
				name: "noticelist",
				component: NoticeList,
			},
			{
				path: "/notice/create",
				name: "noticecreate",
				component: NoticeCreate,
				beforeEnter: onlyAdminUser,
			},
			{
				path: "/notice/:id",
				name: "noticedetail",
				component: NoticeDetail,
			},
			{
				path: "/notice/update/:id",
				name: "noticeupdate",
				component: NoticeUpdate,
				beforeEnter: onlyAdminUser,
			},
		],
	},
	{
		path: "/article",
		name: "article",
		component: ArticleView,
		children: [
			{
				path: "/article/list",
				name: "/articlelist",
				component: ArticleList,
			},
			{
				path: "/article/create",
				name: "/articlecreate",
				component: ArticleCreate,
				beforeEnter: onlyLoginUser,
			},
			{
				path: "/article/:id",
				name: "/articledetail",
				component: ArticleDetail,
			},
			{
				path: "/article/update/:id",
				name: "/articleupdate",
				component: ArticleUpdate,
				beforeEnter: onlyLoginUser,
			},
		],
	},
	{
		path: "/lecture",
		name: "lecture",
		component: LectureView,
		beforeEnter: onlyLoginUser,
		children: [
			{
				path: "/lecture/studentwaiting",
				name: "studentwaiting",
				component: StudentWaiting,
			},
			{
				path: "/lecture/studentongoing",
				name: "studentongoing",
				component: StudentOngoing,
			},
			{
				path: "/lecture/studentfinish",
				name: "studentfinish",
				component: StudentFinish,
			},
			{
				path: "/lecture/teacherwaiting",
				name: "teacherwaiting",
				component: TeacherWaiting,
			},
			{
				path: "/lecture/teacherongoing",
				name: "teacherongoing",
				component: TeacherOngoing,
			},
			{
				path: "/lecture/teacherfinish",
				name: "teacherfinish",
				component: TeacherFinish,
			},
		],
	},
	{
		path: "/search",
		name: "search",
		component: SearchView,
		beforeEnter(to, from, next) {
			if (to.path == "/search" && from.path == "/") {
				const store = useStore();
				store.dispatch("commitInstrument", "악기종류");
				store.dispatch("commitGender", "");
				store.dispatch("commitKeyword", "");
				store.dispatch("commitClassDay", "1111111");
				store.dispatch("commitCost", [0, 100]);
				store.dispatch("commitTime", [0, 100]);
				store.dispatch("commitCareer", [0, 100]);
				store.dispatch("commitOrderBy", "최신순");
				store.dispatch("searchTeacher");
				next();
			} else {
				next();
			}
		},
	},

	{
		path: "/profile",
		name: "profile",
		component: ProfileView,
		children: [
			{
				path: "/profile/myprofile",
				name: "myprofile",
				component: MyProfile,
				beforeEnter: onlyLoginUser,
			},
			{
				path: "/profile/teacherProfile/:id",
				name: "teacherProfile",
				component: TeacherProfile,
			},
			{
				path: "/profile/teacherprofileupdate",
				name: "teacherprofileupdate",
				component: TeacherProfileUpdate,
				beforeEnter: onlyLoginUser,
			},
			{
				path: "/profile/teacherreview",
				name: "teacherreview",
				component: TeacherReview,
			},
			{
				path: "/profile/teacherprofilecreate",
				name: "teacherprofilecreate",
				component: TeacherProfileCreate,
				beforeEnter: onlyLoginUser,
			},
			{
				path: "/profile/teacherprofileprompt",
				name: "teacherprofileprompt",
				component: TeacherProfilePrompt,
				beforeEnter: onlyLoginUser,
			},
		],
	},
	{
		path: "/login",
		name: "login",
		component: LoginView,
		children: [
			{
				path: "/login/signin",
				name: "signin",
				component: SignIn,
			},
			{
				path: "/login/signup",
				name: "signup",
				component: SignUp,
			},
		],
	},
	{
		path: "/chats/:lectureId",
		name: "chat",
		component: Chatview,
		beforeEnter: onlyLoginUser,
	},
	{
		path: "/livemeeting/:lectureId",
		name: "livemeeting",
		component: LiveMeetingView,
		beforeEnter: onlyLoginUser,
	},
	{
		path: "/",
		redirect: "/main",
	},
	{
		path: "/error",
		name: "errors",
		component: ErrorView,
		children: [
			{
				path: "/error/404",
				name: "not-found",
				component: ErrorPage,
			},
		],
	},
	{
		path: "/:pathMatch(.*)",
		redirect: "/error/404",
	},
];

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes,
});

export default router;
