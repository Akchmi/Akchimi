import axios from "@/api/axios";
import router from "@/router/index";

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
  const articleId = data.articleId;
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
    .then(() => {
      router.push(`/article/${articleId}`);
    })
    .catch((error) => {
      console.error("PUT 요청 에러 : ", error);
    });
}

function apiArticlecreate(context, data) {
  axios
    .post(`/articles`, data)
    .then(({ data }) => {
      context.commit("SET_CREATE_ID", data);
    })
    .catch((error) => {
      console.error("POST 요청 에러 : ", error);
    });
}

function apiArticledelete(context, articleId) {
  axios
    .delete(`/articles/${articleId}`)
    .then(() => {
      router.push(`/article/list`);
    })
    .catch((error) => {
      console.error("POST 요청 에러 : ", error);
    });
}

export {
  apiGetArticlelist,
  apiGetPageno,
  apiGetArticledetail,
  apiArticleupdate,
  apiArticlecreate,
  apiArticledelete,
};
