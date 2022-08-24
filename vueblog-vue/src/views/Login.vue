<template>
  <div>
    <Header></Header>
    <div>
      <div class="home-banner">
        <div class="banner-container"></div>
      </div>
    </div>
    <el-container class="login_box">
      <!-- 头像区域 -->
      <div class="avatar_box">
        <img src="../assets/logo.png" alt="">
      </div>
<!--登录表单-->
      <el-form  :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="login_form">
        <!-- 用户名 -->
        <el-form-item prop="username">
          <el-input v-model="ruleForm.username" placeholder="用户名" prefix-icon="el-icon-user-solid"></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="password">
          <el-input v-model="ruleForm.password" placeholder="密码" prefix-icon="el-icon-moon-night" type="password"></el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-input v-model="ruleForm.code" placeholder="请输入验证码" prefix-icon="el-icon-picture-outline-round" style=""></el-input>
          <img v-bind:src="verifyCode" @click="getVerifyCode()" class="verifyCode" />
        </el-form-item>
        <!-- 按钮区域 -->
        <el-form-item class="btns">
          <el-button type="success" @click="signup()">注册</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
          <el-button type="info" @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
        <div></div>
      </el-form>
    </el-container>

  </div>

</template>


<script>
import Header from "@/components/Header";
export default {
  name: "Login",
  components:{
    Header,
  },
  data() {
    return {
      verifyCode: '',
      ruleForm: {
        username: '',
        password: '',
        code: '',
        verKey: '',
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'change'}
        ]
      },
      hasLogin: false,
      };
    },
  created() {
    this.getVerifyCode()
  },

  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
      if (valid) {
        const _this = this
      // alert('submit!');
      this.$axios.post('/api/login', this.ruleForm).then(res => {
          console.log(res)
          //从返回的结果请求头中获取到token的信息
          const jwt = res.headers['authorization']
          const userInfo = res.data.data
          //把数据共享出去——使用store提交token和用户信息的状态
          _this.$store.commit("SET_TOKEN", jwt);
          _this.$store.commit("SET_USERINFO", userInfo)
          //获取userinfo信息
          // console.log(_this.$store.getters.getUserInfo)
          _this.hasLogin = true
          //调整到了/blogs路由，即博客列表页面。
          _this.$router.push("/blogs")
      })
      } else {
        console.log('error submit!!');
        return false;
      }
  });
},
  resetForm(formName) {
    this.$refs[formName].resetFields();
  },
    signup(){
      this.$router.push("/signup")
    },
    async getVerifyCode () {
      const { data: res } = await this.$axios.get('/api/captcha')
      this.verifyCode = res.data.verCode
      this.ruleForm.verKey = res.data.verKey
    },
}
}
</script>

<style scoped>

  body > .el-container {
    margin-bottom: 40px;
    }

    .el-container:nth-child(5) .el-aside,
    .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
    }
  body > .el-container {
    margin-bottom: 40px;
  }

  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }

  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }


  .home-banner {
    position: absolute;
    top: 0px;
    left: 0;
    right: 0;
    height: 100vh;
    background: url("https://dspncdn.com/a1/media/originals/c7/fb/c1/c7fbc158d8c3a6e103747fd9e7c6e083.jpg") center center /
    cover no-repeat;
    background-color: #49b1f5;
    background-attachment: fixed;
    text-align: center;
    /*color: #fff !important;*/
    animation: header-effect 1s !important;
    z-index: -1;
  }
  .banner-container {
    margin-top: 43vh;
    line-height: 1.5;
    /*color: #eee;*/
    color: black;
  }
  .blog-contact a {
    /*color: #fff !important;*/
    color: black;
  }

   /*修改form表单的字体颜色*/
   .form-content >>> .el-form-item__label{
     color: black;
   }

  .avatar-uploader .el-upload {
    border: 5px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .login_box {
    width: 450px;
    height: 410px;
    background-color: #fff;
    border-radius: 7px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);}

    .avatar_box {
      height: 130px;
      width: 130px;
      border: 1px solid #eee;
      border-radius: 50%;
      padding: 10px;
      box-shadow: 0 0 10px #ddd;
      position: absolute;
      left: 50%;
      transform: translate(-50%, -50%);
      background-color: #fff;}
      img {
        width: 100%;
        height: 100%;
        border-radius: 50%;
        background-color: white;
      }
  /*  }*/
  /*}*/


  .login_form {
    position: absolute;
    bottom: 0px;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
  }
  .verifyCode{
    float: left;
    width: 140px;
    height: 50px;
    cursor:pointer;
  }


</style>