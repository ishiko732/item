const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  Authorization: state => state.user.Authorization,
  refreshToken: state => state.user.refreshToken,
  name: state => state.user.name,
  uid: state => state.user.uid,
  role: state => state.user.role,
  permissions: state => state.user.permissions
}
export default getters
