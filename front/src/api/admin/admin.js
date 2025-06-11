import request from '@/utils/adminRequest'

export function GetPage(params) {
	return request({
		url: '/sys/admin/page',
		method: 'get',
		params: params
	})
}

export function Info(id) {
	return request({
		url: '/sys/admin/info/' + id,
		method: 'get'
	})
}

export function Save(data) {
	return request({
		url: '/sys/admin/save',
		method: 'post',
		data
	})
}

export function Delete(ids) {
	return request({
		url: '/sys/admin/delete',
		method: 'post',
		data: ids
	})
}

export function ResetPwd(id) {
	return request({
		url: '/sys/admin/resetPwd',
		method: 'post',
		params: {
			id: id
		}
	})
}