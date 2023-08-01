import { apiGetArticlelist } from "@/api/articles";

export default {
  // 필요 기능
  //자유게시글 리스트 조회
  getArticlelist(context, params) {
    apiGetArticlelist(context, params);
  },

  //자유게시글 작성
  //자유게시글 상세
  //자유게시글 수정
  //자유게시글 삭제
  //자유게시글 댓글 조회
  //자유게시글 댓글 작성
  //자유게시글 댓글 수정
  //자유게시글 댓글 삭제
  //첨부파일?
};
