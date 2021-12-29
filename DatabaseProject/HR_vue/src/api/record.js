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

// 查询档案模糊
export function selectRecord(data) {
  console.log(data)
  return request({
    url: '/record/selectRecord',
    method: 'post',
    data
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

// 获取职位
export function selectPosition(id) {
  let url_;
  if(id==null){
    url_='/record/position'
  }else{
    url_='/record/position/' + id
  }
  return request({
    url: url_,
    method: 'get',
  })
}

// 获取职位 通过pid
export function getPositionByPid(id) {
  return request({
    url: '/record/positionByPid/' + id,
    method: 'get',
  })
}

// 获取职称
export function selectJobtitle() {
  return request({
    url: '/record/jobTitles',
    method: 'get',
  })
}

// 获取职称类别
export function selectClassification() {
  return request({
    url: '/record/classification',
    method: 'get',
  })
}

// 获取职称类别通过PCID
export function selectClassificationByPcId(pcid) {
  console.log(pcid)
  return request({
    url: '/record/classification/' + pcid,
    method: 'get',
  })
}

// 获取当前登录用户ID和姓名 uid，name
export function getMNR() {
  return request({
    url: 'auth/require_auth',
    method: 'get',
  })
}

// 获取详细档案
export function getRecord(uid) {
  return request({
    url: 'record/' + uid,
    method: 'get',
  })
}
