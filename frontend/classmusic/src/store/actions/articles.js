import {
  apiGetArticlelist,
  apiGetPageno,
  apiGetArticledetail,
  apiArticleupdate,
  apiArticlecreate,
  apiArticledelete,
} from "@/api/articles";

export default {
  // 필요 기능
  //자유게시글 리스트 조회
  getArticlelist(context, params) {
    apiGetArticlelist(
      context,
      params.searchType,
      params.keyword,
      params.pageNo,
      params.sortType
    );
    apiGetPageno(context, params.searchType, params.keyword);
  },

  //자유게시글 작성
  getArticledetail(context, articleId) {
    apiGetArticledetail(context, articleId);
  },

  //자유게시글 수정
  putArticleupdate(context, data) {
    apiArticleupdate(context, data);
  },
  //자유게시글 작성
  postArticleCreate(context, data) {
    apiArticlecreate(context, data);
  },
  //자유게시글 삭제
  deleteArticleDelete(context, articleId) {
    apiArticledelete(context, articleId);
  },
  //자유게시글 댓글 조회
  //자유게시글 댓글 작성
  //자유게시글 댓글 수정
  //자유게시글 댓글 삭제
  //첨부파일?
};
