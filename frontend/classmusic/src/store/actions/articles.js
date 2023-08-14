import {
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
} from "@/api/articles";

export default {
  // 필요 기능
  //자유게시글 리스트 조회
  getArticlelist(context, params) {
    apiGetArticlePageno(context, params.searchType, params.keyword);
    apiGetArticlelist(
      context,
      params.searchType,
      params.keyword,
      params.pageNo,
      params.sortType
    );
  },

  //자유게시글 작성
  getArticledetail(context, articleId) {
    apiGetArticledetail(context, articleId);
  },

  //자유게시글 수정
  putArticleupdate(context, data) {
    console.log("자유수정", context, data);
    apiArticleupdate(context, data);
  },
  //자유게시글 작성
  postArticleCreate(context, data) {
    const response = apiArticlecreate(context, data);
    if(response) {
      context.commit("SAVE_ARTICLEID",response)
      console.log('세아액', response)
    }
    return response;
  },
  //자유게시글 삭제
  deleteArticleDelete(context, articleId) {
    apiArticledelete(context, articleId);
  },

  //자유게시글 댓글 조회
  getArticlecomment(context, articleId) {
    apiGetArticlecomment(context, articleId);
  },

  //자유게시글 댓글 작성
  postArticleComment(context, data) {
    apiPostArticleComment(context, data);
  },
  //자유게시글 댓글 수정
  putArticleComment(context, data) {
    apiCommentUpdate(context, data);
  },
  //자유게시글 댓글 삭제
  deleteComment(context, data) {
    apiCommentdelete(context, data);
  },
  //첨부파일?
};
