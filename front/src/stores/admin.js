import {defineStore} from "pinia"
import {Login, Logout} from "^/admin/login.js";
import router from "@/router/index.js";

export const useAdminStore = defineStore('admin', {
	state: () => ({
		adminToken: '',
		adminInfo: {},
		menus: [],
		perms: []
	}),
	persist: true,
	getters: {},
	actions: {
		login(form) {
			return new Promise((resolve, reject) => {
				Login(form).then(res => {
					this.adminToken = res.data.adminToken
					this.adminInfo = res.data.adminInfo

					resolve()
				}).catch(err => {
					reject(err)
				})
			})
		},
		logout() {
			Logout().then(res => {
				this.adminToken = ''
				this.adminInfo = {}

				router.push("/admin/login")
			})
		},
	}
})