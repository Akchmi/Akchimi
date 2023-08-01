import axios from "@/api/axios";

function apiGetArticlelist(context, params) {
  axios
    .get("/articles", {
      params: params,
    })
    .then(({ data }) => {
      context.commit("SET_ARTICLE_LIST", data);
      console.log(data);
    })
    .catch((error) => {
      console.error("GET 요청 에러 : ", error);
    });
}

export { apiGetArticlelist };
