import request from '@/utils/request'

// 获取部门矩阵-正向-list
export function department() {
  return request({
    url: '/record/department',
    method: 'get',
  })
}

// 获取部门矩阵-反向-list
export function reDepartment(dep3id) {
  return request({
    url: '/record/reDepartment/' + dep3id,
    method: 'get',
  })
}

// 档案登记 必须字段rid，fid，pid，name，password
export function register(data) {
  return request({
    url: '/record/register',
    method: 'post',
    data
  })
}

// 档案更新 必须字段uid
export function update(data) {
  return request({
    url: '/record/updateUID',
    method: 'post',
    data
  })
}

// 档案调动 必须字段uid,rid,pid
export function transfer(data_) {
  var FormData = require('form-data');
  var data = new FormData();
  data.append('uid', data_.uid);
  data.append('rid', data_.rid);
  data.append('pid', data_.pid);
  return request({
    url: '/record/transferPosition',
    method: 'post',
    data
  })
}

// 复核查询
export function check(id) {
  if (id != null) {
    url_ = '/recheck/checkUser?id=' + id
  }else {
    url_ = '/recheck/checkUser'
  }
  return request({
    url: url_,
    method: 'get',
    data
  })
}

// 复核查询详细内容
export function checkMessageById(id) {
  return request({
    url: '/recheck/checkUser/' + id,
    method: 'get',
  })
}

// 确认复核
export function checkById(data) {
  return request({
    url: '/recheck/checkUser/' + data.id,
    method: 'put',
    data
  })
}

// 删除档案
export function deleteById(id) {
  var FormData = require('form-data');
  var data = new FormData();
  data.append('statusID', '-1');
  return request({
    url: '/recheck/checkUser/' + id,
    method: 'put',
    data
  })
}

// 恢复档案
export function recoverById(id) {
  var FormData = require('form-data');
  var data = new FormData();
  data.append('statusID', '1');
  return request({
    url: '/recheck/checkUser/' + id,
    method: 'put',
    data
  })
}
