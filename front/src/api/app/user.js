import request from '@/utils/userRequest'

export function GetUserInfo() {
	return request({
		url: '/app/userInfo',
		method: 'get'
	})
}

export function UpdateUserInfo(data) {
	return request({
		url: '/app/userInfo/update',
		method: 'post',
		data
	})
}

export function UpdatePass(data) {
	return request({
		url: '/app/updatePass',
		method: 'post',
		data
	})
}
