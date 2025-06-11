import {createApp} from 'vue'
import './style.css'
import App from './App.vue'
import router from "./router/index.js"
import {createPinia} from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import {dayjs} from "element-plus";

import './permission'

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

const app = createApp(App)

app.config.globalProperties.$fD = function (dateStr) {
	return dayjs(dateStr).format('YYYY-MM-DD HH:mm:ss')
}

app
		.use(router)
		.use(pinia)
		.use(ElementPlus)
		.mount('#app')
