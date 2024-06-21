import request from '@/utils/request'

// 查询检测单位列表
export function listDetectUnit(query) {
  return request({
    url: '/projectrole/DetectUnit/list',
    method: 'get',
    params: query
  })
}

// 查询检测单位详细
export function getDetectUnit(id) {
  return request({
    url: '/projectrole/DetectUnit/' + id,
    method: 'get'
  })
}

// 新增检测单位
export function addDetectUnit(data) {
  return request({
    url: '/projectrole/DetectUnit',
    method: 'post',
    data: data
  })
}

// 修改检测单位
export function updateDetectUnit(data) {
  return request({
    url: '/projectrole/DetectUnit',
    method: 'put',
    data: data
  })
}

// 删除检测单位
export function delDetectUnit(id) {
  return request({
    url: '/projectrole/DetectUnit/' + id,
    method: 'delete'
  })
}

// 检测单位字典
export function detectUnitDict() {
  return request({
    url: '/projectrole/DetectUnit/dict',
    method: 'get'
  })
}
