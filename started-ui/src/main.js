import {createApp, ref} from 'vue'
import App from './App.vue'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from "@/routes";

const thisDomain = {name: '加载中', init: false, login: false};

const app = createApp(App);
app.provide(/* key */ 'thisDomain', /* value */ ref(thisDomain))
  .use(router)
  .use(ElementPlus)
  .mount('#app')
// Object.keys(console).forEach(e => {
//   console[e] = (c) => {console[e]}
// })
