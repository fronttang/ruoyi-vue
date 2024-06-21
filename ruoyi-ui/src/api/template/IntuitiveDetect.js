import request from '@/utils/request'

// 查询直观检测标题列表
export function listIntuitiveDetect(query) {
  return request({
    url: '/template/IntuitiveDetect/list',
    method: 'get',
    params: query
  })
}

// 查询直观检测标题详细
export function getIntuitiveDetect(id) {
  return request({
    url: '/template/IntuitiveDetect/' + id,
    method: 'get'
  })
}

// 新增直观检测标题
export function addIntuitiveDetect(data) {
  return request({
    url: '/template/IntuitiveDetect',
    method: 'post',
    data: data
  })
}

// 修改直观检测标题
export function updateIntuitiveDetect(data) {
  return request({
    url: '/template/IntuitiveDetect',
    method: 'put',
    data: data
  })
}

// 删除直观检测标题
export function delIntuitiveDetect(id) {
  return request({
    url: '/template/IntuitiveDetect/' + id,
    method: 'delete'
  })
}
