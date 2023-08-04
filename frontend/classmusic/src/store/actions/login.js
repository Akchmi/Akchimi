import { apiLogin, apiFindId, apiFindPw } from "@/api/auth.js";
import { HttpStatusCode } from "axios";

export default {
  async login({ commit }, loginInfo) {
    const response = await apiLogin(loginInfo);
    const { status, data } = response;
    console.log(`로그인 요청 결과 : ${status}`);
    console.log(data);
    if (status == HttpStatusCode.Ok) {
      // 로그인 성공 시 vuex common에 저장
      commit("SET_TOKEN", data, { root: true });
    } else if (status == HttpStatusCode.Unauthorized) {
      alert("비밀번호가 일치하지 않습니다.");
      throw response;
    } else if (status == HttpStatusCode.NotFound) {
      alert("아이디가 존재하지 않습니다.");
      throw response;
    }
  },

  async findId({ commit }, namemail) {
    try {
      const { data } = await apiFindId(namemail);
      console.log(data);
      console.log(namemail);
      commit("SET_FINDID_ERROR", null);
    } catch (error) {
      console.log(error.response.data);
      commit("SET_FINDID_ERROR", "정보가 잘못되었습니다.");
    }
  },

  async findPw({ commit }, idemail) {
    try {
      const { data } = await apiFindPw(idemail);
      console.log(data);
      commit("SET_FINDPW_ERROR", null);
    } catch (error) {
      commit("SET_FINDPW_ERROR", "정보가 잘못되었습니다.");
      console.log(error.response.data);
    }
  },
};
