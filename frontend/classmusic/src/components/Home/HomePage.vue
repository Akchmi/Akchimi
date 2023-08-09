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
      <input
        class="mainSearchBar"
        type="text"
        placeholder="검색어를 입력해주세요"
      />
      <img
        id="mainSearchButton"
        src="@/assets/images/home/searchButton.png"
        alt="메인검색버튼"
      />
    </div>
    <div>
      <button>
        <img
          class="intstumentButton"
          src="@/assets/images/home/pianoButton.png"
          alt=""
        />
      </button>
      <button>
        <img
          class="intstumentButton"
          src="@/assets/images/home/baseButton.png"
          alt=""
        />
      </button>
      <button>
        <img
          class="intstumentButton"
          src="@/assets/images/home/guitarButton.png"
          alt=""
        />
      </button>
      <button>
        <img
          class="intstumentButton"
          src="@/assets/images/home/violinButton.png"
          alt=""
        />
      </button>
      <button>
        <img
          class="intstumentButton"
          src="@/assets/images/home/drumButton.png"
          alt=""
        />
      </button>
      <button>
        <img
          class="intstumentButton"
          src="@/assets/images/home/etcButton.png"
          alt=""
        />
      </button>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
@import "@/assets/scss/home.scss";

.container {
  justify-content: center;
  text-align: center;
  align-items: center;
}

#mainImage {
  width: 100%;
}

.mainSearch {
  margin-top: 100px;
  margin-bottom: 15px;
  display: flex;
  justify-content: center;
  align-content: center;
}

.mainSearchBar {
  width: 400px;
  height: 50px;
  border-radius: 2em;
  border: 3px solid #edd9b7;
  padding-left: 30px;
  padding-right: 100px;
}

#mainSearchButton {
  width: 40px;
  height: 40px;
  margin-top: 10px;
  margin-left: -60px;
}

#mainSearchButton:hover {
  width: 42px;
  height: 42px;
}

.intstumentButton {
  widows: 60px;
  height: 60px;
}

button {
  background-color: transparent;
  padding: 0;
  border: transparent;
}

button:hover {
  border: solid black 1px;
}
</style>
