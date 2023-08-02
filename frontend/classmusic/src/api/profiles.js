import axios from "@/api/axios";


async function apiGetUserInfo(id) {
  try {
    const response = await axios.get(`/users/${id}`);  
    console.log('api',response.data)
    return response.data
  } catch (error) {
    console.log(error);
    return error
  }
}


export { apiGetUserInfo, }