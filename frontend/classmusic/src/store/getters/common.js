export default {
  getUserId(state) {
    return state.common.userId;
  },
  getIsLogin(state) {
    return state.common.isLogin;
  },
  getId(state) {
    return state.common.id;
  },
  getUsertype(state) {
    return state.common.userType;
  },
  getUserName(state) {
    return state.common.userName;
  },
  getTeacherId(state) {
    return state.common.teacherId;
  },
  getNowNavPage(state) {
    return state.common.nowNavPage;
  },
};
