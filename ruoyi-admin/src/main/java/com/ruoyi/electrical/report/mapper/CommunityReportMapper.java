package com.ruoyi.electrical.report.mapper;

import java.util.List;

import com.ruoyi.electrical.report.domain.CommunityReport;
import com.ruoyi.electrical.report.dto.CommunityReportOwnerUnit;
import com.ruoyi.electrical.vo.CommunityReportVo;

/**
 * 社区报告Mapper接口
 * 
 * @author ruoyi
 * @date 2024-10-11
 */
public interface CommunityReportMapper {

	List<CommunityReportVo> selectCommunityReportList(CommunityReport report);

	/**
	 * 查询社区报告
	 * 
	 * @param id 社区报告主键
	 * @return 社区报告
	 */
	CommunityReport selectCommunityReportById(Long id);

	CommunityReport getCommunityReportByAreaAndType(CommunityReport data);

	/**
	 * 修改社区报告
	 * 
	 * @param communityReport 社区报告
	 * @return 结果
	 */
	public int updateCommunityReport(CommunityReport communityReport);

	/**
	 * 新增社区报告
	 * 
	 * @param communityReport 社区报告
	 * @return 结果
	 */
	public int insertCommunityReport(CommunityReport communityReport);

	List<CommunityReportOwnerUnit> getCommunityOwnerUnitList(CommunityReport report);

}
