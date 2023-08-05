  import axios from "@/api/axios";

  async function apiGetUserInfo(id) {
    try {
      const response = await axios.get(`/users/${id}`);
      return response.data;
    } catch (error) {
      console.log(error);
      return error;
    }
  }

  async function apiChangePw(id, newPassword) {
    try {
      const response = await axios.put(`/users/${id}/password`, {
        password: newPassword,
      });
      return response.data;
    } catch (error) {
      console.log(error);
      return error;
    }
  }

  async function apiDeleteUser(id) {
    try {
      const response = await axios.delete(`/users/${id}`);
      return response.data;
    } catch (error) {
      console.log(error);
      return error;
    }
  }


  // function apiTeacherProfileCreate(context, data) {
  //   console.log(data);
  //   axios
  //     .post(`/teachers/`, data)
  //     .then(({ data }) => {
  //       console.log(data);
  //     })
  //     .catch((error) => {
  //       console.error("POST 요청 에러 : ", error);
  //     });
  // }

  function apiLikeTeacherUpdate(context, data) {
    const teacherId = data.teacherId
    console.log(333,data)
    axios
      .post(`/users/${teacherId}/like`, data)
      .then(({data}) => {
        console.log(11, data)
      })
      .catch((error) => {
        console.error("즐겨찾기에러", error)
      })
  }


  async function apiLikeTeacher(teacherId) {
    try {
      const response = await axios.get(`/users/${teacherId}/like`);
      return response.data;
    } catch (error) {
      console.log(error);
      return error;
    }
  }

  async function apiDeleteLIkeTeacher(likeId) {
    try {
      const response = await axios.delete(`users/like/${likeId}`);
      return response.data;
    } catch (error) {
      console.log(error);
      return error;
    }
  }

  async function apiDetailTeacherInfo(userId) {
    try {
      const response = await axios.get(`teachers/${userId}`);
      return response.data;
    } catch (error) {
      console.log(error);
      return error;
    }
  }

  function apiTeacherProfileCreate(context, data) {
    console.log(data);
    axios
      .post(`/teachers/`, data)
      .then(({ data }) => {
        console.log(data);
      })
      .catch((error) => {
        console.error("POST 요청 에러 : ", error);
      });
  }

  function apiTeacherProfileUpdate(context, data){
    const teacherId = data.teacherId
    console.log('수정은 ',data, teacherId)
    axios
      .put(`/teachers/${teacherId}`, data)
      .then(({data}) => {
        console.log('풋전송',data)
      })
      .catch((error) => {
        console.error(teacherId, "PUt 요청 에러 :", error)
      })
  }



  // async function apiTeacherProfileUpdate(userId, updateData) {
  //   try {
  //     const response = await axios.put(`/teachers/${userId}`, updateData);
  //     return response.data;
  //   } catch (error) {
  //     console.log(error);
  //     return error;
  //   }
  // }

  async function apiGetReview(teacherId) {
    try {
      const response = await axios.get(`/reveiws?teacher=${teacherId}`);
      return response.data;
    } catch (error) {
      console.log(error);
      return error;
    }
  }

  async function apiReviewCreate(data) {
    try {
      const response = await axios.post(`/reviews`, data);
      return response.data;
    } catch (error) {
      console.log(error);
      return error;
    }
  }

  async function apiGetMyReview(matchingId) {
    try {
      const response = await axios.get(
        `/reviews/myreview?contactId=${matchingId}`
      );
      return response.data;
    } catch (error) {
      console.log(error);
      return error;
    }
  }

  export {
    apiGetMyReview,
    apiReviewCreate,
    apiGetReview,
    apiTeacherProfileUpdate,
    apiTeacherProfileCreate,
    apiGetUserInfo,
    apiLikeTeacher,
    apiDeleteLIkeTeacher,
    apiChangePw,
    apiDeleteUser,
    apiDetailTeacherInfo,
    apiLikeTeacherUpdate
  };
