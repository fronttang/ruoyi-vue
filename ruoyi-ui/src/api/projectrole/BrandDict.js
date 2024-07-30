import request from '@/utils/request'

// 查询品牌生产厂家列表
export function listBrandDict(query) {
  return request({
    url: '/role/BrandDict/list',
    method: 'get',
    params: query
  })
}

// 查询品牌生产厂家详细
export function getBrandDict(dictCode) {
  return request({
    url: '/role/BrandDict/' + dictCode,
    method: 'get'
  })
}

// 新增品牌生产厂家
export function addBrandDict(data) {
  return request({
    url: '/role/BrandDict',
    method: 'post',
    data: data
  })
}

// 修改品牌生产厂家
export function updateBrandDict(data) {
  return request({
    url: '/role/BrandDict',
    method: 'put',
    data: data
  })
}

// 删除品牌生产厂家
export function delBrandDict(dictCode) {
  return request({
    url: '/role/BrandDict/' + dictCode,
    method: 'delete'
  })
}
