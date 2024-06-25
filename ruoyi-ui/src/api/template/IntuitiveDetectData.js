import request from '@/utils/request'

// 查询直观检测表内容列表
export function listIntuitiveDetectData(query) {
  return request({
    url: '/template/IntuitiveDetectData/list',
    method: 'get',
    params: query
  })
}

// 查询直观检测表内容列表
export function listIntuitiveDetectViewData(query) {
  return request({
    url: '/template/IntuitiveDetectData/list/view',
    method: 'get',
    params: query
  })
}

// 查询直观检测表内容详细
export function getIntuitiveDetectData(id) {
  return request({
    url: '/template/IntuitiveDetectData/' + id,
    method: 'get'
  })
}

// 查询直观检测表内容详细
export function getIntuitiveDetectDataView(id) {
  return request({
    url: '/template/IntuitiveDetectData/view/' + id,
    method: 'get'
  })
}

// 新增直观检测表内容
export function addIntuitiveDetectData(data) {
  return request({
    url: '/template/IntuitiveDetectData',
    method: 'post',
    data: data
  })
}

// 修改直观检测表内容
export function updateIntuitiveDetectData(data) {
  return request({
    url: '/template/IntuitiveDetectData',
    method: 'put',
    data: data
  })
}

// 删除直观检测表内容
export function delIntuitiveDetectData(id) {
  return request({
    url: '/template/IntuitiveDetectData/' + id,
    method: 'delete'
  })
}

// 删除直观检测表内容
export function delIntuitiveDetectDataView(id) {
  return request({
    url: '/template/IntuitiveDetectData/view/' + id,
    method: 'delete'
  })
}

// 查询直观检测表内容列表
export function getIntuitiveDetectDataDict(query) {
  return request({
    url: '/template/IntuitiveDetectData/dict',
    method: 'get',
    params: query
  })
}
