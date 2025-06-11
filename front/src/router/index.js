import {createRouter, createWebHistory} from "vue-router"

const routes = [
	{
		path: '/adminLayout',
		name: "AdminLayout",
		component: () => import('~/AdminLayout.vue'),
		children: [
			{
				path: '/admin',
				name: "AdminIndex",
				component: () => import('../views/admin/index.vue')
			},

			{
				path: '/admin/user',
				name: "AdminUser",
				component: () => import('../views/admin/user/index.vue')
			},
			{
				path: '/admin/admin',
				name: "AdminAdmin",
				component: () => import('../views/admin/admin/index.vue')
			},
			{
				path: '/admin/updatePwd',
				name: "AdminUpdatePwd",
				component: () => import('../views/admin/admin/updatePwd.vue')
			},
			{
				path: '/admin/hotel',
				name: "AdminHotel",
				component: () => import('../views/admin/hotel/index.vue')
			},
			{
				path: '/admin/roomType',
				name: "AdminRoomType",
				component: () => import('../views/admin/roomType/index.vue')
			},
			{
				path: '/admin/room',
				name: "AdminRoom",
				component: () => import('../views/admin/room/index.vue')
			},
			{
				path: '/admin/checkIn',
				name: "AdminCheckIn",
				component: () => import('../views/admin/order/checkIn.vue')
			},
			{
				path: '/admin/order',
				name: "AdminOrder",
				component: () => import('../views/admin/order/index.vue')
			},
		]
	},

	{
		path: '/admin/login',
		name: "AdminLogin",
		component: () => import('../views/admin/login.vue')
	},


	{
		path: '/appLayout',
		name: "AppLayout",
		component: () => import('~/AppLayout.vue'),
		children: [
			{
				path: '/login',
				name: "Login",
				component: () => import('../views/app/login.vue')
			},
			{
				path: '/register',
				name: "Register",
				component: () => import('../views/app/register.vue')
			},
			{
				path: '/',
				name: "Index",
				component: () => import('../views/app/index.vue')
			},
			{
				path: '/hotel-list',
				name: "hotel-list",
				component: () => import('../views/app/hotelList.vue')
			},
			{
				path: '/hotel-detail/:hotelId',
				name: "hotel-detail",
				component: () => import('../views/app/hotelDetail.vue')
			},
			{
				path: '/order-detail/:orderId',
				name: "order-detail",
				component: () => import('../views/app/orderDetail.vue')
			},
			{
				path: '/user/center',
				name: "UserCenter",
				component: () => import('../views/app/user/center.vue'),
				children: [
					{
						path: '/user/profile',
						name: "UserProfile",
						component: () => import('../views/app/user/profile.vue')
					},
					{
						path: '/user/updatePwd',
						name: "UserUpdatePwd",
						component: () => import('../views/app/user/updatePwd.vue')
					},
										{
						path: '/user/order-list',
						name: "UserOrderList",
						component: () => import('../views/app/user/orderList.vue')
					},
				]
			},

		]
	},

]

const router = createRouter({
	history: createWebHistory(),
	routes
})
export default router