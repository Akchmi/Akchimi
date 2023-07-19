import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import NoticeView from "../views/NoticeView.vue";
import ArticleView from "../views/ArticleView.vue";
import LectureView from "../views/LectureView.vue";
import SearchView from "../views/SearchView.vue";
import ProfileView from "../views/ProfileView.vue";
import LoginView from "../views/LoginView.vue";

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
  },
  {
    path: "/article",
    name: "article",
    component: ArticleView,
  },
  {
    path: "/lecture",
    name: "lecture",
    component: LectureView,
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
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
