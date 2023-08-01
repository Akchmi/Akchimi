import { apiGetNoticedetail, apiGetNoticelist } from "@/api/notice";

export default {
  //공지게시글 리스트 조회 &검색
  getNoticelist(context, params) {
    apiGetNoticelist(context, params);
  },

  //공지게시글 상세
  getNoticedetail(context, noticeId) {
    apiGetNoticedetail(context, noticeId);
  },

  //공지게시글 작성

  //공지게시글 수정
  //공지게시글 삭제
  //첨부파일?
};
