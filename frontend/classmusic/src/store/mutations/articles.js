import router from "@/router/index";

export default {
  SET_ARTICLE_LIST(state, data) {
    state.articles.articleList = data;
  },
  GET_ARTICLE_PAGENO(state, data) {
    state.articles.endPageno = data;
  },
  SET_ARTICLE_DETAIL(state, data) {
    state.articles.articleDetail = data;
  },
  SET_ARTICLE_COMMENTS(state, data) {
    state.articles.articleCommentsList = data;
  },
  SET_CREATE_ID(state, data) {
    state.articles.articleCreateId = data;
    router.push(`/article/${data}`);
  },
  UPDATE_ARTICLE_COMMENTS(state, data) {
    const commentsList = state.articles.articleCommentsList;

    for (let i = 0; i < commentsList.length; i++) {
      if (commentsList[i].commentId == data.commentId) {
        commentsList[i].content = data.content;
      }
    }
  },
  DELETE_ARTICLE_COMMENTS(state, commentId) {
    const commentsList = state.articles.articleCommentsList;

    for (let i = 0; i < commentsList.length; i++) {
      if (commentsList[i].commentId == commentId) {
        commentsList.splice(i, 1);
        return;
      }
    }
  },
};
