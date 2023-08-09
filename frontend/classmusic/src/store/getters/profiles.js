// import axios from "@/common/axios";

export default {
  getUserId: state => state.userId,
  getUserInfo: state => state.userInfo,
  getLikeTeacherList(state) {
    return state.profiles.liketeachers;
  }



};
