<template>
  <div>
    <button @click="$router.push('/notice/list')">목록으로</button>
    <hr />
    <div class="container">
      <h2>글제목입니다아아아아아</h2>
      <br />
    </div>

    <div class="container">
      <p>조회수:</p>
      <p>작성자:</p>
    </div>
    <hr />

    <div>
      <h3>글 내용</h3>
    </div>
    <hr />

    <h3>첨부파일</h3>

    <button @click="$router.push('/notice/update')">수정</button>
    <button>삭제</button>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/notice.scss";
</style>
