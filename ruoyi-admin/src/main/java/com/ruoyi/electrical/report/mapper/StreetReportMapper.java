package com.ruoyi.electrical.report.mapper;

import java.util.List;

import com.ruoyi.electrical.report.domain.StreetReport;
import com.ruoyi.electrical.report.dto.StreetReportOwnerUnit;
import com.ruoyi.electrical.vo.StreetReportVo;

/**
 * 街道报告Mapper接口
 * 
 * @author ruoyi
 * @date 2024-10-11
 */
public interface StreetReportMapper {

	List<StreetReportVo> selectStreetReportList(StreetReport report);

	/**
	 * 查询街道报告
	 * 
	 * @param id 街道报告主键
	 * @return 街道报告
	 */
	StreetReport selectStreetReportById(Long id);

	StreetReport getStreetReportByAreaAndType(StreetReport data);

	/**
	 * 修改街道报告
	 * 
	 * @param communityReport 街道报告
	 * @return 结果
	 */
	public int updateStreetReport(StreetReport streetReport);

	/**
	 * 新增街道报告
	 * 
	 * @param communityReport 街道报告
	 * @return 结果
	 */
	public int insertStreetReport(StreetReport streetReport);

	List<StreetReportOwnerUnit> getStreetOwnerUnitList(StreetReport report);

}
