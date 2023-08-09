<template>
  <div class="container">
    <div>
      <img
        id="mainImage"
        src="@/assets/images/home/mainImage.png"
        alt="메인이미지"
      />
    </div>

    <div class="mainSearch">
      <input type="text" placeholder="검색어를 입력해주세요" />
      <button>검색</button>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/home.scss";
</style>

<style>
.container {
  justify-content: center;
  text-align: center;
  align-items: center;
}

#mainImage {
  width: 100%;
}

.mainSearch {
  margin: 500px;
  border: solid 1px;
}
</style>
