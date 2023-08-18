import axios from "@/api/axios";

async function apiLogin(params) {
  try {
    const response = await axios.post(`/auth/login`, JSON.stringify(params));
    return response;
  } catch (error) {
    console.log("api login error : " + error);
    return error.response;
  }
}

async function apiFindId(params) {
  try {
    const response = await axios.post("/auth/find-id", JSON.stringify(params));
    return response;
  } catch (error) {
    console.error(error.response.data);
  }
}

async function apiFindPw(params) {
  try {
    const response = await axios.put(
      "/auth/temporary-password",
      JSON.stringify(params)
    );
    return response;
  } catch (error) {
    console.error(error.response.data);
  }
}

async function apiCheckId(params) {
  const url = `/auth/check-id?id=${params}`;
  console.log("URL:", url);
  try {
    const response = await axios.get(`/auth/check-id?id=${params}`);

    return response;
  } catch (error) {
    console.error(error.response.data);
  }
}

async function apiCheckEmail(email) {
  // const url = `/auth/check-email?email=${email}`;
  // console.log("URL:", url);
  try {
    const response = await axios.get(`/auth/check-email?email=${email}`);

    return response;
  } catch (error) {
    console.error(error.response.data);
  }
}

async function apiRegister(params) {
  try {
    const response = await axios.post("/auth/sign-up", params);

    return response;
  } catch (error) {
    console.error(error.response.data);
  }
}

async function apiEventAlarm(userId) {
  try {
    const response = await axios.get(`/event/${userId}`);

    return response;
  } catch (error) {
    console.log(error);
  }
}

async function apiDeleteEventAlarm(eventId) {
  try {
    await axios.delete(`/event/${eventId}`);
  } catch (error) {
    console.log(error);
  }
}

export {
  apiLogin,
  apiCheckId,
  apiFindId,
  apiFindPw,
  apiRegister,
  apiCheckEmail,
  apiEventAlarm,
  apiDeleteEventAlarm,
};
