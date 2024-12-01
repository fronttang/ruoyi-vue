import request from '@/utils/request'

// 查询勘探数据查询列表
export function listProject(query) {
  return request({
    url: '/electricity/project/list',
    method: 'get',
    params: query
  })
}

// 查询电力设计项目设备列表
export function listProjectDevice(query) {
    return request({
      url: '/electricity/project/device/list',
      method: 'get',
      params: query
    })
}


// 查询设备照片集列表
export function listProjectDeviceImage(query) {
    return request({
      url: '/electricity/project/device/image/list',
      method: 'get',
      params: query
    })
}
  