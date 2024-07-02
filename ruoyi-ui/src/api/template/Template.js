import request from '@/utils/request'

// 查询模板列表列表
export function listTemplate(query) {
  return request({
    url: '/template/Template/list',
    method: 'get',
    params: query
  })
}

// 查询模板列表详细
export function getTemplate(id) {
  return request({
    url: '/template/Template/' + id,
    method: 'get'
  })
}

// 新增模板列表
export function addTemplate(data) {
  return request({
    url: '/template/Template',
    method: 'post',
    data: data
  })
}

// 修改模板列表
export function updateTemplate(data) {
  return request({
    url: '/template/Template',
    method: 'put',
    data: data
  })
}

// 删除模板列表
export function delTemplate(id) {
  return request({
    url: '/template/Template/' + id,
    method: 'delete'
  })
}

// 查询模板列表详细
export function getTemplateDict() {
  return request({
    url: '/template/Template/dict',
    method: 'get'
  })
}

// 查询模板列表详细
export function queryTemplateDict(query) {
  return request({
    url: '/template/Template/dict/list',
    method: 'get',
    params: query
  })
}
