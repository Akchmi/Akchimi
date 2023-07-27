<template>
  <div class="container">
    <div class="create__container">
      <div>
        <button @click="$router.push('/article/list')">목록으로</button>
        <hr />
        <div>
          <h2>글제목</h2>
        </div>

        <div>
          <p>조회수:</p>
          <p>작성자:</p>
        </div>
        <hr />

        <div>
          <h3>글 내용</h3>
        </div>
        <hr />

        <h3>첨부파일</h3>

        <button @click="$router.push('/article/update')">수정</button>
        <button>삭제</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/article.scss";
</style>
