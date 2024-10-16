package com.ruoyi.electrical.report.controller;

import java.io.File;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aspose.words.Document;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.electrical.project.domain.OwnerUnit;
import com.ruoyi.electrical.project.service.IOwnerUnitService;
import com.ruoyi.electrical.report.domain.OwnerUnitReport;
import com.ruoyi.electrical.report.service.IChargingStationInitialReportService;
import com.ruoyi.electrical.report.service.IHighFireRiskInitialReportService;
import com.ruoyi.electrical.report.service.IOwnerUnitReportService;
import com.ruoyi.electrical.report.service.IUrbanVillageUnitInitialReportService;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 初检/复检报告
 */
@Slf4j
@RestController
@RequestMapping("/report/download")
public class UnitReportController extends BaseController {

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
		if ("1".equalsIgnoreCase(report.getType())) {

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
		} else if ("2".equalsIgnoreCase(report.getType())) {

			// 城中村 / 工业园
			if ("1".equalsIgnoreCase(ownerUnit.getType()) || "2".equalsIgnoreCase(ownerUnit.getType())) {
				return toAjax(urbanVillageUnitInitialReportService.reviewReport(reportId));
			} else if ("3".equalsIgnoreCase(ownerUnit.getType())) {
				// 高风险
				return toAjax(fireRiskInitialReportService.reviewReport(reportId));

			} else if ("4".equalsIgnoreCase(ownerUnit.getType())) {
				// 充电站
				return toAjax(chargingStationInitialReportService.reviewReport(reportId));

			} else {
				return AjaxResult.error();
			}
		} else {
			return AjaxResult.error();
		}
	}

	@GetMapping("/archived/pdf/{reportId}")
	public AjaxResult archivedPdf(@PathVariable Long reportId) {
		OwnerUnitReport report = unitReportService.selectOwnerUnitReportById(reportId);
		if (report == null) {
			return AjaxResult.error();
		}

		String archivedWord = report.getArchivedWord();

		if (StrUtil.isBlankIfStr(archivedWord)) {
			return AjaxResult.error("无归档Word报告");
		}

		try {

			// 本地资源路径
			String localPath = RuoYiConfig.getProfile();
			// 数据库资源地址
			String archivedWordPath = localPath + StringUtils.substringAfter(archivedWord, Constants.RESOURCE_PREFIX);

			// 加载Word文档
			// FileInputStream fis = new FileInputStream(new File(archivedWordPath));
			// XWPFDocument document = new XWPFDocument(fis);

			LocalDateTime now = LocalDateTime.now();
			String timestamp = DateUtil.format(now, DatePattern.PURE_DATETIME_MS_PATTERN);
			String fileName = timestamp + IdUtils.fastSimpleUUID().toUpperCase() + ".pdf";
			String datePath = DateUtil.format(now, "yyyy/MM/dd");

			String filePath = StrUtil.format("{}/{}", datePath, fileName);

			String baseDir = RuoYiConfig.getUploadPath();
			File saveFile = FileUploadUtils.getAbsoluteFile(baseDir, filePath);

			Document doc = new Document(archivedWordPath);
			doc.save(saveFile.getAbsolutePath());

			log.info("Word文档已成功转换为PDF！");

			OwnerUnitReport result = new OwnerUnitReport();
			result.setId(reportId);
			result.setArchivedPdf(FileUploadUtils.getPathFileName(baseDir, filePath));

			return toAjax(unitReportService.updateOwnerUnitReport(result));

		} catch (Exception e) {
			log.error("", e);
		}
		return AjaxResult.error();
	}
}