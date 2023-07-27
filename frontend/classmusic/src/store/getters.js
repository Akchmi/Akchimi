import articles from "./getters/articles";
import chats from "./getters/chats";
import lectures from "./getters/lectures";
import liveMeetings from "./getters/liveMeetings";
import login from "./getters/login";
import notices from "./getters/notices";
import profiles from "./getters/profiles";
import search from "./getters/search";

export default {
  // 각 파일 만들어서 모듈화 사용
  ...articles,
  ...chats,
  ...lectures,
  ...liveMeetings,
  ...login,
  ...notices,
  ...profiles,
  ...search,
};
