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
      default: "",
    },
  },
};
</script>

<style lang="scss" scoped>
.theBanner {
  width: 100%;
  height: 100%;
  position: relative;
  overflow: hidden;
}

.theBanner__image {
  width: 100%;
  margin-top: -90px;
}

.theBanner__title {
  position: absolute;
  top: 50%;
  left: 15%;
  color: white;
  font-size: 30px;
}

.theBanner__subTitle {
  position: absolute;
  top: 60%;
  left: 15%;
  color: white;
  font-size: 15px;
}
</style>
