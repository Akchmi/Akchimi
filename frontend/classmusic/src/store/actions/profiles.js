import axios from "@/api/axios";
// import utils from "@/common/utils";

export default {
  getUser({ commit, state }) {
    axios
      .get(`users/${state.id}`)
      .then((response) => {
        commit("SET_USER", response.data);
        commit("SET_FAVORITE_TEACHERS", response.data.favoriteTeachers);
      })
      .catch((error) => {
        console.error(error);
      });
  },
  updateUser({ commit, state }, userData) {
    axios
      .put(`users/${state.id}`, userData)
      .then((response) => {
        commit("SET_USER", response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  },
  deleteUser({ commit, state }) {
    axios
      .delete(`users/${state.id}`)
      .then(() => {
        commit("SET_USER", {
          id: "",
          name: "",
          email: "",
          userProfileImage: "",
          type: 0,
          createdAt: null,
          gender: 0,
        });
      })
      .catch((error) => {
        console.error(error);
      });
  },
  // getFavoriteTeachers({commit,state}) {
  //   axios.get(`users/${state.id}/like`).then((response) => {
  //     commit('SET_FAVORITE_TEACHERS', response.data);
  //   }).catch((error) => {
  //     console.error(error)
  //   })
  // }

  // createTeacherProfile({commit}, teacherProfile) {
  //   try{
  //     const response = await axios.post(`teachers/`).then(() => {
  //       commit('SET_TEACHER')
  //     })
  //   }
  // }
};
