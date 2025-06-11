import request from '@/utils/userRequest'

export function Create(data) {
	return request({
		url: '/app/order/create',
		method: 'post',
		data
	})
}

export function Detail(id) {
	return request({
		url: '/app/order/detail/' + id,
		method: 'get',
	})
}

export function Cancel(id) {
	return request({
		url: '/app/order/cancel',
		method: 'post',
		params: {
			id: id
		}
	})
}

export function Pay(id) {
	return request({
		url: '/app/order/pay',
		method: 'post',
		params: {
			id: id
		}
	})
}

export function List(params) {
	return request({
		url: '/app/order/list',
		method: 'get',
		params: params
	})
}

export function Dict() {
	return request({
		url: '/app/order/dict',
		method: 'get',
	})
}
