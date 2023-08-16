<template>
  <div>
    <footer class="footer__container">
      <img class="footerLogo" src="@/assets/images/colorlogo.png" alt="" />
      <div class="footerContent">
        <div class="footerContentTop">
          <div>
            <div style="display: flex">
              <p class="footerRightTitle" @click="changeViewTeam">Team&nbsp;</p>
              <p class="footerRightContent" v-if="nowView == 'team'">
                쿼카in&nbsp;
              </p>
            </div>
          </div>
          <div style="display: flex">
            <p class="footerRightTitle" @click="changeViewCreators">
              | Creators&nbsp;
            </p>
            <p class="footerRightContent" v-if="nowView == 'creators'">
              박한샘 김상혁 김주현 송희도 신우종 최태호&nbsp;
            </p>
          </div>
          <div style="display: flex">
            <p class="footerRightTitle" @click="changeViewEmail">
              | Email&nbsp;
            </p>
            <p class="footerRightContent" v-if="nowView == 'email'">
              shinuj9966@gmail.com&nbsp;
            </p>
          </div>
        </div>
        <p style="color: grey; font-size: 12px">
          본 사이트의 콘텐츠는 저작권법의 보호를 받는 바 무단 전재, 복사, 배포
          등을 금합니다.
        </p>
        <p style="font-size: 16px">
          Copyright &copy; Quokka-in All Rights Reserved.
        </p>
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  data() {
    return {
      nowView: "none",
    };
  },
  methods: {
    changeViewTeam() {
      if (this.nowView == "team") {
        this.nowView = "none";
      } else {
        this.nowView = "team";
      }
    },
    changeViewCreators() {
      if (this.nowView == "creators") {
        this.nowView = "none";
      } else {
        this.nowView = "creators";
      }
    },
    changeViewEmail() {
      if (this.nowView == "email") {
        this.nowView = "none";
      } else {
        this.nowView = "email";
      }
    },
  },
};
</script>

<style scoped>
.footerLogo {
  margin: 10px 0px 10px 0px;
  height: 80px;
}

.footerContent {
  margin: 20px 0px 0px 50px;
}

.footerContentTop {
  display: flex;
}

p {
  margin-bottom: 10px;
  width: fit-content;
}

.footerRightTitle {
  cursor: pointer;
}

.footerRightContent {
  color: black;
}
</style>
