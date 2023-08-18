import articles from "./mutations/articles";
import chats from "./mutations/chats";
import lectures from "./mutations/lectures";
import liveMeetings from "./mutations/liveMeetings";
import login from "./mutations/login";
import notices from "./mutations/notices";
import profiles from "./mutations/profiles";
import search from "./mutations/search";
import common from "./mutations/common";

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
  ...common,
};
