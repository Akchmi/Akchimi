<template>
  <div>
    studentongoing
    <br />
    <button>
      <router-link to="/lecture/studentwaiting">대기중</router-link>
    </button>
    <br />
    <button>
      <router-link to="/lecture/studentfinish">완료</router-link>
    </button>
  </div>
</template>

<script>
export default {};
</script>

<style></style>
