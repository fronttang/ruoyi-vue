import request from '@/utils/request'

// 查询检测单位账号列表
export function listDetectUnitUser(query) {
  return request({
    url: '/projectrole/detectUnitUser/list',
    method: 'get',
    params: query
  })
}

// 查询检测单位账号详细
export function getDetectUnitUser(id) {
  return request({
    url: '/projectrole/detectUnitUser/' + id,
    method: 'get'
  })
}

// 新增检测单位账号
export function addDetectUnitUser(data) {
  return request({
    url: '/projectrole/detectUnitUser',
    method: 'post',
    data: data
  })
}

// 修改检测单位账号
export function updateDetectUnitUser(data) {
  return request({
    url: '/projectrole/detectUnitUser',
    method: 'put',
    data: data
  })
}

// 删除检测单位账号
export function delDetectUnitUser(id) {
  return request({
    url: '/projectrole/detectUnitUser/' + id,
    method: 'delete'
  })
}

// 查询账号字典
export function getDetectUnitUserDictByType(type, detectId) {
  return request({
    url: '/projectrole/detectUnitUser/dict/' + type + '/' + detectId,
    method: 'get'
  })
}

// 查询账号字典
export function getDetectUnitUserDict() {
  return request({
    url: '/projectrole/detectUnitUser/dict',
    method: 'get'
  })
}
