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
// 查询报告日志
export function getReportLogs(reportId) {
  return request({
    url: '/report/log/' + reportId,
    method: 'get'
  })
}
