import {
  apiGetLectureList,
  apiPostLecture
} from "@/api/lectures";

export default {
  // 필요 기능
  //매칭리스트 조회
  getLectureList(context, params){
    console.log(params)
    apiGetLectureList(context,params);
  },
  apiPostLecture(context, params){
    apiPostLecture(context, params);
  }
  //매칭 삭제
  //매칭 상태 수정
  //매칭 메모 수정
  //매칭 순서 수정
  //매칭 생성
  //강의실 입장
};
