import request from '@/utils/userRequest'

export function Login(data) {
	return request({
		url: '/app/login',
		method: 'post',
		data
	})
}

export function Logout() {
	return request({
		url: '/app/logout',
		method: 'get'
	})
}

export function GetCaptcha() {
	return request({
		url: '/app/captcha',
		method: 'get'
	})
}

export function Register(data) {
	return request({
		url: '/app/register',
		method: 'post',
		data
	})
}
