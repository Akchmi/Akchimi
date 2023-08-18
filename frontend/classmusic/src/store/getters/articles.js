export default {
  getArticleEndPageNo(state) {
    return state.articles.endPageno;
  },
  getArticleList(state) {
    return state.articles.articleList;
  },
  getArticleDetail(state) {
    return state.articles.articleDetail;
  },
  getArticleCreateId(state) {
    return state.articles.articleCreateId;
  },

  getCommentList(state) {
    return state.articles.articleCommentsList;
  },
  getAricleId(state) {
    return state.articles.articleId;
  },

  getArticleNowPage(state) {
    return state.articles.nowPage;
  },
};
