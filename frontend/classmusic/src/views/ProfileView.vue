<template>
  <div>
    profileview
    <br />
    <button>
      <router-link to="/profile/myprofile">내 프로필</router-link>
    </button>
    |
    <button>
      <router-link to="/profile/teacherprofile">강사 프로필</router-link>
    </button>
    <router-view></router-view>
  </div>
</template>

<script>
export default {};
</script>

<style></style>
