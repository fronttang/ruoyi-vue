package com.ruoyi.electrical.report.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.danger.mapper.OwnerUnitDangerMapper;
import com.ruoyi.electrical.project.domain.ChargingPile;
import com.ruoyi.electrical.project.domain.OwnerUnit;
import com.ruoyi.electrical.project.domain.Project;
import com.ruoyi.electrical.project.mapper.OwnerUnitMapper;
import com.ruoyi.electrical.project.service.IChargingPileService;
import com.ruoyi.electrical.project.service.IProjectService;
import com.ruoyi.electrical.report.domain.OwnerUnitReport;
import com.ruoyi.electrical.report.dto.DetectUnitInfo;
import com.ruoyi.electrical.report.dto.OwnerUnitReportInfo;
import com.ruoyi.electrical.report.dto.station.ChargingPileInfo;
import com.ruoyi.electrical.report.dto.station.StationDanger;
import com.ruoyi.electrical.report.dto.station.StationForm;
import com.ruoyi.electrical.report.dto.station.StationFormData;
import com.ruoyi.electrical.report.dto.station.StationInitialReport;
import com.ruoyi.electrical.report.dto.station.StationOwnerUnitInfo;
import com.ruoyi.electrical.report.service.IChargingStationInitialReportService;
import com.ruoyi.electrical.report.service.IOwnerUnitReportService;
import com.ruoyi.electrical.role.domain.DetectUnit;
import com.ruoyi.electrical.role.service.IDetectUnitService;
import com.ruoyi.electrical.template.mapper.IntuitiveDetectDataMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChargingStationInitialReportServiceImpl implements IChargingStationInitialReportService {

	@Autowired
	private IOwnerUnitReportService unitReportService;

	// @Autowired
	// private IOwnerUnitService ownerUnitService;

	@Autowired
	private OwnerUnitMapper ownerUnitMapper;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IDetectUnitService detectUnitService;

	@Autowired
	private IChargingPileService chargingPileService;

	@Autowired
	private IntuitiveDetectDataMapper detectDataMapper;

	@Autowired
	private OwnerUnitDangerMapper ownerUnitDangerMapper;

	private static final Map<String, String> MODULE_MAP = new HashMap<String, String>();

	static {
		MODULE_MAP.put("1", "整体安全检查");
		MODULE_MAP.put("2", "用电安全检查");
		MODULE_MAP.put("3", "消防设施安全检查");
		MODULE_MAP.put("4", "电化学储能设施安全检查");
		MODULE_MAP.put("5", "场站其他检查");
		MODULE_MAP.put("6", "充电系统检查");
	}

	@Override
	public int initialReport(Long reportId) {

		StationInitialReport report = getStationInitialReport(reportId);
		try {

			log.info("reportInfo:{}", report);

			LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
			ConfigureBuilder configureBuilder = Configure.builder().useSpringEL().bind("pileGroup", policy)
					.bind("piles", policy).bind("form", policy).bind("data", policy).bind("form.data", policy)
					.bind("danger", policy);
			Configure config = configureBuilder.build();

			Map<String, Object> dataMap = BeanUtil.beanToMap(report);

			log.info("dataMap:{}", dataMap);

			NiceXWPFDocument main;

			InputStream mainInputStream = ClassPathResource.class.getClassLoader()
					.getResourceAsStream("report/initial/station/Initial_Report.docx");
			XWPFTemplate mainTemplate = XWPFTemplate.compile(mainInputStream, config).render(dataMap);
			main = mainTemplate.getXWPFDocument();

			LocalDateTime now = LocalDateTime.now();
			String timestamp = DateUtil.format(now, DatePattern.PURE_DATETIME_MS_PATTERN);
			String fileName = timestamp + IdUtils.fastSimpleUUID().toUpperCase() + ".docx";
			String datePath = DateUtil.format(now, "yyyy/MM/dd");

			String filePath = StrUtil.format("{}/{}", datePath, fileName);

			String baseDir = RuoYiConfig.getUploadPath();
			File saveFile = FileUploadUtils.getAbsoluteFile(baseDir, filePath);

			main.write(new FileOutputStream(saveFile));

			PoitlIOUtils.closeQuietlyMulti(main); // 最后不要忘记关闭这些流。

			// FileUploadUtils.getPathFileName(baseDir, filePath);

			Integer wordFileVersion = report.getReport().getWordFileVersion();

			if (wordFileVersion == null) {
				wordFileVersion = 1;
			} else {
				wordFileVersion = wordFileVersion + 1;
			}

			OwnerUnitReport result = new OwnerUnitReport();
			result.setId(reportId);
			result.setWordFileVersion(wordFileVersion);
			result.setWordFile(FileUploadUtils.getPathFileName(baseDir, filePath));

			return unitReportService.updateOwnerUnitReport(result);

			// return AjaxResult.success(data);
		} catch (Exception e) {
			log.error("生成初检报告失败！", e);
			return 0;
		} finally {

		}
	}

	private StationInitialReport getStationInitialReport(Long reportId) {

		OwnerUnitReport report = unitReportService.selectOwnerUnitReportById(reportId);
		if (report == null) {
			return null;
		}

		OwnerUnit ownerUnit = ownerUnitMapper.getOwnerUnitById(report.getUnitId());

		if (ownerUnit == null) {
			return null;
		}

		Project project = projectService.selectProjectById(ownerUnit.getProjectId());

		if (project == null) {
			return null;
		}

		DetectUnit detectUnit = detectUnitService.selectDetectUnitById(project.getDetectId());
		if (detectUnit == null) {
			return null;
		}

		StationInitialReport data = new StationInitialReport();

		StationOwnerUnitInfo unitInfo = new StationOwnerUnitInfo();
		BeanUtils.copyProperties(ownerUnit, unitInfo);
		data.setUnit(unitInfo);

		OwnerUnitReportInfo reportInfo = new OwnerUnitReportInfo();
		BeanUtils.copyProperties(report, reportInfo);
		reportInfo.setDetectData(DateUtil.format(report.getDetectData(), DatePattern.CHINESE_DATE_FORMATTER));
		data.setReport(reportInfo);

		DetectUnitInfo detectUnitInfo = new DetectUnitInfo();
		BeanUtils.copyProperties(detectUnit, detectUnitInfo);
		data.setDetect(detectUnitInfo);

		ChargingPile query = new ChargingPile();
		query.setUnitId(ownerUnit.getId());
		query.setRounds(ownerUnit.getRounds());

		List<ChargingPile> chargingPiles = chargingPileService.selectChargingPileList(query);
		List<ChargingPileInfo> piles = BeanUtil.copyToList(chargingPiles, ChargingPileInfo.class);
		data.setPiles(piles);

		List<StationForm> forms = new ArrayList<StationForm>();

		List<StationDanger> dangers = new ArrayList<StationDanger>();

		String detectModule = ownerUnit.getDetectModule();
		if (StrUtil.isNotBlank(detectModule)) {
			String[] modules = detectModule.split(",");
			if (modules != null) {
				for (int i = 0; i < modules.length; i++) {
					StationForm form = new StationForm();
					form.setName(MODULE_MAP.get(modules[i]));

					List<StationFormData> formData = detectDataMapper.selectStationDetectDataByModule(modules[i],
							project.getTemplateId(), ownerUnit.getId());
					form.setData(formData);

					forms.add(form);

					StationDanger danger = new StationDanger();
					Style style = Style.builder().buildBold().build();
					danger.setLocation(
							new TextRenderData(StrUtil.format("{}、{}", i + 1, MODULE_MAP.get(modules[i])), style));
					dangers.add(danger);

					try {
						OwnerUnitDanger dangerQuery = new OwnerUnitDanger();
						dangerQuery.setFormId(Long.valueOf(modules[i]));
						dangerQuery.setUnitId(ownerUnit.getId());
						dangerQuery.setRounds(ownerUnit.getRounds());

						List<OwnerUnitDanger> ownerUnitDangerList = ownerUnitDangerMapper
								.ownerUnitDangerList(dangerQuery);

						dangers.addAll(BeanUtil.copyToList(ownerUnitDangerList, StationDanger.class));
					} catch (Exception e) {
					}
				}
			}
		}
		data.setForm(forms);
		data.setDanger(dangers);
		return data;
	}

}
