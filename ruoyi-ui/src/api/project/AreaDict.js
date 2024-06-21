import request from '@/utils/request'

// 查询区域字典列表
export function listAreaDict(query) {
  return request({
    url: '/project/AreaDict/list',
    method: 'get',
    params: query
  })
}

// 查询区域字典详细
export function getAreaDict(dictCode) {
  return request({
    url: '/project/AreaDict/' + dictCode,
    method: 'get'
  })
}

// 新增区域字典
export function addAreaDict(data) {
  return request({
    url: '/project/AreaDict',
    method: 'post',
    data: data
  })
}

// 修改区域字典
export function updateAreaDict(data) {
  return request({
    url: '/project/AreaDict',
    method: 'put',
    data: data
  })
}

// 删除区域字典
export function delAreaDict(dictCode) {
  return request({
    url: '/project/AreaDict/' + dictCode,
    method: 'delete'
  })
}
