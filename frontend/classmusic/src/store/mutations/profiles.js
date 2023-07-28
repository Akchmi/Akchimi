import http from "@/common/axios";
// import utils from "@/common/utils";

export default {
  async getMypage(context) {
    try {
      let { data } = await http.get(`users/${context.state.user_id}`)
      switch (data.resultCode) {
        case "SUCCESS":
            context.commit("SET_USER_INFO", data.data);
            break;
        case "FAIL":
            break;
    }
    } catch (err) {
        console.error(err);
    }
  }
};
  
  // 회원 정보
