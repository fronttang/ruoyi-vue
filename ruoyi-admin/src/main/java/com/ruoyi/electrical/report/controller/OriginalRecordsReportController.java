package com.ruoyi.electrical.report.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.data.style.Style.StyleBuilder;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.electrical.project.domain.OwnerUnit;
import com.ruoyi.electrical.project.domain.Project;
import com.ruoyi.electrical.project.service.IOwnerUnitService;
import com.ruoyi.electrical.project.service.IProjectService;
import com.ruoyi.electrical.report.domain.OwnerUnitReport;
import com.ruoyi.electrical.report.dto.DetectForm;
import com.ruoyi.electrical.report.dto.DetectFormData;
import com.ruoyi.electrical.report.dto.OriginalRecords;
import com.ruoyi.electrical.report.dto.OwnerUnitInfo;
import com.ruoyi.electrical.report.dto.OwnerUnitReportInfo;
import com.ruoyi.electrical.report.service.IOwnerUnitReportService;
import com.ruoyi.electrical.role.domain.DetectUnit;
import com.ruoyi.electrical.role.service.IDetectUnitService;
import com.ruoyi.electrical.template.domain.IntuitiveDetect;
import com.ruoyi.electrical.template.domain.IntuitiveDetectDanger;
import com.ruoyi.electrical.template.domain.IntuitiveDetectData;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDangerService;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDataService;
import com.ruoyi.electrical.template.service.IIntuitiveDetectService;
import com.ruoyi.system.service.ISysDictDataService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/report/download/originalRecords")
public class OriginalRecordsReportController extends BaseController {

	@Autowired
	private IOwnerUnitReportService unitReportService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IDetectUnitService detectUnitService;

	@Autowired
	private ISysDictDataService dictDataService;

	@Autowired
	private IIntuitiveDetectService intuitiveDetectService;

	@Autowired
	private IIntuitiveDetectDataService intuitiveDetectDataService;

	@Autowired
	private IIntuitiveDetectDangerService intuitiveDetectDangerService;

