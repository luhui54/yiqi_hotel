import request from '@/utils/userRequest'

export function GetDict() {
	return request({
		url: '/app/hotel/dict',
		method: 'get'
	})
}

export function Search(params) {
	return request({
		url: '/app/hotel/search',
		method: 'get',
		params: params
	})
}

export function Detail(id) {
	return request({
		url: '/app/hotel/detail',
		method: 'get',
		params: {
			id: id
		}
	})
}

