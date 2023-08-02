import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",

  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  (config) => {
    console.log("axios bearer token 설정");
    const common = JSON.parse(localStorage.getItem("vuex")).common;
    const accessToken = common.accessToken;
    console.log(accessToken);

    if (accessToken != null) config.headers.Authorization = `${accessToken}`;
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;
