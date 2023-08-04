import { apiTeacherProfileCreate, apiTeacherProfileUpdate } from "@/api/profiles.js";

export default {
  postTeacherProfileCreate(context, data) {
    apiTeacherProfileCreate(context, data);
  },
  async updateUserType({ commit }, userType) {   
    commit('SET_USER_TYPE', userType);
  },
  putTeacherProfileUpdate(context, data) {
    apiTeacherProfileUpdate(context,data);
  },
};
