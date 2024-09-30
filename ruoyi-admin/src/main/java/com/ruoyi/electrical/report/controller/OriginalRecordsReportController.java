package com.ruoyi.electrical.report.controller;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.CellRenderData;
import com.deepoove.poi.data.Cells;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.CellStyle;
import com.deepoove.poi.data.style.ParagraphStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.policy.TableRenderPolicy;
import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.util.TableTools;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.danger.service.IOwnerUnitDangerService;
import com.ruoyi.electrical.project.domain.OwnerUnit;
import com.ruoyi.electrical.project.domain.Project;
import com.ruoyi.electrical.project.service.IOwnerUnitService;
import com.ruoyi.electrical.project.service.IProjectService;
import com.ruoyi.electrical.report.annotation.Formb;
import com.ruoyi.electrical.report.domain.OwnerUnitReport;
import com.ruoyi.electrical.report.dto.DetectForm;
import com.ruoyi.electrical.report.dto.DetectFormData;
import com.ruoyi.electrical.report.dto.OriginalRecords;
import com.ruoyi.electrical.report.dto.OwnerUnitInfo;
import com.ruoyi.electrical.report.dto.OwnerUnitReportInfo;
import com.ruoyi.electrical.report.formb.FormB1;
import com.ruoyi.electrical.report.formb.FormB14;
import com.ruoyi.electrical.report.formb.FormB6;
import com.ruoyi.electrical.report.service.IOwnerUnitReportService;
import com.ruoyi.electrical.role.domain.DetectUnit;
import com.ruoyi.electrical.role.service.IDetectUnitService;
import com.ruoyi.electrical.template.domain.IntuitiveDetect;
import com.ruoyi.electrical.template.domain.IntuitiveDetectData;
import com.ruoyi.electrical.template.dto.IntuitiveDetectQuery;
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
	private IOwnerUnitDangerService ownerUnitDangerService;

	private static Set<Class<?>> allFormbBeans = new HashSet<Class<?>>();

	static {
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.forPackages("com.ruoyi.electrical.report.formb")
				.filterInputsBy(new FilterBuilder().includePackage("BOOT-INF.classes.com.ruoyi.electrical.report.formb")
						.includePackage("com.ruoyi.electrical.report.formb"))
				.setScanners(Scanners.TypesAnnotated));

		allFormbBeans = reflections.getTypesAnnotatedWith(Formb.class);
	}

	private class FormLoopRowTableRenderPolicy extends LoopRowTableRenderPolicy {

		@SuppressWarnings("unchecked")
		@Override
		protected void afterloop(XWPFTable table, Object data) {

			List<DetectFormData> formDatas = (List<DetectFormData>) data;

			for (int i = 1; i <= formDatas.size(); i++) {

				DetectFormData formData = formDatas.get(i - 1);

				if (formData.isMerge()) {

					XWPFTableRow row = table.getRow(i);
					TableTools.mergeCellsHorizonal(table, i, 1, 3);

					CellRenderData cellRenderData = Cells.of(String.valueOf(formData.getFirstContent())).create();
					XWPFTableCell cell = row.getCell(1);

					try {
						CellStyle cellStyle = new CellStyle();
						ParagraphStyle paragraphStyle = ParagraphStyle.builder()
								.withDefaultTextStyle(Style.builder().buildBold().build()).build();
						cellStyle.setDefaultParagraphStyle(paragraphStyle);

						TableRenderPolicy.Helper.renderCell(cell, cellRenderData, cellStyle);
					} catch (Exception e) {
						log.error("", e);
					}
				}
			}
		}
	}

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
		ConfigureBuilder configureBuilder = Configure.builder().useSpringEL()
				.bind("data", new FormLoopRowTableRenderPolicy()).bind("formb.B1", policy).bind("formb.B1A", policy)
				.bind("formb.B1B", policy).bind("formb.BB1", policy).bind("formb.B2", policy).bind("formb.B3", policy)
				.bind("formb.B4", policy).bind("formb.B5", policy).bind("formb.B6", policy).bind("formb.B7", policy)
				.bind("formb.B8", policy).bind("formb.B9", policy).bind("formb.B10", policy).bind("formb.B11", policy)
				.bind("formb.B12", policy).bind("formb.B13", policy).bind("formb.B14", policy)
				.bind("formb.B14A", policy).bind("formb.B14B", policy).bind("formb.B15", policy);
		Configure config = configureBuilder.build();
		try {

			Map<String, Object> dataMap = BeanUtil.beanToMap(originalRecords);

			NiceXWPFDocument main;

			InputStream titleInputStream = ClassPathResource.class.getClassLoader()
					.getResourceAsStream("report/originalRecords/OriginalRecords_Title.docx");

			XWPFTemplate titleTemplate = XWPFTemplate.compile(titleInputStream, config).render(dataMap);
			main = titleTemplate.getXWPFDocument();

			InputStream infoInputStream = ClassPathResource.class.getClassLoader()
					.getResourceAsStream("report/originalRecords/OriginalRecords_Info.docx");
			XWPFTemplate infoTemplate = XWPFTemplate.compile(infoInputStream, config).render(dataMap);
			main = main.merge(infoTemplate.getXWPFDocument());

			InputStream formbInputStream = ClassPathResource.class.getClassLoader()
					.getResourceAsStream("report/originalRecords/OriginalRecords_Formb.docx");
			XWPFTemplate formbTemplate = XWPFTemplate.compile(formbInputStream, config).render(dataMap);
			main = main.merge(formbTemplate.getXWPFDocument());

			List<DetectForm> detectForm = getDetectForm(originalRecords.getUnit(), originalRecords.getProject());

			if (CollUtil.isNotEmpty(detectForm)) {

				for (DetectForm form : detectForm) {

					InputStream formInputStream = ClassPathResource.class.getClassLoader()
							.getResourceAsStream("report/originalRecords/OriginalRecords_Form.docx");
					form.setDetect(originalRecords.getDetect());
					form.setUnit(originalRecords.getUnit());

					Map<String, Object> formDataMap = BeanUtil.beanToMap(form);

					log.info("formDataMap:" + formDataMap);

					XWPFTemplate template = XWPFTemplate.compile(formInputStream, config).render(formDataMap);
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

		Map<String, List<Object>> formb = queryFormb(ownerUnit);

		// getDetectForm(ownerUnit, project);

		OriginalRecords records = new OriginalRecords();
		records.setDetect(detectUnit);
		records.setProject(project);
		records.setUnit(unitInfo);
		records.setReport(reportInfo);
		records.setFormb(formb);

		// Map<String, Object> dataMap = BeanUtil.beanToMap(records);
		return records;
	}

	private Map<String, List<Object>> queryFormb(OwnerUnit ownerUnit) {

		Map<String, List<Object>> formb = new HashMap<String, List<Object>>();

		SysDictData query = new SysDictData();
		query.setDictType("detect_table_b");

		List<SysDictData> formbDict = dictDataService.selectDictDataList(query);

		Map<String, Class<?>> frombMap = allFormbBeans.stream().collect(Collectors.toMap((clazz) -> {

			Formb annotation = clazz.getAnnotation(Formb.class);
			return annotation.value();

		}, Function.identity()));

		formbDict.forEach((dict) -> {
			// Class<?> formbClass = frombMap.get(dict.getDictValue());

			try {
				formb.put(dict.getDictValue(), Arrays.asList());
			} catch (Exception e) {
				log.error("", e);
			}
		});
		formb.put("B14A", Arrays.asList());
		formb.put("B14B", Arrays.asList());
		formb.put("B1A", Arrays.asList());
		formb.put("B1B", Arrays.asList());

		// 查所有formb的隐患数据
		OwnerUnitDanger danger = new OwnerUnitDanger();
		danger.setUnitId(ownerUnit.getId());
		danger.setFormType("B");
		List<OwnerUnitDanger> dangers = ownerUnitDangerService.selectOwnerUnitDangerList(danger);

		if (CollUtil.isNotEmpty(dangers)) {
			dangers.sort(Comparator.comparing(OwnerUnitDanger::getFormCode));

			Map<String, List<Object>> collect = dangers.stream()
					.collect(Collectors.groupingBy(OwnerUnitDanger::getFormCode, Collectors.mapping((dang) -> {
						JSONObject formbJson = dang.getFormb();
						if (Objects.nonNull(formbJson) && frombMap.get(dang.getFormCode()) != null) {
							try {
								JSONObject formbData = formbJson.getJSONObject("data");
								if (Objects.nonNull(formbData)) {
									Object formbBean = formbData.toJavaObject(frombMap.get(dang.getFormCode()));
									// BeanUtils.copyProperties(formbBean, newInstance);
									if (!"B15".equalsIgnoreCase(dang.getFormCode())) {
										BeanUtil.setFieldValue(formbBean, "temperature", ownerUnit.getTemperature());
									}
									BeanUtil.setFieldValue(formbBean, "humidity", ownerUnit.getHumidity());
									String detectDate = DateUtil.format(dang.getInitialTime(),
											DatePattern.CHINESE_DATE_FORMATTER);
									BeanUtil.setFieldValue(formbBean, "detectDate", detectDate);

									if ("B1".equalsIgnoreCase(dang.getFormCode())
											|| "BB1".equalsIgnoreCase(dang.getFormCode())) {
										BeanUtil.setFieldValue(formbBean, "weather", ownerUnit.getWeather());
										BeanUtil.setFieldValue(formbBean, "windSpeed", ownerUnit.getWindSpeed());
										BeanUtil.setFieldValue(formbBean, "deviceName", dang.getReportLocation1());
									} else {
										BeanUtil.setFieldValue(formbBean, "location", dang.getReportLocation1());
									}

									if ("B6".equalsIgnoreCase(dang.getFormCode())) {
										FormB6 formb6 = (FormB6) formbBean;
										if (">1000".equalsIgnoreCase(formb6.getAction())
												|| "不动作".equalsIgnoreCase(formb6.getAction())) {
											BeanUtil.setFieldValue(formbBean, "dialValue0", ">1000");
											BeanUtil.setFieldValue(formbBean, "dialValue180", ">1000");
										}
									}

									return formbBean;
								}
							} catch (Exception e) {
								log.error("", e);
							}
						}
						return formbJson;
					}, Collectors.toList())));

			if (CollUtil.isNotEmpty(collect)) {

				collect.forEach((key, value) -> {
					if ("B14".equalsIgnoreCase(key)) {
						List<Object> residualCurrents = value.stream().filter((d) -> {
							FormB14 b14 = (FormB14) d;
							return FormB14.TYPE_RESIDUALCURRENT.equalsIgnoreCase(b14.getType());
						}).collect(Collectors.toList());

						formb.put("B14A", residualCurrents);

						List<Object> alarmTimes = value.stream().filter((d) -> {
							FormB14 b14 = (FormB14) d;
							return FormB14.TYPE_ALARMTIME.equalsIgnoreCase(b14.getType());
						}).collect(Collectors.toList());

						formb.put("B14B", alarmTimes);
					} else if ("B1".equalsIgnoreCase(key)) {
						// 单相
						List<Object> singlePhase = value.stream().filter((d) -> {
							FormB1 b1 = (FormB1) d;
							return FormB1.SINGLE_PHASE.equalsIgnoreCase(b1.getType());
						}).collect(Collectors.toList());

						formb.put("B1B", singlePhase);

						// 三相
						List<Object> threePhase = value.stream().filter((d) -> {
							FormB1 b1 = (FormB1) d;
							return !FormB1.SINGLE_PHASE.equalsIgnoreCase(b1.getType());
						}).collect(Collectors.toList());

						formb.put("B1A", threePhase);
					} else {
						formb.put(key, value);
					}
				});
			}

			formb.putAll(collect);

		}
		return formb;
	}

	private List<DetectForm> getDetectForm(OwnerUnitInfo ownerUnit, Project project) {
		// 直观检测表
		IntuitiveDetectQuery detectQuery = new IntuitiveDetectQuery();
		detectQuery.setTemplateId(project.getTemplateId());

		List<IntuitiveDetect> intuitiveDetect = intuitiveDetectService.selectIntuitiveDetectList(detectQuery);

		List<DetectForm> forms = new ArrayList<DetectForm>();

		List<IntuitiveDetectData> detectDatas = intuitiveDetectDataService
				.selectReportIntuitiveDetectDataList(project.getTemplateId(), ownerUnit.getId());

		Map<Long, List<IntuitiveDetectData>> formDataMap = new HashMap<Long, List<IntuitiveDetectData>>();

		if (CollUtil.isNotEmpty(detectDatas)) {
			formDataMap.putAll(detectDatas.stream().filter((d) -> Objects.nonNull(d.getDetectTitle()))
					.collect(Collectors.groupingBy(IntuitiveDetectData::getDetectTitle, Collectors.toList())));
		}

		if (intuitiveDetect != null) {
			intuitiveDetect.forEach((detect) -> {
				DetectForm form = new DetectForm();
				form.setName(detect.getName());

				List<DetectFormData> formDatas = new ArrayList<DetectFormData>();

				List<IntuitiveDetectData> detectData = formDataMap.get(detect.getId());
				if (detectData != null) {

					detectData.forEach((data) -> {

						DetectFormData formData = new DetectFormData();
						formData.setFirstCode(data.getFirstCode());
						formData.setFirstContent(data.getFirstContent());

						if ("1".equalsIgnoreCase(data.getType())) {
							// Style style = Style.builder().buildBold().build();
							// formData.setFirstContent(new TextRenderData(data.getFirstContent(), style));
							formData.setMerge(true);
						}
						formData.setLevel(
								StrUtil.isNotBlank(data.getReportLevel()) ? data.getReportLevel() : data.getLevel());
						// formData.setDecide(data.getDanger() != null && data.getDanger() > 0 ? "不符合" :
						// "符合");
						formData.setResult(data.getDanger() != null && data.getDanger() > 0 ? "不符合" : "符合");

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
			for (int i = 1; i <= 12; i++) {
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