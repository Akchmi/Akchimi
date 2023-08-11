import {
  apiSearchTeachers,
  apiPostMatchingCreate,
  apiGetTeacherList,
} from "@/api/search";

export default {
  // 필요 기능

  //강사 목록 조회(처음)
  getTeacherList(context, data) {
    apiGetTeacherList(context, data);
  },

  searchTeacher(context) {
    apiGetTeacherList(context, context.state.search.searchParams);
  },

  //강사 목록 조회(검색)
  // searchTeacher({ commit, state }) {
  //   console.log("searchTeacher actions called");
  //   commit("SET_SEARCHPARAMS_PAGE", 1);
  //   apiSearchTeachers(
  //     state.search.searchParams,
  //     ({ data }) => {
  //       console.log("teachers : ", data);
  //       commit("REPLACE_TEACHER_LIST", data);
  //       commit("INCREASE_SEARCHPARAMS_PAGE");
  //     },
  //     (error) => {
  //       console.log(error);
  //     }
  //   );
  // },

  searchMoreTeacher({ commit, state }) {
    console.log("searchMoreTeacher : ", state.search.searchParams);

    apiSearchTeachers(
      state.search.searchParams,
      ({ data }) => {
        console.log("teachers : ", data);
        commit("ADD_TEACHER_LIST", data);
        commit("INCREASE_SEARCHPARAMS_PAGE");
      },
      (error) => {
        console.log(error);
      }
    );
  },

  commitCareer({ commit }, career) {
    console.log("ACTIONS : 경력 필터 수정", career);

    commit("SET_SEARCHPARAMS_START_CAREER", career[0]);
    commit("SET_SEARCHPARAMS_END_CAREER", career[1]);
  },

  commitCost({ commit }, cost) {
    console.log("ACTIONS : 비용 필터 수정", cost);

    commit("SET_SEARCHPARAMS_START_COST", cost[0]);
    commit("SET_SEARCHPARAMS_END_COST", cost[1]);
  },

  commitTime({ commit }, time) {
    console.log("ACTIONS : 시간 필터 수정", time);

    commit("SET_SEARCHPARAMS_START_TIME", time[0]);
    commit("SET_SEARCHPARAMS_END_TIME", time[1]);
  },

  commitClassDay({ commit }, classDay) {
    console.log("ACTIONS : 요일 필터 수정", classDay.toString(2));

    commit("SET_SEARCHPARAMS_CLASS_DAY", classDay);
  },

  commitInstrument({ commit }, instrument) {
    console.log("ACTIONS : 악기 종류 수정", instrument);

    commit("SET_SEARCHPARAMS_INSTRUMENT", instrument);
  },

  commitGender({ commit }, gender) {
    console.log("ACTIONS : 성별 수정", gender);

    commit("SET_SEARCHPARAMS_GENDER", gender);
  },

  commitOrderBy({ commit }, orderBy) {
    console.log("ACTIONS : 정렬 수정", orderBy);

    commit("SET_SEARCHPARAMS_ORDER_BY", orderBy);
  },

  commitKeyword({ commit }, keyword) {
    console.log("ACTIONS : 검색 키워드 수정", keyword);

    commit("SET_SEARCHPARAMS_KEYWORD", keyword);
  },

  postMachingCreate(context, data) {
    apiPostMatchingCreate(context, data);
  },
};
