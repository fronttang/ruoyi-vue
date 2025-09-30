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

// 开始推进轮次
export function startRounds(id) {
  return request({
    url: '/project/OwnerUnit/rounds/' + id,
    method: 'get'
  })
}

// 轮次推进步骤
export function roundsStep(id) {
  return request({
    url: '/project/OwnerUnit/rounds/step/' + id,
    method: 'get'
  })
}

export function setGridmain(type, gridman, unitIds) {
  return request({
    url: '/project/OwnerUnit/gridman/' + type + '/' + gridman + '/' + unitIds,
    method: 'get'
  })
}
