import axios from "@/api/axios";

async function apiSearchTeacher(params) {
  try {
    const response = await axios.post(`/teachers`, JSON.stringify(params));
    return response;
  } catch (error) {
    console.log("api search teacher error : " + error);
    return error.response;
  }
}

export { apiSearchTeacher }