export default {
  getTotalTeachers(state) {
    return state.articles.totalTeachers;
  },
  getListSize(state) {
    return state.articles.listSize;
  },
  getListNumber(state) {
    return state.articles.listNumber;
  },
  getListTotalCount(state) {
    return state.articles.listTotalCount;
  },
  getTeacherList(state) {
    return state.articles.teacherList;
  },
};
