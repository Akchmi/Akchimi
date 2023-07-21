<template>
  <div>
    <!-- 공지사항 작성 최상단-->
    <div class="flex justify-between">
      <p class="text-4xl">공지사항 작성</p>
    </div>
    <hr class="my-12" />

    <!-- 공지사항 작성 제목/내용-->
    <div>
      <span class="text-2xl flex justify-between">제목입력</span>
      <input class="border h-12 w-3/5 my-8" />
      <span class="text-2xl flex justify-between">내용입력</span>
      <input class="border h-96 w-3/5 my-8" />
    </div>

    <!-- 공지사항 작성 첨부파일-->
    <div class="flex justify-between">
      <button class="border">첨부파일 추가</button>
    </div>
    <div class="flex justify-end">
      <button class="border">작성 완료</button>
    </div>
    <br />
  </div>
</template>

<script>
export default {};
</script>

<style></style>
