import request from '@/utils/request'

// 查询光伏参数列表
export function listPhotovoltaicConfig(query) {
  return request({
    url: '/storage/photovoltaic/config/list',
    method: 'get',
    params: query
  })
}

// 查询光伏参数详细
export function getPhotovoltaicConfig(id) {
  return request({
    url: '/storage/photovoltaic/config/' + id,
    method: 'get'
  })
}

// 新增光伏参数
export function addPhotovoltaicConfig(data) {
  return request({
    url: '/storage/photovoltaic/config',
    method: 'post',
    data: data
  })
}

// 修改光伏参数
export function updatePhotovoltaicConfig(data) {
  return request({
    url: '/storage/photovoltaic/config',
    method: 'put',
    data: data
  })
}

// 删除光伏参数
export function delPhotovoltaicConfig(id) {
  return request({
    url: '/storage/photovoltaic/config/' + id,
    method: 'delete'
  })
}

// 光伏省市字典
export function photovoltaicAreaDict() {
  return request({
    url: '/storage/photovoltaic/config/dict',
    method: 'get'
  })
}
