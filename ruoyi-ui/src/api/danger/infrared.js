import request from '@/utils/request'

// 查询红外判定列表
export function listInfrared(query) {
  return request({
    url: '/danger/infrared/list',
    method: 'get',
    params: query
  })
}

// 查询红外判定详细
export function getInfrared(id) {
  return request({
    url: '/danger/infrared/' + id,
    method: 'get'
  })
}

// 修改红外判定
export function updateInfrared(data) {
  return request({
    url: '/danger/infrared',
    method: 'put',
    data: data
  })
}

