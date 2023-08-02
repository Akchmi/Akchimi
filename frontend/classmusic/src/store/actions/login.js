import { apiLogin,  apiFindId, apiFindPw,   } from "@/api/auth.js";

export default {
  async login({commit}, loginInfo) {    
    try {
      const { data } = await apiLogin(loginInfo);
      console.log(data);
      commit('SET_TOKEN', data.accessToken, {root: true});
    } catch (error) {
      console.log(error.response.data);
      throw error;
    }        
  },

  async findId({commit}, namemail) {
    try {
      const { data } = await apiFindId(namemail);
      console.log(data);
      console.log(namemail)
      commit('SET_FINDID_ERROR', null);      
    } catch (error) {
      console.log(error.response.data);
      commit('SET_FINDID_ERROR','정보가 잘못되었습니다.');    
    }
  },

  async findPw({commit}, idemail) {
    try {
      const { data } = await apiFindPw(idemail);
      console.log(data)
      commit('SET_FINDPW_ERROR', null)
    } catch (error) {
      commit('SET_FINDPW_ERROR', '정보가 잘못되었습니다.')
      console.log(error.response.data)
    }
  },  


};