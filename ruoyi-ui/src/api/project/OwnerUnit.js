import request from '@/utils/request'

// 查询城中村电检列表
export function listOwnerUnit(query) {
  return request({
    url: '/project/OwnerUnit/list',
    method: 'get',
    params: query
  })
}

// 查询城中村电检详细
export function getOwnerUnit(id) {
  return request({
    url: '/project/OwnerUnit/' + id,
    method: 'get'
  })
}

// 新增城中村电检
export function addOwnerUnit(data) {
  return request({
    url: '/project/OwnerUnit',
    method: 'post',
    data: data
  })
}

// 修改城中村电检
export function updateOwnerUnit(data) {
  return request({
    url: '/project/OwnerUnit',
    method: 'put',
    data: data
  })
}

// 删除城中村电检
export function delOwnerUnit(id) {
  return request({
    url: '/project/OwnerUnit/' + id,
    method: 'delete'
  })
}