	/**
	 * 原始记录 unit.nature=='1'?'':''
	 * 
	 * @param reportId
	 */
	@RequestMapping("/{reportId}")
	public void originalRecords(@PathVariable Long reportId, HttpServletRequest request, HttpServletResponse response) {

		OriginalRecords originalRecords = getReportData(reportId);
		if (originalRecords == null) {
			return;
		}

		log.info("originalRecords : " + originalRecords);

		LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
		ConfigureBuilder configureBuilder = Configure.builder().useSpringEL().bind("data", policy);
		Configure config = configureBuilder.build();
		try {

			Map<String, Object> dataMap = BeanUtil.beanToMap(originalRecords);
			List<NiceXWPFDocument> documents = new ArrayList<NiceXWPFDocument>();

			NiceXWPFDocument main;

			File titleFile = new ClassPathResource("report/originalRecords/OriginalRecords_Title.docx").getFile();
			XWPFTemplate titleTemplate = XWPFTemplate.compile(titleFile, config).render(dataMap);
			main = titleTemplate.getXWPFDocument();

			File infoFile = new ClassPathResource("report/originalRecords/OriginalRecords_Info.docx").getFile();
			XWPFTemplate infoTemplate = XWPFTemplate.compile(infoFile, config).render(dataMap);
			main = main.merge(infoTemplate.getXWPFDocument());

			List<DetectForm> detectForm = getDetectForm(originalRecords.getProject());

			if (CollUtil.isNotEmpty(detectForm)) {
				File formFile = new ClassPathResource("report/originalRecords/OriginalRecords_Form.docx").getFile();
				for (DetectForm form : detectForm) {
					form.setDetect(originalRecords.getDetect());

					Map<String, Object> formDataMap = BeanUtil.beanToMap(form);

					log.info("formDataMap:" + formDataMap);

					XWPFTemplate template = XWPFTemplate.compile(formFile, config).render(formDataMap);
					main = main.merge(template.getXWPFDocument());

					// formTemplates.add(XWPFTemplate.compile(formFile,
					// config).render(formDataMap));
				}
			}

			// main.merge(documents, main.createParagraph().createRun());

			String fileName = URLEncodeUtil.encode("电检原始记录报告");
			response.setContentType("application/octet-stream;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".docx");

			OutputStream out = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(out);
			main.write(bos);
			bos.flush();
			out.flush();

			PoitlIOUtils.closeQuietlyMulti(main, bos, out); // 最后不要忘记关闭这些流。

		} catch (Exception e) {
			log.error("生成电检原始记录报告失败！", e);
		} finally {

		}
	}

	private OriginalRecords getReportData(Long reportId) {

		OwnerUnitReport report = unitReportService.selectOwnerUnitReportById(reportId);
		if (report == null) {
			return null;
		}

		OwnerUnit ownerUnit = ownerUnitService.selectOwnerUnitById(report.getUnitId());

		if (ownerUnit == null) {
			return null;
		}

		Project project = projectService.selectProjectById(ownerUnit.getProjectId());

		if (project == null) {
			return null;
		}

		DetectUnit detectUnit = detectUnitService.selectDetectUnitById(project.getDetectId());

		OwnerUnitReportInfo reportInfo = new OwnerUnitReportInfo();
		BeanUtils.copyProperties(report, reportInfo, "detectData");
		reportInfo.setDetectData(DateUtil.format(report.getDetectData(), DatePattern.CHINESE_DATE_FORMATTER));

		OwnerUnitInfo unitInfo = new OwnerUnitInfo();
		BeanUtils.copyProperties(ownerUnit, unitInfo, "nature");

		buildOwnerUnitNatureInfo(ownerUnit, unitInfo);

		buildOwnerUnitDetecContentInfo(ownerUnit, unitInfo);

		getDetectForm(project);

		OriginalRecords records = new OriginalRecords();
		records.setDetect(detectUnit);
		records.setProject(project);
		records.setUnit(unitInfo);
		records.setReport(reportInfo);

		// Map<String, Object> dataMap = BeanUtil.beanToMap(records);
		return records;
	}

	private List<DetectForm> getDetectForm(Project project) {
		// 直观检测表
		IntuitiveDetect detectQuery = new IntuitiveDetect();
		detectQuery.setTemplateId(project.getTemplateId());

		List<IntuitiveDetect> intuitiveDetect = intuitiveDetectService.selectIntuitiveDetectList(detectQuery);

		List<DetectForm> forms = new ArrayList<DetectForm>();

		if (intuitiveDetect != null) {
			intuitiveDetect.forEach((detect) -> {
				DetectForm form = new DetectForm();
				form.setName(detect.getName());

				IntuitiveDetectData dataQuery = new IntuitiveDetectData();
				dataQuery.setDetectTitle(detect.getId());

				List<DetectFormData> formDatas = new ArrayList<DetectFormData>();

				List<IntuitiveDetectData> detectData = intuitiveDetectDataService
						.selectIntuitiveDetectDataList(dataQuery);
				if (detectData != null) {

					detectData.forEach((data) -> {

						DetectFormData formData = new DetectFormData();
						formData.setFirstCode(data.getFirstCode());
						formData.setFirstContent(data.getFirstContent());

						if ("1".equalsIgnoreCase(data.getType())) {
							Style style = Style.builder().buildBold().build();
							formData.setFirstContent(new TextRenderData(data.getFirstContent(), style));
						}

						if ("2".equalsIgnoreCase(data.getType())) {

							List<IntuitiveDetectDanger> detectDangers = intuitiveDetectDangerService
									.selectIntuitiveDetectDangersByDataId(data.getId());

							if (CollUtil.isNotEmpty(detectDangers)) {
								formData.setLevel(detectDangers.get(0).getLevel());
							}

							Long dangers = intuitiveDetectDangerService.countDangersByDataId(data.getId());
							formData.setResult(dangers != null && dangers > 0 ? "不符合" : "符合");
						}

						formDatas.add(formData);

					});
				}
				form.setData(formDatas);
				forms.add(form);
			});
		}

		return forms;
	}

	private void buildOwnerUnitNatureInfo(OwnerUnit ownerUnit, OwnerUnitInfo unitInfo) {
		SysDictData query = new SysDictData();
		query.setDictType("building_nature");

		Map<String, TextRenderData> nature = new HashMap<String, TextRenderData>();

		List<SysDictData> natureDict = dictDataService.selectDictDataList(query);
		if (natureDict == null || StrUtil.isBlank(ownerUnit.getNature())) {
			for (int i = 1; i <= 10; i++) {
				nature.put("nature" + i, new TextRenderData("\u00A3", new Style("Wingdings 2", 14)));
			}
		} else {
			natureDict.forEach((dict) -> {

				if (dict.getDictValue().equalsIgnoreCase(ownerUnit.getNature())) {
					nature.put("nature" + dict.getDictValue(), new TextRenderData("R", new Style("Wingdings 2", 14)));
				} else {
					nature.put("nature" + dict.getDictValue(),
							new TextRenderData("\u00A3", new Style("Wingdings 2", 14)));
				}
			});
		}
		unitInfo.setNature(nature);
	}

	private void buildOwnerUnitDetecContentInfo(OwnerUnit ownerUnit, OwnerUnitInfo unitInfo) {
		SysDictData query = new SysDictData();
		query.setDictType("detect_content");

		Map<String, TextRenderData> detectContent = new HashMap<String, TextRenderData>();

		List<SysDictData> natureDict = dictDataService.selectDictDataList(query);
		if (natureDict == null || StrUtil.isBlank(ownerUnit.getTestContent())) {
			for (int i = 1; i <= 10; i++) {
				detectContent.put("item" + i, new TextRenderData("\u00A3", new Style("Wingdings 2", 12)));
			}
		} else {

			List<String> split = StrUtil.split(ownerUnit.getTestContent(), ",");

			natureDict.forEach((dict) -> {

				if (split.contains(dict.getDictValue())) {
					detectContent.put("item" + dict.getDictValue(),
							new TextRenderData("R", new Style("Wingdings 2", 12)));
				} else {
					detectContent.put("item" + dict.getDictValue(),
							new TextRenderData("\u00A3", new Style("Wingdings 2", 12)));
				}
			});
		}
		unitInfo.setDetectContent(detectContent);
	}

}
