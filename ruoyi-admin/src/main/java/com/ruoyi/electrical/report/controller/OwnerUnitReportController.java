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
import com.ruoyi.electrical.dto.OwnerUnitReportDto;
import com.ruoyi.electrical.dto.OwnerUnitReportPassDto;
import com.ruoyi.electrical.report.domain.OwnerUnitReport;
import com.ruoyi.electrical.report.domain.OwnerUnitReportLog;
import com.ruoyi.electrical.report.service.IOwnerUnitReportLogService;
import com.ruoyi.electrical.report.service.IOwnerUnitReportService;
import com.ruoyi.electrical.vo.OwnerUnitReportVo;

/**
 * 初检报告Controller
 * 
 * @author fronttang
 * @date 2024-07-15
 */
@RestController
@RequestMapping("/report")
public class OwnerUnitReportController extends BaseController {

	@Autowired
	private IOwnerUnitReportService ownerUnitReportService;

	@Autowired
	private IOwnerUnitReportLogService ownerUnitReportLogService;

	/**
	 * 查询初检报告列表
	 */
	@GetMapping("/list")
	public TableDataInfo list(OwnerUnitReportDto dto) {
		startPage();
		List<OwnerUnitReportVo> list = ownerUnitReportService.selectOwnerUnitReportList(dto);
		return getDataTable(list);
	}

	@GetMapping("/info")
	public AjaxResult info(OwnerUnitReportDto dto) {
		OwnerUnitReport report = ownerUnitReportService.selectOwnerUnitReportByUnitIdAndType(dto.getUnitId(),
				dto.getType());
		return AjaxResult.success(report);
	}

	@GetMapping("/log/{unitId}/{type}")
	public AjaxResult reportLogList(@PathVariable Long unitId, @PathVariable String type) {

		OwnerUnitReport report = ownerUnitReportService.selectOwnerUnitReportByUnitIdAndType(unitId, type);
		if (report == null) {
			return AjaxResult.success();
		}

		List<OwnerUnitReportLog> logs = ownerUnitReportLogService.selectOwnerUnitReportLogByReportId(report.getId());

		return AjaxResult.success(logs);
	}

	/**
	 * 通过审核
	 * 
	 * @return
	 */
	@GetMapping("/pass/{unitId}/{type}")
	public AjaxResult pass(@PathVariable Long unitId, @PathVariable String type) {

		OwnerUnitReport report = ownerUnitReportService.selectOwnerUnitReportByUnitIdAndType(unitId, type);
		if (report == null) {
			return AjaxResult.error();
		}

		ownerUnitReportService.pass(report.getId());

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

		OwnerUnitReport report = ownerUnitReportService.selectOwnerUnitReportByUnitIdAndType(data.getUnitId(),
				data.getType());
		if (report == null) {
			return AjaxResult.error();
		}

		data.setReportId(report.getId());

		ownerUnitReportService.notPass(data);

		return AjaxResult.success();
	}

	@GetMapping("/reset/status/{unitId}/{type}")
	public AjaxResult resetStatus(@PathVariable("unitId") Long unitId, @PathVariable("type") String type) {
		ownerUnitReportService.resetStatusOwnerUnitReportByUnitIdAndType(unitId, type);
		return AjaxResult.success();
	}

	@PostMapping("/report/date")
	public AjaxResult setReportDate(OwnerUnitReport dto) {

		OwnerUnitReport report = ownerUnitReportService.selectOwnerUnitReportByUnitIdAndType(dto.getUnitId(),
				dto.getType());
		if (report == null) {
			return AjaxResult.error();
		}

		dto.setId(report.getId());

		ownerUnitReportService.setReportDate(dto);

		return AjaxResult.success();
	}

}
