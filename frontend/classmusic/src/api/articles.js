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
      if (data.length == 0) {
        alert("검색된 게시글이 없습니다.");
        return;
      }
      context.commit("SET_ARTICLE_LIST", data);
    })
    .catch((error) => {
      console.error("GET 요청 에러 : ", error);
    });
}

function apiGetArticlePageno(context, searchType, keyword) {
  axios
    .get("/articles/endPageNo", {
      params: {
        searchType: searchType,
        keyword: keyword,
      },
    })
    .then(({ data }) => {
      context.commit("GET_ARTICLE_PAGENO", data);
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
      console.error("DELETE 요청 에러 : ", error);
    });
}

function apiGetArticlecomment(context, articleId) {
  axios
    .get(`/articles/${articleId}/comments`)
    .then(({ data }) => {
      context.commit("SET_ARTICLE_COMMENTS", data);
    })
    .catch((error) => {
      console.error("GET 요청 에러 : ", error);
    });
}

function apiPostArticleComment(context, data) {
  axios
    .post(`/articles/${data.articleId}/comments`, { content: data.content })
    .then(() => {
      apiGetArticlecomment(context, data.articleId);
    })
    .catch((error) => {
      console.error("POST 요청 에러 : ", error);
    });
}

function apiCommentUpdate(context, data) {
  const articleId = data.articleId;
  const commentId = data.commentId;
  axios
    .put(
      `/articles/${articleId}/comments/${commentId}`,
      JSON.stringify({
        content: data.content,
      })
    )
    .then(() => {
      context.commit("UPDATE_ARTICLE_COMMENTS", {
        commentId: commentId,
        content: data.content,
      });
    })
    .catch((error) => {
      console.error("PUT 요청 에러 : ", error);
    });
}

function apiCommentdelete(context, data) {
  const articleId = data.articleId;
  const commentId = data.commentId;
  axios
    .delete(`/articles/${articleId}/comments/${commentId}`)
    .then(() => {
      context.commit("DELETE_ARTICLE_COMMENTS", commentId);
    })
    .catch((error) => {
      console.error("POST 요청 에러 : ", error);
    });
}

export {
  apiGetArticlelist,
  apiGetArticlePageno,
  apiGetArticledetail,
  apiArticleupdate,
  apiArticlecreate,
  apiArticledelete,
  apiGetArticlecomment,
  apiPostArticleComment,
  apiCommentUpdate,
  apiCommentdelete,
};
