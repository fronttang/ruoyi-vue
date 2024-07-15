package com.ruoyi.electrical.report.service;

import java.util.List;

import com.ruoyi.electrical.report.domain.OwnerUnitReport;
import com.ruoyi.electrical.report.domain.OwnerUnitReportLog;

/**
 * 报告日志Service接口
 * 
 * @author fronttang
 * @date 2024-07-15
 */
public interface IOwnerUnitReportLogService {
	/**
	 * 查询报告日志
	 * 
	 * @param id 报告日志主键
	 * @return 报告日志
	 */
	public OwnerUnitReportLog selectOwnerUnitReportLogById(Long id);

	/**
	 * 查询报告日志列表
	 * 
	 * @param ownerUnitReportLog 报告日志
	 * @return 报告日志集合
	 */
	public List<OwnerUnitReportLog> selectOwnerUnitReportLogList(OwnerUnitReportLog ownerUnitReportLog);

	/**
	 * 新增报告日志
	 * 
	 * @param ownerUnitReportLog 报告日志
	 * @return 结果
	 */
	public int insertOwnerUnitReportLog(OwnerUnitReportLog ownerUnitReportLog);

	/**
	 * 修改报告日志
	 * 
	 * @param ownerUnitReportLog 报告日志
	 * @return 结果
	 */
	public int updateOwnerUnitReportLog(OwnerUnitReportLog ownerUnitReportLog);

	/**
	 * 批量删除报告日志
	 * 
	 * @param ids 需要删除的报告日志主键集合
	 * @return 结果
	 */
	public int deleteOwnerUnitReportLogByIds(Long[] ids);

	/**
	 * 删除报告日志信息
	 * 
	 * @param id 报告日志主键
	 * @return 结果
	 */
	public int deleteOwnerUnitReportLogById(Long id);

	public List<OwnerUnitReportLog> selectOwnerUnitReportLogByReportId(Long reportId);

}
