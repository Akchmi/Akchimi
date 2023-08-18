export default {
  // setToken( {commit}, token) {
  //   commit('SET_TOKEN', token)
  // }
  changeNavPage(context, nextpage) {
    context.state.common.nowNavPage = nextpage;
  },
};
