import request from '@/utils/request'

// 查询社区报告列表
export function listReport(query) {
  return request({
    url: '/community/report/list',
    method: 'get',
    params: query
  })
}

// 查询社区报告详细
export function getReport(query) {
  return request({
    url: '/community/report/info',
    method: 'get',
    params: query
  })
}

// 查询报告日志
export function getReportLogs(reportId) {
  return request({
    url: '/community/report/log/' + reportId,
    method: 'get'
  })
}

export function passReport(reportId) {
  return request({
    url: '/community/report/pass/' + reportId,
    method: 'get'
  })
}

export function notPassReport(data) {
  return request({
    url: '/community/report/notpass',
    method: 'post',
    params: data
  })
}

export function resetReportStatus(reportId) {
  return request({
    url: '/community/report/reset/status/' + reportId,
    method: 'get'
  })
}

export function getWordReport(reportId) {
  return request({
    url: '/community/report/download/' + reportId,
    method: 'get'
  })
}
