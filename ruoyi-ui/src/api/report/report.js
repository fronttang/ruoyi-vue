import request from '@/utils/request'

// 查询初检报告列表
export function listReport(query) {
  return request({
    url: '/report/list',
    method: 'get',
    params: query
  })
}
// 查询报告日志
export function getReport(query) {
  return request({
    url: '/report/info',
    method: 'get',
    params: query
  })
}

export function getWordReport(unitId, type) {
  return request({
    url: '/report/download/' + unitId + '/' + type,
    method: 'get'
  })
}

export function archivedPdf(unitId, type) {
  return request({
    url: '/report/download/archived/pdf/' + unitId + '/' + type,
    method: 'get'
  })
}
// 查询报告日志
export function getReportLogs(unitId, type) {
  return request({
    url: '/report/log/' + unitId + '/' + type,
    method: 'get'
  })
}

export function passReport(unitId, type) {
  return request({
    url: '/report/pass/' + unitId + '/' + type,
    method: 'get'
  })
}

export function notPassReport(data) {
  return request({
    url: '/report/notpass/',
    method: 'post',
    params: data
  })
}

export function resetReportStatus(unitId, type) {
  return request({
    url: '/report/reset/status/' + unitId +'/' + type,
    method: 'get'
  })
}

export function setReportDate(data) {
  return request({
    url: '/report/report/date',
    method: 'post',
    params: data
  })
}

export function getOriginalRecords(unitId, type) {
  return request({
    url: '/report/download/originalRecords/' + unitId +'/' + type,
    method: 'get'
  })
}

export function batchPass(data) {
  return request({
    url: '/report/batch/pass',
    method: 'post',
    data: data
  })
}

export function batchNotPass(data) {
  return request({
    url: '/report/batch/notpass',
    method: 'post',
    data: data
  })
}

export function batchSetReportDate(data) {
  return request({
    url: '/report/batch/date',
    method: 'post',
    data: data
  })
}

export function batchDownload(data, type) {
  return request({
    url: '/report/batch/download/' + type,
    method: 'post',
    data: data
  })
}
