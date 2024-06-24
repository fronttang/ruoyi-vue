import request from '@/utils/request'

// 查询检测内容隐患列表
export function listIntuitiveDetectDanger(query) {
  return request({
    url: '/template/IntuitiveDetectDanger/list',
    method: 'get',
    params: query
  })
}

// 查询检测内容隐患详细
export function getIntuitiveDetectDanger(id) {
  return request({
    url: '/template/IntuitiveDetectDanger/' + id,
    method: 'get'
  })
}

// 新增检测内容隐患
export function addIntuitiveDetectDanger(data) {
  return request({
    url: '/template/IntuitiveDetectDanger',
    method: 'post',
    data: data
  })
}

// 修改检测内容隐患
export function updateIntuitiveDetectDanger(data) {
  return request({
    url: '/template/IntuitiveDetectDanger',
    method: 'put',
    data: data
  })
}

// 删除检测内容隐患
export function delIntuitiveDetectDanger(id) {
  return request({
    url: '/template/IntuitiveDetectDanger/' + id,
    method: 'delete'
  })
}
