import axios from "@/api/axios";


async function apiGetUserInfo(id) {
  try {
    const response = await axios.get(`/users/${id}`);     
    return response.data
  } catch (error) {
    console.log(error);
    return error
  }
}

async function apiChangePw(id, newPassword) {
  try {
    const response = await axios.put(`/users/${id}/password`,  { password: newPassword })
    return response.data
  } catch (error) {
    console.log(error);
    return error
  }
}

async function apiDeleteUser(id) {
  try {
    const response = await axios.delete(`/users/${id}`)
    return response.data
  } catch (error) {
    console.log(error)
    return error
  }
}

async function apiLikeTeacher(id) {
  try{
    const response = await axios.get(`/users/${id}/like`);
    return response.data
  } catch (error) {
    console.log(error);
    return error
  }
}

async function apiDeleteLIkeTeacher(likeId) {
  try{
    const response = await axios.delete(`users/like/${likeId}`)
    return response.data
  } catch(error) {
    console.log(error)
    return error
  }
}

export { apiGetUserInfo, apiLikeTeacher , apiDeleteLIkeTeacher, apiChangePw, apiDeleteUser}