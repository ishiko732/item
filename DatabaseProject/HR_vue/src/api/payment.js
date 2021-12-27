import request from '@/utils/request'

// 获取薪酬标准
export function getPayment(salaryId) {
  return request({
    url: '/payment/getSalary?salaryId=' + salaryId,
    method: 'get',
  })
}

// 获取薪酬标准-模糊
export function getPayment_mode(data_) {
  var FormData = require('form-data');
  var data = new FormData();
  data.append('salaryId', data_.salaryId);
  data.append('salaryName', data_.salaryName);
  data.append('MRUName', data_.MRUName);
  data.append('registerName', data_.registerName);
  data.append('checkUserName', data_.checkUserName);
  data.append('time1', data_.time1);
  data.append('time2', data_.time2);
  return request({
    url: '/payment/selectSalary',
    method: 'get',
    data
  })
}

// 新建薪酬标准
export function insertPayment(data_) {
  const data =JSON.stringify(data_)
  return request({
    url: 'payment/Salary',
    method: 'post',
    data
  })
}

// 复核查询
export function check(id) {
  if (id != null) {
    url_ = '/recheck/checkSalary?id=' + id
  }else {
    url_ = '/recheck/checkSalary'
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
    url: '/recheck/checkSalary/' + id,
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

// 删除薪酬标准
export function deleteById(id) {
  var FormData = require('form-data');
  var data = new FormData();
  data.append('statusID', '-1');
  return request({
    url: '/recheck/checkUser/' + id,
    method: 'delete',
    data
  })
}
