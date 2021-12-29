import request from '@/utils/request'
import store from '@/store'

export function login(data_) {
  var data = {
    'name':data_.name,
    'password':data_.password
  }
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
