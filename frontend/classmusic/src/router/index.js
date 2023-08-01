import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import NoticeView from "../views/NoticeView.vue";
import ArticleView from "../views/ArticleView.vue";
import LectureView from "../views/LectureView.vue";
import SearchView from "../views/SearchView.vue";
import ProfileView from "../views/ProfileView.vue";
import LoginView from "../views/LoginView.vue";
import Chatview from "../views/ChatView.vue";
import LivemeetingView from "../views/LivemeetingView.vue";

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
import TeacherReview from "../components/Profile/TeacherReview.vue";

const routes = [
  {
    path: "/",
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
      },
      {
        path: "/notice/:id",
        name: "noticedetail",
        component: NoticeDetail,
      },
      {
        path: "/notice/update",
        name: "noticeupdate",
        component: NoticeUpdate,
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
      },
      {
        path: "/article/detail",
        name: "/articledetail",
        component: ArticleDetail,
      },
      {
        path: "/article/update",
        name: "/articleupdate",
        component: ArticleUpdate,
      },
    ],
  },
  {
    path: "/lecture",
    name: "lecture",
    component: LectureView,
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
      },
      {
        path: "/profile/teacherProfile",
        name: "teacherProfile",
        component: TeacherProfile,
      },
      {
        path: "/profile/teacherprofileupdate",
        name: "teacherprofileupdate",
        component: TeacherProfileUpdate,
      },
      {
        path: "/profile/teacherreview",
        name: "teacherreview",
        component: TeacherReview,
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
    path: "/chat",
    name: "chat",
    component: Chatview,
  },
  {
    path: "/livemeeting",
    name: "livemeeting",
    component: LivemeetingView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
