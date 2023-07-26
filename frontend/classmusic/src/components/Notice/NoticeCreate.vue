<template>
  <div>
    <div class="container">
      <!-- 공지사항 글쓰기 최상단 -->
      <h1>공지사항 작성</h1>
      <hr />
    </div>

    <div class="container">
      <!-- 글 제목, 내용 -->
      <h3>제목</h3>
      <input class="input__title" type="text" />
      <h3>내용</h3>
      <input class="input__content" type="text" />
    </div>

    <hr />
    <div class="container">
      <!-- 첨부파일 -->
      <button>첨부파일 추가</button>
    </div>

    <div class="container">
      <!-- 작성완료 버튼 -->
      <button>작성완료</button>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/notice.scss";
</style>
