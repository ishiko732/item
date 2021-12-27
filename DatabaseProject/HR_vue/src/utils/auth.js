import Cookies from 'js-cookie'

const Authorization = 'Authorization'
const refreshToken = 'refreshToken'

export function getToken() {
  return Cookies.get(Authorization)
}

export function getRefreshToken() {
  return Cookies.get(refreshToken)
}

export function setToken(token) {
  return Cookies.set(Authorization, token)
}

export function setRefreshToken(token) {
  return Cookies.set(refreshToken, token)
}

export function removeToken() {
  Cookies.remove(Authorization)
  return Cookies.remove(refreshToken)
}
