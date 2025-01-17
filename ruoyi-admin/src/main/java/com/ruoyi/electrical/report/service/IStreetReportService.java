package com.ruoyi.electrical.report.service;

import java.util.List;

import com.ruoyi.electrical.dto.OwnerUnitReportPassDto;
import com.ruoyi.electrical.report.domain.StreetReport;
import com.ruoyi.electrical.vo.StreetReportVo;

/**
 * 街道报告Service接口
 * 
 * @author ruoyi
 * @date 2024-10-11
 */
public interface IStreetReportService {

	List<StreetReportVo> selectStreetReportList(StreetReport report);

	StreetReport getStreetReportByAreaAndType(StreetReport data);

	boolean pass(Long reportId);

	boolean notPass(OwnerUnitReportPassDto data);

	boolean resetStatusByReportId(Long reportId);

	String wordReport(Long reportId);

}
