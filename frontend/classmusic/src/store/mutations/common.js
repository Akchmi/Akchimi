export default {
  SET_TOKEN(state, data) {
    console.log(data);
    state.common.accessToken = data.accessToken;
    state.common.isLogin = true;
    state.common.userId = data.userId;
    state.common.id = data.id;
    state.common.userType = data.type;
  },
};
