# blog
基于vue和springboot的前后端分离博客项目
## 功能介绍

本博客主要是个人练习项目，目的是巩固之前所学的后端开发基本知识。本博客已实现的功能有：

- 主站
  - 浏览博客
  - 发布博客
  - 删除博客
  - 发布评论
  - 删除评论
  - 点赞、收藏
- 音乐盒
  - 发现音乐
  - 搜索音乐
  - 听歌
  - 观看mv
- 个人中心
  - 查看、修改个人信息
  - 发布博客
  - 查看收藏的博客
  
## 后端部分
### 技术栈
- SpringBoot
- mybatis plus
- shiro
- jwt
- lombok
- hibernate validatior

其中，Mybatis Plus帮助快速进行 CRUD 操作，提升开发效率。Shiro用于权限管理。

由于这是一个前后端分离项目，所以本项目使用了jwt作为用户身份凭证。
  
## 前端部分 vueblog-vue

### 个人博客项目的前端代码，项目结构采用的是vue-cli2，用到的技术主要有：
- axios 发送异步请求
- element ui 页面美化
- mavon-editor  markdown编辑器
- markdown-it
- github-markdown-css md文件渲染
- animate.css 动画

### 页面展示

### 首页

![](https://github.com/YeMeng12/imgs/blob/master/%E9%A6%96%E9%A1%B511.png)

#### 博客页

![](https://github.com/YeMeng12/imgs/blob/master/%E5%8D%9A%E5%AE%A2%E9%A1%B5.png)

#### 博客详情页

![](https://github.com/YeMeng12/imgs/blob/master/%E5%8D%9A%E5%AE%A2%E8%AF%A6%E6%83%85.png)

#### 个人中心

![](https://github.com/YeMeng12/imgs/blob/master/%E4%B8%AA%E4%BA%BA%E6%94%B6%E8%97%8F.png)

#### 发布博客

![](https://github.com/YeMeng12/imgs/blob/master/%E5%8F%91%E5%B8%83%E5%8D%9A%E5%AE%A2.png)

#### 我的收藏

![](https://github.com/YeMeng12/imgs/blob/master/%E4%B8%AA%E4%BA%BA%E6%94%B6%E8%97%8F.png)

### 音乐盒

#### 发现音乐页

![](https://github.com/YeMeng12/imgs/blob/master/%E9%9F%B3%E4%B9%90%E9%A6%96%E9%A1%B5.png)

#### 歌单

![](https://github.com/YeMeng12/imgs/blob/master/%E6%8E%A8%E8%8D%90%E6%AD%8C%E5%8D%95.png)

#### 最新音乐

![](https://github.com/YeMeng12/imgs/blob/master/%E6%9C%80%E6%96%B0%E9%9F%B3%E4%B9%90.png)

#### 最新mv

![](https://github.com/YeMeng12/imgs/blob/master/mv.png)

#### 观看mv及评论

![](https://github.com/YeMeng12/imgs/blob/master/%E8%A7%82%E7%9C%8Bmv.png)
