export default {
  getIsLogin(state) {
    return state.articles.isLogin;
  },
  getIsLoginModalOpen(state) {
    return state.articles.isLoginModalOpen;
  },
  getLoginInfo(state) {
    return state.articles.loginInfo;
  },
  getUserInfo(state) {
    return state.articles.userInfo;
  },
  getTeacherInfo(state) {
    return state.articles.teacherInfo;
  },
};
