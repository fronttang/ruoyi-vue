package com.ruoyi.electrical.report.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.electrical.dto.OwnerUnitReportPassDto;
import com.ruoyi.electrical.report.domain.CommunityReport;
import com.ruoyi.electrical.report.domain.OwnerUnitReportLog;
import com.ruoyi.electrical.report.service.ICommunityReportService;
import com.ruoyi.electrical.report.service.IOwnerUnitReportLogService;
import com.ruoyi.electrical.vo.CommunityReportVo;

import cn.hutool.core.util.StrUtil;

/**
 * 街道报告
 */
@RestController
@RequestMapping("/street/report")
public class StreetReportController extends BaseController {

	@Autowired
	private ICommunityReportService communityReportService;

	@Autowired
	private IOwnerUnitReportLogService ownerUnitReportLogService;

	@RequestMapping("/list")
	public TableDataInfo list(CommunityReport report) {
		startPage();
		List<CommunityReportVo> list = communityReportService.selectCommunityReportList(report);
		return getDataTable(list);
	}

	@GetMapping("/info")
	public AjaxResult info(CommunityReport data) {
		CommunityReport report = communityReportService.getCommunityReportByAreaAndType(data);
		return AjaxResult.success(report);
	}

	@GetMapping("/log/{reportId}")
	public AjaxResult reportLogList(@PathVariable Long reportId) {
		List<OwnerUnitReportLog> logs = ownerUnitReportLogService.selectOwnerUnitReportLogByReportId(reportId);
		return AjaxResult.success(logs);
	}

	/**
	 * 通过审核
	 * 
	 * @return
	 */
	@GetMapping("/pass/{reportId}")
	public AjaxResult pass(@PathVariable Long reportId) {

		communityReportService.pass(reportId);

		return AjaxResult.success();
	}

	/**
	 * 不通过
	 * 
	 * @param data
	 * @return
	 */
	@PostMapping("/notpass")
	public AjaxResult notPass(OwnerUnitReportPassDto data) {

		communityReportService.notPass(data);

		return AjaxResult.success();
	}

	@GetMapping("/reset/status/{reportId}")
	public AjaxResult resetStatus(@PathVariable("reportId") Long reportId) {
		communityReportService.resetStatusByReportId(reportId);
		return AjaxResult.success();
	}

	/**
	 * @param reportId
	 */
	@RequestMapping("/download/{reportId}")
	public AjaxResult wordReport(@PathVariable Long reportId) {
		String path = communityReportService.wordReport(reportId);
		if (StrUtil.isBlank(path)) {
			return AjaxResult.error();
		}
		AjaxResult success = success();
		success.put("data", path);
		return success;
	}
}
