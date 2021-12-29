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
  var data ={
    'salaryId': data_.salaryId,
    'salaryName': data_.salaryName,
    'MRUName': data_.MRUName,
    'registerName': data_.registerName,
    'checkUserName': data_.checkUserName,
    'time1': data_.time1,
    'time2': data_.time2,
  }
  return request({
    url: '/payment/selectSalary',
    method: 'get',
    data
  })
}

// 新建薪酬标准
export function insertPayment(data) {
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
  var data = {
    'statusID':-1
  }
  return request({
    url: '/recheck/checkUser/' + id,
    method: 'delete',
    data
  })
}
