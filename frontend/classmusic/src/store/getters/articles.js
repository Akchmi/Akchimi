export default {
  getEndPageNo(state) {
    return state.articles.endPageno;
  },
  getArticleList(state) {
    return state.articles.articleList;
  },
  getArticleDetail(state) {
    return state.articles.articleDetail;
  },
  getCommentListTotalCount(state) {
    return state.articles.commentListTotalCount;
  },
  getCommentList(state) {
    return state.articles.commentList;
  },
  getCommentDetail(state) {
    return state.articles.commentDetail;
  },
};
