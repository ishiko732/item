import request from '@/utils/request'

// 根据部门生成薪酬发放编号
export function generalpayroll() {
  return request({
    url: '/serial/generalSalary',
    method: 'post',
  })
}

// 查询薪酬发放内容 isCascade=1 并查询serials条目
export function selectPayroll() {
  // var data = {
  //   // 'year': data_.year,
  //   // 'month': data_.month,
  //   // 'isCascade': data_.isCascade
  // }
  return request({
    url: '/serial/payroll?isCascade=1',
    method: 'get',
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
export function updatepayrollBySerialId(data) {
  return request({
    url: '/serial/payroll/' + data.serialID +"?bounty="+data.bounty+"&penalty="+data.penalty,
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
  return request({
    url: '/recheck/checkSerial',
    method: 'get',
  })
}

// 审核发放
export function checkById(data) {
  return request({
    url: '/recheck/checkSerial/' + data.id,
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
