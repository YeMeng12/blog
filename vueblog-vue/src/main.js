import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
import axios from 'axios'

import "./axios"
import "./permission"

// 导入后台管理全局样式表
import './assets/css/iconfont.css'
import './assets/css/global.css'
import './assets/css/index.css'
import './assets/css/me.css'
import './plugins/element.js'

import './plugins/element.js'
import 'mavon-editor/dist/css/index.css'
// 全局注册
import mavonEditor from 'mavon-editor'


// use
// 使用elementUI
Vue.use(Element)

Vue.use(mavonEditor)

Vue.prototype.$axios = axios

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')


