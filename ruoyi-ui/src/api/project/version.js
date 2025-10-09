import request from '@/utils/request'

// 查询版本更新列表
export function listVersion(query) {
  return request({
    url: '/project/version/list',
    method: 'get',
    params: query
  })
}

// 查询版本更新详细
export function getVersion(id) {
  return request({
    url: '/project/version/' + id,
    method: 'get'
  })
}

// 新增版本更新
export function addVersion(data) {
  return request({
    url: '/project/version',
    method: 'post',
    data: data
  })
}

// 修改版本更新
export function updateVersion(data) {
  return request({
    url: '/project/version',
    method: 'put',
    data: data
  })
}

// 删除版本更新
export function delVersion(id) {
  return request({
    url: '/project/version/' + id,
    method: 'delete'
  })
}

export function latestVersion(client) {
  return request({
    url: '/project/version/latest?client=' + client,
    method: 'get'
  })
}
