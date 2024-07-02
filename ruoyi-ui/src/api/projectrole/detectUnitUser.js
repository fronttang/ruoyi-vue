import request from '@/utils/request'

// 查询检测单位账号列表
export function listDetectUnitUser(query) {
  return request({
    url: '/electrical/user/list',
    method: 'get',
    params: query
  })
}

// 查询检测单位账号详细
export function getDetectUnitUser(id) {
  return request({
    url: '/electrical/user/' + id,
    method: 'get'
  })
}

// 新增检测单位账号
export function addDetectUnitUser(data) {
  return request({
    url: '/electrical/user',
    method: 'post',
    data: data
  })
}

// 修改检测单位账号
export function updateDetectUnitUser(data) {
  return request({
    url: '/electrical/user',
    method: 'put',
    data: data
  })
}

// 删除检测单位账号
export function delDetectUnitUser(id) {
  return request({
    url: '/electrical/user/' + id,
    method: 'delete'
  })
}

// 查询账号字典
export function getDetectUnitUserDictByTypeAndProjectId(type, projectId) {
  return request({
    url: '/electrical/user/dict/' + type + '/' + projectId,
    method: 'get'
  })
}

// 查询账号字典
export function getDetectUnitUserDict() {
  return request({
    url: '/electrical/user/dict',
    method: 'get'
  })
}
