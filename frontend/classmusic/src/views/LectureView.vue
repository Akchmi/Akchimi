<template>
  <div>
    lectureview <br />
    <button>
      <router-link to="/lecture/studentongoing">내 프로필</router-link>
    </button>
    |
    <button>
      <router-link to="/lecture/teacherongoing">강사 프로필</router-link>
    </button>
    <router-view></router-view>
  </div>
</template>

<script>
export default {};
</script>

<style></style>
