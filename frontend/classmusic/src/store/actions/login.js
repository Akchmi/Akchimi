export default {
  // 필요 기능
  //로그인
  //회원 등록
  //id 중복확인
  //임시 비밀번호 발급
  //아이디 찾기
  setToken( {commit, dispatch}, token) {
    commit('SET_TOKEN', token)
  }


};
