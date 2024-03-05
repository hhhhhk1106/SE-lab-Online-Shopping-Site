import { createApp } from 'vue'
import { createPinia } from 'pinia'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import App from './App.vue'
import router from './router'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'//引入icons图标

// import './router/permission'



// var axios = require('axios')

// Vue.prototype.$axios = axios

import axios from 'axios'
// 将axios挂载到原型对象上

// import Vue from "vue";
// import VueAxios from "vue-axios";

// Vue.use(VueAxios,axios)

const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }

app.config.globalProperties.$axios = axios;
// 设置url
axios.defaults.baseURL = '/api';
// 请求时带上cookie
axios.defaults.withCredentials = true;



app.use(createPinia())
app.use(router)

app.use(ElementPlus)

app.mount('#app')
