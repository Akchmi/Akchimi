<template>
  <div class="theBanner">
    <img class="theBanner__image" src="@/assets/images/home/mainGuitar.png" />
    <h1 class="theBanner__title">{{ title }}</h1>
    <h3 class="theBanner__subTitle">{{ subTitle }}</h3>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: "악기라는 취미에 미치다.",
    },
    subTitle: {
      type: String,
      default: "악기라는 취미에 미치다.",
    },
  },
};
</script>

<style lang="scss">
.theBanner {
  position: relative;
}

.theBanner__image {
  width: 100%;
  height: 100%;
}

.theBanner__title {
  position: absolute;
  top: 30%;
  left: 20%;
  color: white;
  font-size: 60px;
}

.theBanner__subTitle {
  position: absolute;
  top: 55%;
  left: 20%;
  color: white;
  font-size: 30px;
}
</style>
