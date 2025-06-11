import {defineStore} from "pinia"
import {Login, Logout} from '^/app/login'
import router from "@/router/index.js";

export const useUserStore = defineStore('user', {
	state: () => ({
		userToken: '',
		userInfo: {}
	}),
	persist: true,
	getters: {},
	actions: {
		login(form) {
			return new Promise((resolve, reject) => {
				Login(form).then(res => {
					this.userToken = res.data.userToken
					this.userInfo = res.data.userInfo

					resolve()
				}).catch(err => {
					reject(err)
				})
			})
		},
		logout() {
			Logout().then(res => {
				this.userToken = ''
				this.userInfo = {}

				// fix-bug，这里不能让他直接跳转到首页，因为当userRequest判断到用户未登录时候会先调用logout，需要跳转到login页面，而不是首页
				// router.push("/")
			})
		},

		updateUserInfo(userInfo) {
			this.userInfo = userInfo
		},
	}
})