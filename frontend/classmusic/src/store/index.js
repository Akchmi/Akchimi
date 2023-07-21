import { createStore } from "vuex";
import { notices } from "./modules/notices";
import { articles } from "./modules/articles";
import { chats } from "./modules/chats";
import { contacts } from "./modules/contacts";
import { reviews } from "./modules/reviews";
import { teachers } from "./modules/teachers";
import { users } from "./modules/users";

export default createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: { notices, articles, chats, contacts, reviews, teachers, users },
});
