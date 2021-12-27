import request from '@/utils/request'
import store from '@/store'

export function login(data_) {
  var FormData = require('form-data')
  var data = new FormData()
  data.append('name', data_.name)
  data.append('password', data_.password)
  // console.log(data)
  return request({
    url: '/auth/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/role/userinfo',
    method: 'get'
  })
}

export function getUserName() {
  return request({
    url: '/auth/require_auth',
    method: 'get',
    Headers: {
      'Authorization': store.getters.Authorization,
      'refreshToken': store.getters.refreshToken
    }
  })
}

export function logout() {
  return request({
    url: '/auth/login',
    method: 'delete',
    Headers: {
      'Authorization': store.getters.Authorization,
      'refreshToken': store.getters.refreshToken
    }
  })
}
