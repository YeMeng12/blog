<template>
  <div class="home-banner">
    <div class="banner-container">
      <h1 class="blog-title animated zoomIn">
        欢迎👏来到Lydia的博客🎉
      </h1>
      <!-- 向下滚动 -->
      <div class="scroll-down" @click="scrollDown" style="margin-top: 200px">
        <h3>向下滚动<i class="el-icon-arrow-down"></i></h3>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "Home",
  methods:{
    scrollDown () {
      window.scrollTo({
        behavior: 'smooth',
        top: document.documentElement.clientHeight
      })
    },
  }
}
</script>

<style scoped>
@import "../assets/css/typo.css";
@import "../assets/css/animate.css";
.home-banner {
  position: absolute;
  top: 0px;
  left: 0;
  right: 0;
  height: 100vh;
  background: url("https://dspncdn.com/a1/media/originals/c6/73/19/c67319d27e1f3b0cdff0e2f76c5e175c.jpg") center center /
    cover no-repeat;
  /* background-size: 100% 100%; */
  background-color: #164641;
  background-attachment: fixed;
  text-align: center;
  color: #164641;
  animation: header-effect 1s !important;
}
.banner-container {
  margin-top: 43vh;
  line-height: 1.5;
  color: #eee;
}
.blog-title{
  color: #164641;
  margin-top: 47vh;
}

</style>