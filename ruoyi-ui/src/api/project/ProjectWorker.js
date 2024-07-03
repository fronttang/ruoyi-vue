import request from '@/utils/request'

// 查询项目工作人员列表
export function listProjectWorker(query) {
  return request({
    url: '/project/ProjectWorker/list',
    method: 'get',
    params: query
  })
}

// 查询项目工作人员详细
export function getProjectWorker(id) {
  return request({
    url: '/project/ProjectWorker/' + id,
    method: 'get'
  })
}

// 新增项目工作人员
export function addProjectWorker(data) {
  return request({
    url: '/project/ProjectWorker',
    method: 'post',
    data: data
  })
}

// 修改项目工作人员
export function updateProjectWorker(data) {
  return request({
    url: '/project/ProjectWorker',
    method: 'put',
    data: data
  })
}

// 删除项目工作人员
export function delProjectWorker(id) {
  return request({
    url: '/project/ProjectWorker/' + id,
    method: 'delete'
  })
}

// 修改项目工作人员区域
export function saveProjectWorkerArea(data) {
  return request({
    url: '/project/ProjectWorker/area',
    method: 'post',
    data: data
  })
}
