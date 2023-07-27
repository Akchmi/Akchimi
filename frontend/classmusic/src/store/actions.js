import articles from "./actions/articles";
import chats from "./actions/chats";
import lectures from "./actions/lectures";
import liveMeetings from "./actions/liveMeetings";
import login from "./actions/login";
import notices from "./actions/notices";
import profiles from "./actions/profiles";
import search from "./actions/search";

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
