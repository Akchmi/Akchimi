import axios from "@/common/axios";
// import utils from "@/common/utils";

export default {
  actions: {
    // 로그인 사용자 프로필 조회
    fetchProfile({ commit }) {
      axios.get('/api/user/profile')
        .then(response => {
          commit('SET_PROFILE', response.data);
        })
        .catch(error => {
          console.log(error);
        });
    },
  
    // 회원 정보 수정
    updateProfile({ commit }, profileData) {
      axios.put('/api/user/profile', profileData)
        .then(response => {
          commit('SET_PROFILE', response.data);
        })
        .catch(error => {
          console.log(error);
        });
    },
  
    // 회원 탈퇴
    deleteUser({ commit }) {
      axios.delete('/api/user/profile')
        .then(() => {
          commit('LOGOUT');
        })
        .catch(error => {
          console.log(error);
        });
    },
  
    // 비밀번호 변경
    changePassword({ commit }, passwordData) {
      axios.post('/api/user/password', passwordData)
        .then(response => {
          commit('SET_PROFILE', response.data);
        })
        .catch(error => {
          console.log(error);
        });
    },
  
    // 즐겨찾기 목록 조회
    fetchFavorites({ commit }) {
      axios.get('/api/user/favorites')
        .then(response => {
          commit('SET_FAVORITES', response.data);
        })
        .catch(error => {
          console.log(error);
        });
    },
  
    // 즐겨찾기 삭제
    deleteFavorite({ commit }, favoriteId) {
      axios.delete(`/api/user/favorites/${favoriteId}`)
        .then(() => {
          commit('REMOVE_FAVORITE', favoriteId);
        })
        .catch(error => {
          console.log(error);
        });
    },

  },
  
};
