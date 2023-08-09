import {
  apiTeacherProfileCreate,
  apiTeacherProfileUpdate,
  apiLikeTeacherUpdate,
} from "@/api/profiles.js";

export default {
  async postTeacherProfileCreate(context, data) {
    const result = await apiTeacherProfileCreate(context, data);
    return result;
  },
  async updateUserType({ commit }, userType) {
    commit("SET_USER_TYPE", userType);
  },

  putTeacherProfileUpdate(context, data) {
    console.log("액션", context, data);
    apiTeacherProfileUpdate(context, data);
  },

  postLikeTeacherUpdate(context, data) {
    apiLikeTeacherUpdate(context, data);
  },
};
