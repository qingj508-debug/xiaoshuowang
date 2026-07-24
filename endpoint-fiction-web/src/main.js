import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ElementPlus from 'element-plus' //引入element-plus
import 'element-plus/dist/index.css'  //样式文件
import '@/assets/styles/base.css'
import '@/assets/styles/main.css'


const app = createApp(App)

app.use(ElementPlus)     //挂载使用

app.use(store)
app.use(router)
app.mount('#app')