export default {
  REPLACE_TEACHER_LIST(state, data) {
    state.search.teacherList = data;
  },
  ADD_TEACHER_LIST(state, data) {
    state.search.teacherList.push(...data);
  },
  SET_LECTURE_LIST(state, data) {
    state.search.teacherList = data;
  },

  // searchParams
  SET_SEARCHPARAMS_START_CAREER(state, data) {
    state.search.searchParams.startCareer = data;
  },
  SET_SEARCHPARAMS_END_CAREER(state, data) {
    state.search.searchParams.endCareer = data;
  },
  SET_SEARCHPARAMS_START_COST(state, data) {
    state.search.searchParams.startCost = data;
  },
  SET_SEARCHPARAMS_END_COST(state, data) {
    state.search.searchParams.endCost = data;
  },
  SET_SEARCHPARAMS_START_TIME(state, data) {
    state.search.searchParams.startTime = data;
  },
  SET_SEARCHPARAMS_END_TIME(state, data) {
    state.search.searchParams.endTime = data;
  },
  SET_SEARCHPARAMS_CLASS_DAY(state, data) {
    state.search.searchParams.classDay = data;
  },
  SET_SEARCHPARAMS_INSTRUMENT(state, data) {
    state.search.searchParams.instrument = data;
  },
  SET_SEARCHPARAMS_KEYWORD(state, data) {
    state.search.searchParams.keyword = data;
  },
  SET_SEARCHPARAMS_ORDER_BY(state, data) {
    state.search.searchParams.orderBy = data;
  },
  SET_SEARCHPARAMS_PAGE(state, data) {
    state.search.searchParams.page = data;
  },
  SET_SEARCHPARAMS_GENDER(state, data) {
    state.search.searchParams.gender = data;
  },

  INCREASE_SEARCHPARAMS_PAGE(state) {
    state.search.searchParams.page += 1;
  },
};
