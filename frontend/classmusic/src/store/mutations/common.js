export default {
  SET_TOKEN(state, token){
    state.common.accessToken = token;
    state.common.isLogin = true;
    console.log(token)
  }

};
