export default {
  getModeNumber(state) {
    return state.articles.modeNumber;
  },
  getWaitingList(state) {
    return state.articles.waitingList;
  },
  getMatchingList(state) {
    return state.articles.matchingList;
  },
  getFinishedList(state) {
    return state.articles.finishedList;
  },
};
