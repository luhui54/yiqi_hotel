import axios from "axios"
import {ElMessage} from "element-plus"
import {Loading} from "@element-plus/icons-vue"
import {h} from 'vue'
import {useAdminStore} from "../stores/admin"
import router from '../router'

const service = axios.create({
	baseURL:  import.meta.env.VITE_APP_BASE_URL,
	timeout: 10000
})

let loading = null

service.interceptors.request.use((config) => {

	// 请求前拦截处理
	if (!loading) {
		loading = ElMessage({
			message: '数据加载中...',
			duration: 0,
			icon: h(Loading)
		});
	}
	const adminStore = useAdminStore()

	if (adminStore.adminToken) {
		config.headers['adminToken'] = adminStore.adminToken
	}

	return config;
}, (error) => {
	return Promise.reject(error)
})

service.interceptors.response.use((response) => {

	// 请求响应拦截处理
	loading.close()
	const adminStore = useAdminStore()

	// 对返回的数据进行识别
	const res = response.data
	if (res.code === 200) {
		return res

	} else if (res.code === 401) {
		adminStore.logout()
		router.push("/admin/login").then()
		// fix-bug
		return Promise.reject("未登录")

	} else {
		ElMessage.error(res.msg)
		return Promise.reject(new Error(res.msg))
	}

}, (error) => {
	loading.close()

	if (error.response) {
		let {data} = error.response
		ElMessage.error(data.msg)
	}

	return Promise.reject(error);
})

export default service