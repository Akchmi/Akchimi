import { apiTeacherProfileCreate, apiTeacherProfileUpdate, apiLikeTeacherUpdate } from "@/api/profiles.js";

export default {
  postTeacherProfileCreate(context, data) {
    apiTeacherProfileCreate(context, data);
  },
  async updateUserType({ commit }, userType) {   
    commit('SET_USER_TYPE', userType);
  },
  putTeacherProfileUpdate(context, data) {
    console.log('액션',context, data)
    apiTeacherProfileUpdate(context,data);
  },
  postLikeTeacherUpdate(context, data) {
    apiLikeTeacherUpdate(context, data)
  }
};
