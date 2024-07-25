package com.ruoyi.electrical.report.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.electrical.project.domain.OwnerUnit;
import com.ruoyi.electrical.project.service.IOwnerUnitService;
import com.ruoyi.electrical.report.domain.OwnerUnitReport;
import com.ruoyi.electrical.report.service.IChargingStationInitialReportService;
import com.ruoyi.electrical.report.service.IHighFireRiskInitialReportService;
import com.ruoyi.electrical.report.service.IOwnerUnitReportService;
import com.ruoyi.electrical.report.service.IUrbanVillageUnitInitialReportService;

import lombok.extern.slf4j.Slf4j;

/**
 * 初检报告
 */
@RestController
@RequestMapping("/report/download/initial")
public class InitialReportController extends BaseController {

	@Autowired
	private IOwnerUnitReportService unitReportService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IUrbanVillageUnitInitialReportService urbanVillageUnitInitialReportService;

	@Autowired
	private IHighFireRiskInitialReportService fireRiskInitialReportService;

	@Autowired
	private IChargingStationInitialReportService chargingStationInitialReportService;

	/**
	 * @param reportId
	 */
	@RequestMapping("/{reportId}")
	public AjaxResult initialReport(@PathVariable Long reportId, HttpServletRequest request,
			HttpServletResponse response) {

		OwnerUnitReport report = unitReportService.selectOwnerUnitReportById(reportId);
		if (report == null) {
			return AjaxResult.error();
		}

		OwnerUnit ownerUnit = ownerUnitService.selectOwnerUnitById(report.getUnitId());

		if (ownerUnit == null) {
			return AjaxResult.error();
		}

		// 城中村 / 工业园
		if ("1".equalsIgnoreCase(ownerUnit.getType()) || "2".equalsIgnoreCase(ownerUnit.getType())) {
			return toAjax(urbanVillageUnitInitialReportService.initialReport(reportId));
		} else if ("3".equalsIgnoreCase(ownerUnit.getType())) {
			// 高风险
			return toAjax(fireRiskInitialReportService.initialReport(reportId));

		} else if ("4".equalsIgnoreCase(ownerUnit.getType())) {
			// 充电站
			return toAjax(chargingStationInitialReportService.initialReport(reportId));

		} else {
			return AjaxResult.error();
		}
	}

}