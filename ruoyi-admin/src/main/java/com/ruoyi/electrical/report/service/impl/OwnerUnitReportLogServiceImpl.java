package com.ruoyi.electrical.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.electrical.report.domain.OwnerUnitReportLog;
import com.ruoyi.electrical.report.mapper.OwnerUnitReportLogMapper;
import com.ruoyi.electrical.report.service.IOwnerUnitReportLogService;

/**
 * 报告日志Service业务层处理
 * 
 * @author fronttang
 * @date 2024-07-15
 */
@Service
public class OwnerUnitReportLogServiceImpl implements IOwnerUnitReportLogService {

	@Autowired
	private OwnerUnitReportLogMapper ownerUnitReportLogMapper;

	/**
	 * 查询报告日志
	 * 
	 * @param id 报告日志主键
	 * @return 报告日志
	 */
	@Override
	public OwnerUnitReportLog selectOwnerUnitReportLogById(Long id) {
		return ownerUnitReportLogMapper.selectOwnerUnitReportLogById(id);
	}

	/**
	 * 查询报告日志列表
	 * 
	 * @param ownerUnitReportLog 报告日志
	 * @return 报告日志
	 */
	@Override
	public List<OwnerUnitReportLog> selectOwnerUnitReportLogList(OwnerUnitReportLog ownerUnitReportLog) {
		return ownerUnitReportLogMapper.selectOwnerUnitReportLogList(ownerUnitReportLog);
	}

	/**
	 * 新增报告日志
	 * 
	 * @param ownerUnitReportLog 报告日志
	 * @return 结果
	 */
	@Override
	public int insertOwnerUnitReportLog(OwnerUnitReportLog ownerUnitReportLog) {
		ownerUnitReportLog.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
		ownerUnitReportLog.setCreateTime(DateUtils.getNowDate());
		ownerUnitReportLog.setUpdateTime(DateUtils.getNowDate());
		return ownerUnitReportLogMapper.insertOwnerUnitReportLog(ownerUnitReportLog);
	}

	/**
	 * 修改报告日志
	 * 
	 * @param ownerUnitReportLog 报告日志
	 * @return 结果
	 */
	@Override
	public int updateOwnerUnitReportLog(OwnerUnitReportLog ownerUnitReportLog) {
		ownerUnitReportLog.setUpdateTime(DateUtils.getNowDate());
		return ownerUnitReportLogMapper.updateOwnerUnitReportLog(ownerUnitReportLog);
	}

	/**
	 * 批量删除报告日志
	 * 
	 * @param ids 需要删除的报告日志主键
	 * @return 结果
	 */
	@Override
	public int deleteOwnerUnitReportLogByIds(Long[] ids) {
		return ownerUnitReportLogMapper.deleteOwnerUnitReportLogByIds(ids);
	}

	/**
	 * 删除报告日志信息
	 * 
	 * @param id 报告日志主键
	 * @return 结果
	 */
	@Override
	public int deleteOwnerUnitReportLogById(Long id) {
		return ownerUnitReportLogMapper.deleteOwnerUnitReportLogById(id);
	}

	@Override
	public List<OwnerUnitReportLog> selectOwnerUnitReportLogByReportId(Long reportId) {
		return ownerUnitReportLogMapper.selectOwnerUnitReportLogByReportId(reportId);
	}

}
