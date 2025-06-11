import request from '@/utils/userRequest'

export function GetIndex() {
	return request({
		url: '/app/index',
		method: 'get'
	})
}

