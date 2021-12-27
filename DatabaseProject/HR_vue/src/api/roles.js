import request from '@/utils/request'

// 获取角色id信息
export function getRoleByrid(rid) {
  return request({
    url: '/role/' + rid ,
    method: 'get',
  })
}
