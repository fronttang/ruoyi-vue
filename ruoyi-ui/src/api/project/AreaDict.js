import request from '@/utils/request'

// 查询区域字典列表
export function listAreaDict(query) {
  return request({
    url: '/project/AreaDict/list',
    method: 'get',
    params: query
  })
}

// 查询区域字典列表
export function listProjectAreaDict(query) {
  return request({
    url: '/project/AreaDict/dict/list',
    method: 'get',
    params: query
  })
}

// 查询区域字典详细
export function getAreaDict(dictCode) {
  return request({
    url: '/project/AreaDict/' + dictCode,
    method: 'get'
  })
}

// 新增区域字典
export function addAreaDict(data) {
  return request({
    url: '/project/AreaDict',
    method: 'post',
    data: data
  })
}

// 修改区域字典
export function updateAreaDict(data) {
  return request({
    url: '/project/AreaDict',
    method: 'put',
    data: data
  })
}

// 删除区域字典
export function delAreaDict(dictCode) {
  return request({
    url: '/project/AreaDict/' + dictCode,
    method: 'delete'
  })
}

export const districtDictData = {
      "dictSort": 0,
      "dictLabel": "区",
      "dictValue": "district",
      "dictType": "area_type",
      "cssClass": null,
      "listClass": "default",
      "isDefault": "N",
      "status": "0",
      "default": false
}

export const streetDictData = {
      "dictSort": 0,
      "dictLabel": "街道",
      "dictValue": "street",
      "dictType": "area_type",
      "cssClass": null,
      "listClass": "default",
      "isDefault": "N",
      "status": "0",
      "default": false
}

export const communityDictData = {
      "dictSort": 0,
      "dictLabel": "社区",
      "dictValue": "community",
      "dictType": "area_type",
      "cssClass": null,
      "listClass": "default",
      "isDefault": "N",
      "status": "0",
      "default": false
}

export const hamletDictData = {
      "dictSort": 0,
      "dictLabel": "村",
      "dictValue": "hamlet",
      "dictType": "area_type",
      "cssClass": null,
      "listClass": "default",
      "isDefault": "N",
      "status": "0",
      "default": false
}
