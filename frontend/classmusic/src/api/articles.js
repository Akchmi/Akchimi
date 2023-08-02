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

function apiGetArticledetail(context, articleId) {
  axios
    .get(`/articles/${articleId}`)
    .then(({ data }) => {
      context.commit("SET_ARTICLE_DETAIL", data);
      console.log(data);
    })
    .catch((error) => {
      console.error("GET 요청 에러 : ", error);
    });
}

function apiArticleupdate(context, data) {
  console.log(
    JSON.stringify({
      title: data.title,
      content: data.content,
      file: data.file,
      userId: data.userId,
    })
  );
  axios
    .put(
      `/articles/${data.articleId}`,
      JSON.stringify({
        title: data.title,
        content: data.content,
        file: data.file,
        userId: data.userId,
      })
    )
    .then(({ data }) => {
      console.log(data);
      console.log("axios then까지 됨");
      // context.commit("GET_PAGENO", data);
    })
    .catch((error) => {
      console.error("PUT 요청 에러 : ", error);
    });
}

export {
  apiGetArticlelist,
  apiGetPageno,
  apiGetArticledetail,
  apiArticleupdate,
};
