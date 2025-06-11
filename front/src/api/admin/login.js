import request from '@/utils/adminRequest'

export function Login(data) {
	return request({
		url: '/sys/login',
		method: 'post',
		data
	})
}

export function Logout() {
	return request({
		url: '/sys/logout',
		method: 'get'
	})
}

export function UpdatePass(data) {
	return request({
		url: '/sys/updatePass',
		method: 'post',
		data
	})
}

export function GetData() {
	return request({
		url: '/admin/data',
		method: 'get',
	})
}

