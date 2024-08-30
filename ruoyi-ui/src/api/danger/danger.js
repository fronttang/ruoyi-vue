import request from '@/utils/request'

// 查询隐患数据列表
export function listDanger(query) {
  return request({
    url: '/danger/list',
    method: 'get',
    params: query
  })
}

// 查询隐患数据列表
export function listUnitDanger(query) {
  return request({
    url: '/danger/unit/list',
    method: 'get',
    params: query
  })
}

export function listUnitBuildingDanger(query) {
  return request({
    url: '/danger/unit/building/list',
    method: 'get',
    params: query
  })
}

// 查询隐患数据详细
export function getDanger(id) {
  return request({
    url: '/danger/' + id,
    method: 'get'
  })
}

// 楼栋字典
export function getBuildingDict(unitId) {
  return request({
    url: '/danger/building/dict/' + unitId,
    method: 'get'
  })
}

export function exportMissDevice(query) {
  return request({
    url: '/miss/device/export',
    method: 'get'
  })
}

export function resetStatus(ids, status){
  return request({
    url: '/danger/reset/' + ids + '/' + status,
    method: 'get'
  })
}

export function unitPictures(unitId){
  return request({
    url: '/danger/pictures/' + unitId,
    method: 'get'
  })
}

export function unitBuildingPictures(unitId, buildingId){
  return request({
    url: '/danger/pictures/' + unitId + '/' + buildingId,
    method: 'get'
  })
}

export function dangerExport(query){
  return request({
    url: '/owner/unit/danger/export',
    method: 'get',
    params: query
  })
}


export function dangerReivewExport(query){
  return request({
    url: '/owner/unit/danger/export/review',
    method: 'get',
    params: query
  })
}