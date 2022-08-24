<template>
  <div id="app">
    <div class="html">
      <div class="body">
        <!-- 头部组件 -->
        <MusicTopNavBar></MusicTopNavBar>
        <!-- 主体组件 -->
        <index style="width: 100%!important; height: 100%!important"></index>
      </div>
    </div>

  </div>
</template>

<script>
import MusicTopNavBar from '../components/layout/MusicTopNavBar'
import index from '../components/music/Index.vue'
export default {
  components: {
    MusicTopNavBar,
    index
  }
}
</script>

<style scoped>
.html {
  height: 100%;
  margin: 0;
  padding: 0;
  font-weight: 400;
  font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB,
  Microsoft YaHei, '\5FAE\8F6F\96C5\9ED1', Arial, sans-serif;
}

.body {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 120vh;
  background: url("https://dspncdn.com/a1/media/originals/10/d8/42/10d84246cc6d665899f2d34ac95f98e5.jpg") center center /
    cover no-repeat;
  background-color: #49b1f5;
  background-attachment: fixed;
  text-align: center;
  color: #fff !important;
  animation: header-effect 1s !important;
}
</style>
