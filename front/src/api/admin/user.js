import request from '@/utils/adminRequest'

export function GetPage(params) {
	return request({
		url: '/sys/user/page',
		method: 'get',
		params: params
	})
}

export function Info(id) {
	return request({
		url: '/sys/user/info/' + id,
		method: 'get'
	})
}