import request from '@/utils/request'

// 根据部门生成薪酬发放编号
export function generalpayroll() {
  return request({
    url: '/serial/generalSalary',
    method: 'get',
  })
}

// 查询薪酬发放内容 isCascade=1 并查询serials条目
export function selectPayroll(data_) {
  var data = {
    'year': data_.year,
    'month': data_.month,
    'isCascade': data_.isCascade
  }
  return request({
    url: '/serial/payroll',
    method: 'get',
    data
  })
}

// 查询薪酬发放内容-指定
export function selectlpayrollByPayrollId(payrollId) {
  return request({
    url: '/serial/payroll/' + payrollId,
    method: 'get',
  })
}

// 更新某人员的奖金和罚金
export function updatepayrollBySerialId(data_) {
  var data = {
    'bounty':data_.bounty,
    'penalty':data_.penalty
  }
  return request({
    url: '/serial/payroll/' + data_.serialID,
    method: 'put',
  })
}

// 删除某人员的发放
export function deletepayrollBySerialId(serialID) {
  return request({
    url: '/serial/payroll/' + serialID,
    method: 'delete',
  })
}

// 获取薪酬发放列表(获取薪酬发放详细内容)
export function check() {
  if (id != null) {
    url_ = '/recheck/checkSerial/' + id
  }else {
    url_ = '/recheck/checkSerial'
  }
  return request({
    url: url_,
    method: 'get',
  })
}

// 审核发放
export function checkById(data_) {
  var data ={
    'message':data_.message,
    'statusID':data_statusID
  }

  return request({
    url: '/recheck/checkSerial/' + data_.id,
    method: 'put',
    data
  })
}

// 删除薪酬标准
export function deleteById(id) {
  return request({
    url: '/recheck/checkSerial/' + id,
    method: 'delete',
  })
}
