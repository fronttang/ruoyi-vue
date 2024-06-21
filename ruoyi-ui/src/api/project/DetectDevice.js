import request from '@/utils/request'

// 查询检测仪器列表
export function listDetectDevice(query) {
  return request({
    url: '/project/DetectDevice/list',
    method: 'get',
    params: query
  })
}

// 查询检测仪器详细
export function getDetectDevice(id) {
  return request({
    url: '/project/DetectDevice/' + id,
    method: 'get'
  })
}

// 新增检测仪器
export function addDetectDevice(data) {
  return request({
    url: '/project/DetectDevice',
    method: 'post',
    data: data
  })
}

// 修改检测仪器
export function updateDetectDevice(data) {
  return request({
    url: '/project/DetectDevice',
    method: 'put',
    data: data
  })
}

// 删除检测仪器
export function delDetectDevice(id) {
  return request({
    url: '/project/DetectDevice/' + id,
    method: 'delete'
  })
}
