package com.ruoyi.electrical.report.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.electrical.dto.OwnerUnitReportDto;
import com.ruoyi.electrical.report.domain.OwnerUnitReport;
import com.ruoyi.electrical.vo.OwnerUnitReivewDateVo;
import com.ruoyi.electrical.vo.OwnerUnitReportVo;

/**
 * 初检报告Mapper接口
 * 
 * @author fronttang
 * @date 2024-07-15
 */
public interface OwnerUnitReportMapper {

	/**
	 * 查询初检报告列表
	 * 
	 * @param ownerUnitReport 初检报告
	 * @return 初检报告集合
	 */
	public List<OwnerUnitReportVo> selectOwnerUnitReportList(OwnerUnitReportDto ownerUnitReportDto);

	/**
	 * 新增初检报告
	 * 
	 * @param ownerUnitReport 初检报告
	 * @return 结果
	 */
	public int insertOwnerUnitReport(OwnerUnitReport ownerUnitReport);

	/**
	 * 更新报告
	 * 
	 * @param ownerUnitReport
	 * @return
	 */
	public int updateOwnerUnitReport(OwnerUnitReport ownerUnitReport);

	public OwnerUnitReport selectOwnerUnitReportByUnitIdAndType(@Param("unitId") Long unitId,
			@Param("type") String type);

	public OwnerUnitReport selectOwnerUnitReportById(Long id);

	public OwnerUnitReivewDateVo getOwnerUnitReviewDate(@Param("unitId") Long unitId);

	public OwnerUnitReivewDateVo getOwnerUnitReviewer(@Param("unitId") Long unitId);

	public List<OwnerUnitReportVo> selectOwnerUnitReportListByUnitIds(@Param("unitIds") Long[] unitIds,
			@Param("projectId") Long projectId, @Param("type") String type);

}
