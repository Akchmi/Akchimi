export default {
  SET_FINDID_ERROR(state, message) {
    state.findIdError = message;
  },
  SET_FINDPW_ERROR(state, message) {
    state.findPwError = message;
  },
  LOGOUT(state) {
    state.common.isLogin = false;
    state.common.accessToken = null;
    state.common.userId = null;
    state.common.id = null;
    state.common.userType = null;
    state.common.name = null;
    state.common.teacherId = null;
  },
};
