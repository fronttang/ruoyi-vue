package com.ruoyi.electrical.report.service;

import java.util.List;

import com.ruoyi.electrical.dto.OwnerUnitReportDto;
import com.ruoyi.electrical.dto.OwnerUnitReportPassDto;
import com.ruoyi.electrical.report.domain.OwnerUnitReport;
import com.ruoyi.electrical.vo.OwnerUnitReportVo;

/**
 * 初检报告Service接口
 * 
 * @author fronttang
 * @date 2024-07-15
 */
public interface IOwnerUnitReportService {

	/**
	 * 查询初检报告列表
	 * 
	 * @param ownerUnitReport 初检报告
	 * @return 初检报告集合
	 */
	public List<OwnerUnitReportVo> selectOwnerUnitReportList(OwnerUnitReportDto ownerUnitReport);

	public OwnerUnitReport selectOwnerUnitReportByUnitIdAndType(Long unitId, String type);

	public OwnerUnitReport selectOwnerUnitReportById(Long id);

	/**
	 * 更新报告
	 * 
	 * @param ownerUnitReport
	 * @return
	 */
	public int updateOwnerUnitReport(OwnerUnitReport ownerUnitReport);

	public boolean pass(Long reportId);

	public boolean notPass(OwnerUnitReportPassDto data);

	public boolean resetStatusOwnerUnitReportByUnitIdAndType(Long unitId, String type);

	public boolean setReportDate(OwnerUnitReport report);

	public List<OwnerUnitReportVo> selectOwnerUnitReportListByUnitIds(Long[] unitIds, Long projectId, String type);

}
