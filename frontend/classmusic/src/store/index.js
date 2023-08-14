import { createStore } from "vuex";

import actions from "./actions";
import getters from "./getters";
import mutations from "./mutations";

import articles from "./state/articles";
import chats from "./state/chats";
import lectures from "./state/lectures";
import liveMeetings from "./state/liveMeetings";
import login from "./state/login";
import notices from "./state/notices";
import profiles from "./state/profiles";
import search from "./state/search";
import common from "./state/common";

import createPersistedState from "vuex-persistedstate";

export default createStore({
  plugins: [
    createPersistedState({
      paths: ["common"],
    }),
  ],

  state: {
    articles,
    chats,
    lectures,
    liveMeetings,
    login,
    notices,
    profiles,
    search,
    common,
  },
  getters,
  mutations,
  actions,
});
