import request from '@/utils/adminRequest'

export function GetPage(params) {
	return request({
		url: '/admin/order/page',
		method: 'get',
		params: params
	})
}

export function Info(id) {
	return request({
		url: '/admin/order/info/' + id,
		method: 'get'
	})
}

export function CheckIn(data) {
	return request({
		url: '/admin/order/checkIn',
		method: 'post',
		data
	})
}
export function CheckOut(id) {
	return request({
		url: '/admin/order/checkOut',
		method: 'post',
		params: {
			id: id
		}
	})
}

export function Cancel(id) {
	return request({
		url: '/admin/order/cancel',
		method: 'post',
		params: {
			id: id
		}
	})
}