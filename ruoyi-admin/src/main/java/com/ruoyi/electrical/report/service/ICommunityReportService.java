package com.ruoyi.electrical.report.service;

import java.util.List;

import com.ruoyi.electrical.dto.OwnerUnitReportPassDto;
import com.ruoyi.electrical.report.domain.CommunityReport;
import com.ruoyi.electrical.vo.CommunityReportVo;

/**
 * 社区报告Service接口
 * 
 * @author ruoyi
 * @date 2024-10-11
 */
public interface ICommunityReportService {

	List<CommunityReportVo> selectCommunityReportList(CommunityReport report);

	CommunityReport getCommunityReportByAreaAndType(CommunityReport data);

	boolean pass(Long reportId);

	boolean notPass(OwnerUnitReportPassDto data);

	boolean resetStatusByReportId(Long reportId);

	String wordReport(Long reportId);

}
