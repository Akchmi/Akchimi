import axios from "axios";

const api = axios.create({
  // baseURL: "https:i9a210.p.ssafy.io:3000/api",
  baseURL: "http://localhost:8080/api",

  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  (config) => {
    console.log("axios bearer token 설정");
    const vuexStorage = JSON.parse(localStorage.getItem("vuex"));

    if (vuexStorage != null) {
      const accessToken = vuexStorage.common.accessToken;
      console.log("access token : " + accessToken);
      if (accessToken != null)
        config.headers.Authorization = `Bearer ${accessToken}`;
    }

    return config;
  },
  (error) => {
    console.log("axios bearer 설정 에러");
    return Promise.reject(error);
  }
);

export default api;
