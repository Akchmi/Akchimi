<template>
  <div>
    <div>
      <nav class="navbar">
        <div class="navbar__logo">
          <i class="fas fa-blog"></i>
          <a @click="$router.push('/')">Akchimi</a>
        </div>

        <ul class="navbar__menu">
          <li @click="$router.push('/notice/list')">공지사항</li>
          <li @click="$router.push('/article/list')">자유게시판</li>
          <li @click="$router.push('/lecture/studentwaiting')">강의실</li>
          <li @click="$router.push('/search')">강사검색</li>
        </ul>

        <ul class="navbar__menu">
          <li @click="$router.push('/login/signin')">로그인</li>
          <li @click="$router.push('/profile/myprofile')">마이페이지</li>
        </ul>
      </nav>
    </div>
    <br />
    <SearchBar @searchTeacher="onSearchTeacher"> </SearchBar>
    <hr />
    <SearchList> </SearchList>
  </div>
</template>

<script>
import SearchBar from "../components/Search/SearchBar.vue";
import SearchList from "../components/Search/SearchList.vue";
import { apiSearchTeacher } from "@/api/search";

export default {
  name: "SearchView",
  data() {
    return {
      teachers: [],
    }
  },
  components: {
    SearchBar,
    SearchList,
  },
  methods: {
    onSearchTeacher(params){
      apiSearchTeacher(params, 
      ({ data})=>{
        console.log(data);
        this.teachers = data;
      }, (error)=>{
        alert(error);
      });
    }
  },
};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/templates/common.scss";
</style>
