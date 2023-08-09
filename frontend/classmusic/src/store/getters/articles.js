export default {
  getArticleEndPageNo(state) {
    console.log("엔드페이지 게터스 실행", state.articles.endPageno);
    return state.articles.endPageno;
  },
  getArticleList(state) {
    console.log("리스트 게터스 실행", state.articles.articleList);
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
};
