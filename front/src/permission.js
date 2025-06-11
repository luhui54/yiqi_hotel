import router from "@/router/index.js"
import {useAdminStore} from "@/stores/admin.js";

const whiteList = ['AdminLogin']

router.beforeEach(async (to, from, next) => {

	// 白名单
	if (whiteList.includes(to.name)) {
		next()
		return // fix-bug，加上return
	}

	if (!to.path.startsWith('/admin')) {
		next()
		return
	}

	const adminStore = useAdminStore()

	if (!adminStore.adminToken) {
		next('/admin/login')
		return
	}

	next()
})

// fix-bug: 解决新页面不置顶问题
router.afterEach((to, from, next) => {
	window.scrollTo(0, 0)
})