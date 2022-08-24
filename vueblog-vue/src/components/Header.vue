<template>
  <div>
  <div class="navClass" flat height="60">
    <!-- 导航栏 -->
    <div class="d-md-block d-none nav-container">
      <div class="float-left blog-title">
      </div>

      <div class="menus-item" >
        <span><el-link type="success" href="/blogs">主页</el-link></span>
      </div>
<!--      <div class="menus-item">-->
<!--        <span><el-link type="success" href="/blog/add">发表文章</el-link></span>-->
<!--      </div>-->
      <div class="menus-item">
        <span><el-link type="danger" href="/discovery">音乐馆</el-link></span>
      </div>

      <div class="user-btn" style="">
        <a v-if="!hasLogin">
          <i class="el-icon-user-solid" style="color: whitesmoke"/><span style="color: whitesmoke;margin-right: 10px">登录</span>
          <ul class="user-submenu">
            <li>
              <router-link to="/login">
                <i class="el-icon-s-custom" /> 普通用户
              </router-link>
            </li>
            <li>
              <a href=" ">
                <i class="user secret icon"/> 管理员
              </a>
            </li>
          </ul>
        </a>
        <template v-else>
          <img
              class="user-avatar"
              v-bind:src=$store.getters.getUserInfo.avatar
              height="30"
              width="30"
          />
          <ul class="user-submenu">
            <li>
              <router-link to="/adminHome">
                <i class="user circle icon" /> 个人中心
              </router-link>
            </li>
            <li>
              <router-link to="/postblogs">
                <i class="el-icon-edit" /> 发布博客
              </router-link>
            </li>
            <li>
              <a @click="logout"><i class="sign out alternate icon"></i> 退出</a>
            </li>
          </ul>
        </template>
      </div>
    </div>
  </div>

  </div>

</template>

<script>
// import TopNavBar from "@/components/TopNavBar";
export default {
  name: "Header",
  // components: {TopNavBar},
  data() {
    return {
      user:{
        username: '请先登录',
        avatar: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      },
      hasLogin: false,
    }
  },
  methods: {
    logout(){
      const _this = this
      _this.$axios.get("/api/logout", {
        headers: {
          "Authorization": localStorage.getItem("token")
        }
      }).then(res => {
        _this.$store.commit("REMOVE_INFO")
        _this.$router.push("/login")
      })
    }
  },
  //初始化用户的信息
  created() {
    if(this.$store.getters.getUserInfo){
      this.user.username = this.$store.getters.getUserInfo.username
      this.user.avatar = this.$store.getters.getUserInfo.avatar
      this.hasLogin = true
    }
  }
}
</script>

<style scoped>
  @import "../assets/css/typo.css";
  @import "../assets/css/animate.css";



  .blog-contact a {
    color: #fff !important;
  }
  .navClass{
    position:fixed; /* 绝对定位，fixed是相对于浏览器窗口定位。 */
    /*position: relative;*/
    top:15px; /* 距离窗口顶部距离 */
    right: 22px;
    height:10px; /* 高度 */
    float: right;
    z-index:99; /* 层叠顺序，数值越大就越高。页面滚动的时候就不会被其他内容所遮挡。 */
  }
  .menus-item {
    position: relative;
    display: inline-block;
    margin: 0 0 0 0.875rem;
  }
  .user-btn,
  .menus-btn,
  .menus-item {
    position: relative;
    display: inline-block;
    margin: 0 0 0 0.875rem;
  }
  .menus-btn a,
  .menus-item a {
    transition: all 0.2s;
  }
  .nav-fixed .menus-btn a:hover,
  .nav-fixed .menus-item a:hover {
    color: #49b1f5 !important;
  }
  .menus-item a:hover:after {
    width: 100%;
  }
  .menus-item a:after {
    position: absolute;
    bottom: -5px;
    left: 0;
    z-index: -1;
    width: 0;
    height: 3px;
    background-color: #80c8f8;
    content: "";
    transition: all 0.3s ease-in-out;
  }
  .user-btn a {
    transition: all 0.2s;
  }
  .user-avatar {
    cursor: pointer;
    border-radius: 50%;
  }
  .user-btn:hover .user-submenu {
    display: block;
  }
  .user-submenu {
    position: absolute;
    display: none;
    right: 0;
    width: max-content;
    margin-top: 8px;
    box-shadow: 0 5px 20px -4px rgba(0, 0, 0, 0.5);
    background-color: #fff;
    animation: submenu 0.3s 0.1s ease both;
  }
  .user-submenu:before {
    position: absolute;
    top: -8px;
    left: 0;
    width: 100%;
    height: 20px;
    content: "";
  }
  .user-submenu a {
    line-height: 2;
    color: #4c4948 !important;
    text-shadow: none;
    display: block;
    padding: 6px 14px;
  }
  .user-submenu a:hover {
    background: #4ab1f4;
  }
  @keyframes submenu {
    0% {
      opacity: 0;
      filter: alpha(opacity=0);
      transform: translateY(10px);
    }
    100% {
      opacity: 1;
      filter: none;
      transform: translateY(0);
    }
  }

</style>