import axios from "@/api/axios";

function apiGetArticlelist(context, searchType, keyword, pageNo, sortType) {
  axios
    .get("/articles", {
      params: {
        searchType: searchType,
        keyword: keyword,
        pageNo: pageNo,
        sortType: sortType,
      },
    })
    .then(({ data }) => {
      context.commit("SET_ARTICLE_LIST", data);
    })
    .catch((error) => {
      console.error("GET 요청 에러 : ", error);
    });
}

function apiGetPageno(context, searchType, keyword) {
  axios
    .get("/articles/endPageNo", {
      params: {
        searchType: searchType,
        keyword: keyword,
      },
    })
    .then(({ data }) => {
      context.commit("GET_PAGENO", data);
    })
    .catch((error) => {
      console.error("GET 요청 에러 : ", error);
    });
}
export { apiGetArticlelist, apiGetPageno };
