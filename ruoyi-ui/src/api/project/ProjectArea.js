import request from '@/utils/request'

// 查询项目区域列表
export function listProjectArea(query) {
  return request({
    url: '/project/ProjectArea/list',
    method: 'get',
    params: query
  })
}

// 查询项目区域详细
export function getProjectArea(id) {
  return request({
    url: '/project/ProjectArea/' + id,
    method: 'get'
  })
}

// 新增项目区域
export function addProjectArea(data) {
  return request({
    url: '/project/ProjectArea',
    method: 'post',
    data: data
  })
}

// 修改项目区域
export function updateProjectArea(data) {
  return request({
    url: '/project/ProjectArea',
    method: 'put',
    data: data
  })
}

// 删除项目区域
export function delProjectArea(id) {
  return request({
    url: '/project/ProjectArea/' + id,
    method: 'delete'
  })
}

// 查询项目区域字典
export function getProjectAreaDictByProjectId(projectId) {
  return request({
    url: '/project/ProjectArea/dict/' + projectId,
    method: 'get'
  })
}

// 查询项目区域字典
export function getProjectAreaDict() {
  return request({
    url: '/project/ProjectArea/dict',
    method: 'get'
  })
}
