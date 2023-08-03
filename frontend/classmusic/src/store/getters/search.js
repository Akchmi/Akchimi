export default {
  // 강사 목록
  getTeacherList(state) {
    return state.search.teacherList;
  },

  // 검색 조건
  getSearchParams(state) {
    return state.search.searchParams;
  },

  getSearchParamsCareer(state) {
    return [
      state.search.searchParams.startCareer,
      state.search.searchParams.endCareer,
    ];
  },

  getSearchParamsCost(state) {
    return [
      state.search.searchParams.startCost,
      state.search.searchParams.endCost,
    ];
  },

  getSearchParamsTime(state) {
    return [
      state.search.searchParams.startTime,
      state.search.searchParams.endTime,
    ];
  },
};
