import { login, logout, getInfo, getUserName } from '@/api/user'
import { getToken, setToken, removeToken, setRefreshToken, getRefreshToken } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    Authorization: getToken(),
    refreshToken: getRefreshToken(),
    name: '',
    uid: 0,
    role: '',
    permissions: []
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: (state) => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, Authorization) => {
    state.Authorization = Authorization
  },
  SET_REFRESHTOKEN: (state, refreshToken) => {
    state.refreshToken = refreshToken
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_UID: (state, uid) => {
    state.uid = uid
  },
  SET_ROLE: (state, role) => {
    state.role = role
  },
  SET_PERMISSIONS: (state, permissions) => {
    state.permissions = permissions
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { name, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ 'name': name.trim(), 'password': password }).then(response => {
        const { data } = response
        commit('SET_TOKEN', data.Authorization)
        commit('SET_REFRESHTOKEN', data.refreshToken)
        setToken(data.Authorization)
        setRefreshToken(data.refreshToken)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const { data } = response
        if (!data) {
          return reject('验证失败，请重新登录。')
        }

        const { name, permissions } = data
        commit('SET_ROLE', name)
        commit('SET_PERMISSIONS', permissions)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
      getUserName().then(response => {
        const { data } = response
        commit('SET_UID', data.uid)
        commit('SET_NAME', data.name)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout().then(() => {
        removeToken() // must remove  token  first
        resetRouter()
        commit('RESET_STATE')
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

