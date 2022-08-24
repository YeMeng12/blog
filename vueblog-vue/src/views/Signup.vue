<template>
  <div>
    <Header></Header>
    <div class="home-banner">
      <div class="banner-container"></div>
      <h1 class="blog-welcome animated zoomIn">
        欢迎加入Lydia的博客🎉
      </h1>
    </div>
<!--注册页面-->
    <el-container class="login_box">
      <el-main>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户名" prop="username" class="form-content">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email" class="form-content">
            <el-input type="email" v-model="ruleForm.email"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password" class="form-content">
            <el-input type="password" v-model="ruleForm.password"></el-input>
          </el-form-item>
          <el-form-item label="头像地址" prop="avatar" class="form-content">
            <el-input type="avatar" v-model="ruleForm.avatar"></el-input>
          </el-form-item>
          <el-form-item label="or上传本地图片" class="form-content">
            <el-upload
                class="avatar-uploader"
                action="/api/file/userAvatar/"
                accept="image/png,.jpg"
                multiple
                :limit="1"
                :on-exceed="masterFileMax"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item class="btns">
            <el-button @click="resetForm('ruleForm')">重置</el-button>
            <el-button @click="submitForm('ruleForm')">注册</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>

</template>

<script>
import Header from "@/components/Header";
export default {
  name: "Signup",
  components:{
    Header,
  },
  data() {
    return {
      imageUrl: '',
      ruleForm: {
        username: '',
        avatar: '',
        email:'',
        password: '',
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'change'}
        ]
      }
    };
  },
  methods:{
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/api/signUp', this.ruleForm).then(res => {
            this.$message({
              message: '注册成功',
              type: 'success'
            })
            this.$router.push("/blogs")
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
    masterFileMax (files, fileList) {
      this.$message.warning('请最多上传一张图片')
    },
    handleAvatarSuccess (res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
      this.ruleForm.avatar = res.data.url
      console.log("this.imageUrl = " + this.imageUrl);
      this.$message.success('上传头像成功')
    },
    beforeAvatarUpload (file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt5M = file.size / 1024 / 1024 < 5

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt5M) {
        this.$message.error('上传头像图片大小不能超过5MB!')
      }
      return isJPG && isLt5M
    },
  }
}

</script>

<style scoped>
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
    color: #eee;
  }
  .demo-ruleForm{
    margin-right: 40px;
  }

  /*修改fform表单的字体颜色*/
  .form-content >>> .el-form-item__label{
    color: black;
  }
  .blog-welcome{
    color: #164641;
    /*margin-top: 10vh;*/
    position: absolute;
    top: 10px;
    left: 570px;

  }

  .login_box {
    width: 500px;
    height: 530px;
    background-color: #fff;
    border-radius: 7px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }
  .avatar-uploader .el-upload {
    border: 5px dashed #d9d9d9;
    border-radius: 5px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
    /*position: absolute;*/
    /*margin-right: 200px;*/
    margin-top: 0px;
  }
  .avatar {
    width: 60px;
    height: 60px;
    display: block;
  }
  .btns{
    position: relative;
    margin-bottom: 0px;
    padding-bottom: 0px;

  }

</style>