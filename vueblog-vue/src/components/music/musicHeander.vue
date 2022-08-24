<template>
  <div id="app">
    <MusicTopNavBar></MusicTopNavBar>
    <!-- 头部组件 -->
    <!--    <Top></Top>-->
    <!-- 主体组件 -->
<!--    <index style="width: 100%!important;"></index>-->
  </div>
</template>

<script>
import MusicTopNavBar from '../layout/MusicTopNavBar'
import index from '../music/Index.vue'
export default {
  name: "musicHeader",
  // 注册组件
  components: {
    // Top, // top:top
    MusicTopNavBar,
    index// index:index
  }
}
</script>

<style scoped>
</style>
