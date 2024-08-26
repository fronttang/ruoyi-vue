package com.ruoyi.electrical.report.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter.Feature;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.danger.handler.IFormbDangerHandler;
import com.ruoyi.electrical.danger.service.IOwnerUnitDangerService;
import com.ruoyi.electrical.project.domain.DetectDevice;
import com.ruoyi.electrical.project.domain.OwnerUnit;
import com.ruoyi.electrical.project.domain.Project;
import com.ruoyi.electrical.project.service.IDetectDeviceService;
import com.ruoyi.electrical.project.service.IOwnerUnitService;
import com.ruoyi.electrical.project.service.IProjectService;
import com.ruoyi.electrical.report.annotation.Formb;
import com.ruoyi.electrical.report.domain.OwnerUnitReport;
import com.ruoyi.electrical.report.dto.DetectDeviceInfo;
import com.ruoyi.electrical.report.dto.DetectFormData;
import com.ruoyi.electrical.report.dto.DetectUnitInfo;
import com.ruoyi.electrical.report.dto.FormLoopRowTableRenderPolicy;
import com.ruoyi.electrical.report.dto.InitialReport;
import com.ruoyi.electrical.report.dto.OwnerUnitInfo;
import com.ruoyi.electrical.report.dto.OwnerUnitReportInfo;
import com.ruoyi.electrical.report.dto.UrbanVillageDanger;
import com.ruoyi.electrical.report.formb.FormB14;
import com.ruoyi.electrical.report.mapper.OwnerUnitReportMapper;
import com.ruoyi.electrical.report.service.IOwnerUnitReportService;
import com.ruoyi.electrical.report.service.IUrbanVillageUnitInitialReportService;
import com.ruoyi.electrical.role.domain.DetectUnit;
import com.ruoyi.electrical.role.service.IDetectUnitService;
import com.ruoyi.electrical.template.domain.IntuitiveDetectDanger;
import com.ruoyi.electrical.template.domain.IntuitiveDetectData;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDangerService;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDataService;
import com.ruoyi.electrical.vo.OwnerUnitReivewDateVo;
import com.ruoyi.system.service.ISysDictDataService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UrbanVillageUnitInitialReportServiceImpl implements IUrbanVillageUnitInitialReportService {

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
	private IIntuitiveDetectDataService intuitiveDetectDataService;

	@Autowired
	private IIntuitiveDetectDangerService intuitiveDetectDangerService;

	@Autowired
	private IDetectDeviceService detectDeviceService;

	@Autowired
	private IOwnerUnitDangerService ownerUnitDangerService;

	@Autowired
	private OwnerUnitReportMapper ownerUnitReportMapper;

	private static Set<Class<?>> allFormbBeans = new HashSet<Class<?>>();

	static {
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.forPackages("com.ruoyi.electrical.report.formb")
				.filterInputsBy(new FilterBuilder().includePackage("BOOT-INF.classes.com.ruoyi.electrical.report.formb")
						.includePackage("com.ruoyi.electrical.report.formb"))
				.setScanners(Scanners.TypesAnnotated));

		allFormbBeans = reflections.getTypesAnnotatedWith(Formb.class);
	}

	@Override
	public int reviewReport(Long reportId) {

		InitialReport initialReport = getReviewReportData(reportId);
		if (initialReport == null) {
			return 0;
		}

		log.info("reviewReport:{} ", JSON.toJSONString(initialReport, Feature.WriteMapNullValue));

		LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
		ConfigureBuilder configureBuilder = Configure.builder().useSpringEL().bind("device", policy);
		Configure config = configureBuilder.build();
		try {

			Map<String, Object> dataMap = BeanUtil.beanToMap(initialReport);

			NiceXWPFDocument main;

			// 模板
			InputStream mainInputStream = ClassPathResource.class.getClassLoader()
					.getResourceAsStream("report/review/review_report.docx");
			XWPFTemplate mainTemplate = XWPFTemplate.compile(mainInputStream, config).render(dataMap);
			main = mainTemplate.getXWPFDocument();

			return saveReportFile(reportId, initialReport, main);

			// return AjaxResult.success(data);
		} catch (Exception e) {
			log.error("生成电气检测复检报告失败！", e);
			return 0;
		} finally {

		}
	}

	private int saveReportFile(Long reportId, InitialReport initialReport, NiceXWPFDocument main)
			throws IOException, FileNotFoundException {
		LocalDateTime now = LocalDateTime.now();
		String timestamp = DateUtil.format(now, DatePattern.PURE_DATETIME_MS_PATTERN);
		String fileName = timestamp + IdUtils.fastSimpleUUID().toUpperCase() + ".docx";
		String datePath = DateUtil.format(now, "yyyy/MM/dd");

		String filePath = StrUtil.format("{}/{}", datePath, fileName);

		String baseDir = RuoYiConfig.getUploadPath();
		File saveFile = FileUploadUtils.getAbsoluteFile(baseDir, filePath);

		main.write(new FileOutputStream(saveFile));

		PoitlIOUtils.closeQuietlyMulti(main); // 最后不要忘记关闭这些流。

		Integer wordFileVersion = initialReport.getReport().getWordFileVersion();

		if (wordFileVersion == null) {
			wordFileVersion = 1;
		} else {
			wordFileVersion = wordFileVersion + 1;
		}

		OwnerUnitReport report = new OwnerUnitReport();
		report.setId(reportId);
		report.setWordFileVersion(wordFileVersion);
		report.setWordFile(FileUploadUtils.getPathFileName(baseDir, filePath));

		return unitReportService.updateOwnerUnitReport(report);
	}

	@Override
	public int initialReport(Long reportId) {

		InitialReport initialReport = getReportData(reportId);
		if (initialReport == null) {
			return 0;
		}

		log.info("initialReport:{} ", JSON.toJSONString(initialReport, Feature.WriteMapNullValue));

		LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
		ConfigureBuilder configureBuilder = Configure.builder().useSpringEL()
				.bind("data", new FormLoopRowTableRenderPolicy()).bind("device", policy).bind("formb.B1", policy)
				.bind("formb.BB1", policy).bind("formb.B2", policy).bind("formb.B3", policy).bind("formb.B4", policy)
				.bind("formb.B5", policy).bind("formb.B6", policy).bind("formb.B7", policy).bind("formb.B8", policy)
				.bind("formb.B9", policy).bind("formb.B10", policy).bind("formb.B11", policy).bind("formb.B12", policy)
				.bind("formb.B13", policy).bind("formb.B14", policy).bind("formb.B14A", policy)
				.bind("formb.B14B", policy).bind("formb.B15", policy);
		Configure config = configureBuilder.build();
		try {

			Map<String, Object> dataMap = BeanUtil.beanToMap(initialReport);

			NiceXWPFDocument main;

			// 标题
			InputStream mainInputStream = ClassPathResource.class.getClassLoader()
					.getResourceAsStream("report/initial/Initial_Report.docx");
			XWPFTemplate mainTemplate = XWPFTemplate.compile(mainInputStream, config).render(dataMap);
			main = mainTemplate.getXWPFDocument();

			// 检测B表
			InputStream formbInputStream = ClassPathResource.class.getClassLoader()
					.getResourceAsStream("report/initial/Initial_Formb.docx");
			XWPFTemplate formbTemplate = XWPFTemplate.compile(formbInputStream, config).render(dataMap);
			main = main.merge(formbTemplate.getXWPFDocument());

			// 检测表
			InputStream formInputStream = ClassPathResource.class.getClassLoader()
					.getResourceAsStream("report/initial/Initial_Form.docx");
			XWPFTemplate formTemplate = XWPFTemplate.compile(formInputStream, config).render(dataMap);
			main = main.merge(formTemplate.getXWPFDocument());

			return saveReportFile(reportId, initialReport, main);

			// return AjaxResult.success(data);
		} catch (Exception e) {
			log.error("生成电气检测初检报告失败！", e);
			return 0;
		} finally {

		}
	}

	private Map<String, List<Object>> buildFormb(OwnerUnit ownerUnit, List<OwnerUnitDanger> unitDangerList) {

		Map<String, List<Object>> formb = new HashMap<String, List<Object>>();

		SysDictData query = new SysDictData();
		query.setDictType("detect_table_b");

		List<SysDictData> formbDict = dictDataService.selectDictDataList(query);

		Map<String, Class<?>> frombMap = allFormbBeans.stream().collect(Collectors.toMap((clazz) -> {

			Formb annotation = clazz.getAnnotation(Formb.class);
			return annotation.value();

		}, Function.identity()));

		formbDict.forEach((dict) -> {
			Class<?> formbClass = frombMap.get(dict.getDictValue());

			try {
				formb.put(dict.getDictValue(), Arrays.asList(formbClass.newInstance()));
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("", e);
			}
		});
		formb.put("B14A", Arrays.asList(new FormB14()));
		formb.put("B14B", Arrays.asList(new FormB14()));

		List<OwnerUnitDanger> dangers = new ArrayList<OwnerUnitDanger>();
		// 查所有formb的隐患数据
		if (CollUtil.isNotEmpty(unitDangerList)) {
			dangers = unitDangerList.stream().filter((d) -> "B".equalsIgnoreCase(d.getFormType()))
					.collect(Collectors.toList());
		}

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
									BeanUtil.setFieldValue(formbBean, "temperature", ownerUnit.getTemperature());
									BeanUtil.setFieldValue(formbBean, "humidity", ownerUnit.getHumidity());
									String detectDate = DateUtil.format(dang.getInitialTime(),
											DatePattern.CHINESE_DATE_FORMATTER);
									BeanUtil.setFieldValue(formbBean, "detectDate", detectDate);
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
					} else {
						formb.put(key, value);
					}
				});
			}

			formb.putAll(collect);

		}
		return formb;
	}

	private InitialReport getReportData(Long reportId) {

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
		if (detectUnit == null) {
			return null;
		}

		InitialReport initialReport = new InitialReport();

		DetectDevice queryDevice = new DetectDevice();
		queryDevice.setDetectId(detectUnit.getId());

		// 检测设备
		List<DetectDevice> detectDevices = detectDeviceService.selectDetectDeviceList(queryDevice);
		if (CollUtil.isNotEmpty(detectDevices)) {
			List<DetectDeviceInfo> deviceInfos = new ArrayList<DetectDeviceInfo>();

			for (int i = 0; i < detectDevices.size(); i++) {
				DetectDevice detectDevice = detectDevices.get(i);
				DetectDeviceInfo deviceInfo = new DetectDeviceInfo();
				deviceInfo.setId(String.valueOf(i + 1));
				deviceInfo.setName(detectDevice.getName());
				deviceInfo.setDeviceId(detectDevice.getDeviceId());
				deviceInfo.setCalibrationDate(
						DateUtil.format(detectDevice.getCalibrationDate(), DatePattern.CHINESE_DATE_FORMATTER));
				deviceInfos.add(deviceInfo);
			}
			initialReport.setDevice(deviceInfos);
		}

		DetectUnitInfo detectUnitInfo = new DetectUnitInfo();
		BeanUtils.copyProperties(detectUnit, detectUnitInfo);

		OwnerUnitReportInfo reportInfo = new OwnerUnitReportInfo();
		BeanUtils.copyProperties(report, reportInfo, "detectData");
		reportInfo.setDetectData(DateUtil.format(report.getDetectData(), DatePattern.CHINESE_DATE_FORMATTER));

		OwnerUnitInfo unitInfo = new OwnerUnitInfo();
		BeanUtils.copyProperties(ownerUnit, unitInfo, "nature", "testStartDate", "testEndDate");
		unitInfo.setTestStartDate(DateUtil.format(ownerUnit.getTestStartDate(), DatePattern.CHINESE_DATE_FORMATTER));
		unitInfo.setTestEndDate(DateUtil.format(ownerUnit.getTestEndDate(), DatePattern.CHINESE_DATE_FORMATTER));

		buildOwnerUnitNatureInfo(ownerUnit, unitInfo);

		buildOwnerUnitDetecContentInfo(ownerUnit, unitInfo);

		// 所有隐患数据
		OwnerUnitDanger dangerQuery = new OwnerUnitDanger();
		dangerQuery.setUnitId(ownerUnit.getId());
		List<OwnerUnitDanger> unitDangerList = ownerUnitDangerService.ownerUnitDangerList(dangerQuery);

		Map<String, List<Object>> formb = buildFormb(ownerUnit, unitDangerList);

		if (CollUtil.isNotEmpty(unitDangerList)) {

			Long dangers = unitDangerList.stream().filter((d) -> "A".equalsIgnoreCase(d.getLevel())).count();
			unitInfo.setDangers(dangers);
		}
		// 符合项-B表
		buildConformb(initialReport, unitDangerList);
		// 不符合项-B表
		buildNConformb(initialReport, unitDangerList);

		// A/C不符合项
		buildNConform(initialReport, unitDangerList);

		initialReport.setCreateDate(DateUtil.format(new Date(), DatePattern.CHINESE_DATE_FORMATTER));
		initialReport.setDetect(detectUnitInfo);
		initialReport.setProject(project);
		initialReport.setUnit(unitInfo);
		initialReport.setReport(reportInfo);
		initialReport.setData(getDetectForm(initialReport.getUnit(), initialReport.getProject(), unitDangerList));
		initialReport.setFormb(formb);

		// Map<String, Object> dataMap = BeanUtil.beanToMap(records);

		return initialReport;
	}

	private void buildConformb(InitialReport initialReport, List<OwnerUnitDanger> unitDangerList) {
		if (CollUtil.isNotEmpty(unitDangerList)) {

			List<UrbanVillageDanger> conformb = initialReport.getConformb();

			// B表符合项且需要汇总
			List<OwnerUnitDanger> conformbDangers = unitDangerList
					.stream().filter((d) -> "B".equals(d.getFormType())
							&& IFormbDangerHandler.QUALIFIED.equals(d.getResult()) && d.isSummary())
					.collect(Collectors.toList());
			if (CollUtil.isNotEmpty(conformbDangers)) {

				Map<String, UrbanVillageDanger> conformbMap = new HashMap<String, UrbanVillageDanger>();
				conformbDangers.forEach((data) -> {
					UrbanVillageDanger danger = conformbMap.get(data.getFormCode());
					if (danger == null) {
						danger = new UrbanVillageDanger();
						danger.setDescription(data.getDescription());
						danger.setSuggestions(data.getSuggestions());

						conformbMap.put(data.getFormCode(), danger);
					}
					danger.getLocations().add(data.getReportLocation());

					String picture = data.getPicture();
					if (StrUtil.isNotBlank(picture)) {
						List<String> split = StrUtil.split(picture, ",");
						if (CollUtil.isNotEmpty(split)) {
							danger.getPictures().addAll(split);
						}
					}

				});
				conformb.addAll(new ArrayList<UrbanVillageDanger>(conformbMap.values()));
			}
		}
	}

	private void buildNConformb(InitialReport initialReport, List<OwnerUnitDanger> unitDangerList) {
		if (CollUtil.isNotEmpty(unitDangerList)) {

			List<UrbanVillageDanger> conformb = initialReport.getNconformb();

			// B表不符合项且需要汇总
			List<OwnerUnitDanger> conformbDangers = unitDangerList.stream()
					.filter((d) -> "B".equals(d.getFormType()) && IFormbDangerHandler.FAILURE.equals(d.getResult())
							&& d.isSummary())
					.filter((d) -> StrUtil.isNotBlank(d.getDescription())).collect(Collectors.toList());
			if (CollUtil.isNotEmpty(conformbDangers)) {

				Map<String, UrbanVillageDanger> conformbMap = new HashMap<String, UrbanVillageDanger>();
				conformbDangers.forEach((data) -> {
					String key = data.getDescription();
					UrbanVillageDanger danger = conformbMap.get(key);
					if (danger == null) {
						danger = new UrbanVillageDanger();
						danger.setDescription(data.getDescription());
						danger.setSuggestions(data.getSuggestions());

						conformbMap.put(key, danger);
					}
					danger.getLocations().add(data.getReportLocation());

					String picture = data.getPicture();
					if (StrUtil.isNotBlank(picture)) {
						List<String> split = StrUtil.split(picture, ",");
						if (CollUtil.isNotEmpty(split)) {
							danger.getPictures().addAll(split);
						}
					}
				});
				conformb.addAll(new ArrayList<UrbanVillageDanger>(conformbMap.values()));
			}
		}
	}

	private void buildNConform(InitialReport initialReport, List<OwnerUnitDanger> unitDangerList) {
		if (CollUtil.isNotEmpty(unitDangerList)) {

			List<UrbanVillageDanger> conform = initialReport.getNconform();

			List<OwnerUnitDanger> dangers = unitDangerList.stream().filter((d) -> !"B".equals(d.getFormType()))
					.filter((d) -> StrUtil.isNotBlank(d.getDescription())).collect(Collectors.toList());
			if (CollUtil.isNotEmpty(dangers)) {

				// 按检测项汇总
				Map<String, UrbanVillageDanger> dangerMap = new HashMap<String, UrbanVillageDanger>();
				dangers.forEach((data) -> {
					String key = data.getDescription();

					UrbanVillageDanger danger = dangerMap.get(key);
					if (danger == null) {
						danger = new UrbanVillageDanger();
						danger.setDescription(data.getDescription());
						danger.setSuggestions(data.getSuggestions());

						dangerMap.put(key, danger);
					}
					danger.getLocations().add(data.getReportLocation());

					String picture = data.getPicture();
					if (StrUtil.isNotBlank(picture)) {
						List<String> split = StrUtil.split(picture, ",");
						if (CollUtil.isNotEmpty(split)) {
							danger.getPictures().addAll(split);
						}
					}
				});
				conform.addAll(new ArrayList<UrbanVillageDanger>(dangerMap.values()));
			}
		}
	}

	private List<DetectFormData> getDetectForm(OwnerUnitInfo ownerUnit, Project project,
			List<OwnerUnitDanger> unitDangerList) {
		// 直观检测表

		List<DetectFormData> formDatas = new ArrayList<DetectFormData>();

		IntuitiveDetectData dataQuery = new IntuitiveDetectData();
		dataQuery.setTemplateId(project.getTemplateId());

		List<IntuitiveDetectData> detectData = intuitiveDetectDataService
				.selectReportIntuitiveDetectDataList(project.getTemplateId(), ownerUnit.getId());
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

				if ("2".equalsIgnoreCase(data.getType())) {

					List<IntuitiveDetectDanger> detectDangers = intuitiveDetectDangerService
							.selectIntuitiveDetectDangersByDataId(data.getId());

					if (CollUtil.isNotEmpty(detectDangers)) {
						formData.setLevel(detectDangers.get(0).getLevel());
					}

					formData.setDecide(data.getDanger() != null && data.getDanger() > 0 ? "不符合" : "符合");
					formData.setResult("√");
				}
				formDatas.add(formData);
			});
		}

		return formDatas;
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

	private InitialReport getReviewReportData(Long reportId) {

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
		if (detectUnit == null) {
			return null;
		}

		InitialReport initialReport = new InitialReport();

		DetectDevice queryDevice = new DetectDevice();
		queryDevice.setDetectId(detectUnit.getId());

		// 检测设备
		List<DetectDevice> detectDevices = detectDeviceService.selectDetectDeviceList(queryDevice);
		if (CollUtil.isNotEmpty(detectDevices)) {
			List<DetectDeviceInfo> deviceInfos = new ArrayList<DetectDeviceInfo>();

			for (int i = 0; i < detectDevices.size(); i++) {
				DetectDevice detectDevice = detectDevices.get(i);
				DetectDeviceInfo deviceInfo = new DetectDeviceInfo();
				deviceInfo.setId(String.valueOf(i + 1));
				deviceInfo.setName(detectDevice.getName());
				deviceInfo.setDeviceId(detectDevice.getDeviceId());
				deviceInfo.setCalibrationDate(
						DateUtil.format(detectDevice.getCalibrationDate(), DatePattern.CHINESE_DATE_FORMATTER));
				deviceInfos.add(deviceInfo);
			}
			initialReport.setDevice(deviceInfos);
		}

		DetectUnitInfo detectUnitInfo = new DetectUnitInfo();
		BeanUtils.copyProperties(detectUnit, detectUnitInfo);

		OwnerUnitReportInfo reportInfo = new OwnerUnitReportInfo();
		BeanUtils.copyProperties(report, reportInfo, "detectData");
		reportInfo.setDetectData(DateUtil.format(report.getDetectData(), DatePattern.CHINESE_DATE_FORMATTER));

		OwnerUnitReivewDateVo ownerUnitReviewDate = ownerUnitReportMapper.getOwnerUnitReviewDate(ownerUnit.getId());
		OwnerUnitReivewDateVo ownerUnitReviewer = ownerUnitReportMapper.getOwnerUnitReviewer(ownerUnit.getId());
		if (ownerUnitReviewer != null) {
			reportInfo.setReviewer(ownerUnitReviewer.getReviewer());
		}

		if (ownerUnitReviewDate != null) {

			if (ownerUnitReviewDate.getStartReviewDate() != null) {
				reportInfo.setStartReviewDate(ownerUnitReviewDate.getStartReviewDate());
			}
			if (ownerUnitReviewDate.getEndReviewDate() != null) {
				reportInfo.setEndReviewDate(ownerUnitReviewDate.getEndReviewDate());
			}
		}

		OwnerUnitInfo unitInfo = new OwnerUnitInfo();
		BeanUtils.copyProperties(ownerUnit, unitInfo, "nature", "testStartDate", "testEndDate");
		unitInfo.setTestStartDate(DateUtil.format(ownerUnit.getTestStartDate(), DatePattern.CHINESE_DATE_FORMATTER));
		unitInfo.setTestEndDate(DateUtil.format(ownerUnit.getTestEndDate(), DatePattern.CHINESE_DATE_FORMATTER));

		// 所有隐患数据
		OwnerUnitDanger dangerQuery = new OwnerUnitDanger();
		dangerQuery.setUnitId(ownerUnit.getId());
		List<OwnerUnitDanger> unitDangerList = ownerUnitDangerService.ownerUnitDangerList(dangerQuery);

		// 符合项
		buildReivewConform(initialReport, unitDangerList);
		// 不符合项
		buildReivewNConform(initialReport, unitDangerList);

		initialReport.setCreateDate(DateUtil.format(new Date(), DatePattern.CHINESE_DATE_FORMATTER));
		initialReport.setDetect(detectUnitInfo);
		initialReport.setProject(project);
		initialReport.setUnit(unitInfo);
		initialReport.setReport(reportInfo);

		return initialReport;
	}

	// 复检符合项
	private void buildReivewConform(InitialReport initialReport, List<OwnerUnitDanger> unitDangerList) {
		if (CollUtil.isNotEmpty(unitDangerList)) {

			List<UrbanVillageDanger> conform = initialReport.getConform();

			List<OwnerUnitDanger> dangers = unitDangerList.stream().filter((d) -> "2".equals(d.getStatus()))
					.collect(Collectors.toList());
			buildReviewDanger(conform, dangers);
		}
	}

	// 复检符合项
	private void buildReivewNConform(InitialReport initialReport, List<OwnerUnitDanger> unitDangerList) {
		if (CollUtil.isNotEmpty(unitDangerList)) {

			List<UrbanVillageDanger> nconform = initialReport.getNconform();

			List<OwnerUnitDanger> dangers = unitDangerList.stream().filter((d) -> !"2".equals(d.getStatus()))
					.collect(Collectors.toList());
			buildReviewDanger(nconform, dangers);
		}
	}

	private void buildReviewDanger(List<UrbanVillageDanger> nconform, List<OwnerUnitDanger> dangers) {
		if (CollUtil.isNotEmpty(dangers)) {

			List<OwnerUnitDanger> acFormDanger = dangers.stream().filter((d) -> !"B".equalsIgnoreCase(d.getFormType()))
					.collect(Collectors.toList());

			// 按检测项汇总
			Map<Long, UrbanVillageDanger> dangerMap = new HashMap<Long, UrbanVillageDanger>();
			acFormDanger.forEach((data) -> {

				Long dangerId = data.getDangerId();
				if (dangerId == null) {
					dangerId = IdUtil.getSnowflakeNextId();
				}

				UrbanVillageDanger danger = dangerMap.get(dangerId);
				if (danger == null) {
					danger = new UrbanVillageDanger();
					danger.setDescription(data.getDescription());
					danger.setSuggestions(data.getSuggestions());

					dangerMap.put(dangerId, danger);
				}
				danger.getLocations().add(data.getReportLocation());

				String picture = data.getPicture();
				if (StrUtil.isNotBlank(picture)) {
					List<String> split = StrUtil.split(picture, ",");
					if (CollUtil.isNotEmpty(split)) {
						danger.getPictures().addAll(split);
					}
				}

				String rectificationPic = data.getRectificationPic();
				if (StrUtil.isNotBlank(rectificationPic)) {
					List<String> split = StrUtil.split(rectificationPic, ",");
					if (CollUtil.isNotEmpty(split)) {
						danger.getRectificationPics().addAll(split);
					}
				}

			});
			nconform.addAll(new ArrayList<UrbanVillageDanger>(dangerMap.values()));

			List<OwnerUnitDanger> bFormDanger = dangers.stream().filter((d) -> "B".equalsIgnoreCase(d.getFormType()))
					.collect(Collectors.toList());

			// 按检测项汇总
			Map<String, UrbanVillageDanger> dangerBMap = new HashMap<String, UrbanVillageDanger>();
			bFormDanger.forEach((data) -> {

				String formCode = data.getFormCode();

				UrbanVillageDanger danger = dangerBMap.get(formCode);
				if (danger == null) {
					danger = new UrbanVillageDanger();
					danger.setDescription(data.getDescription());
					danger.setSuggestions(data.getSuggestions());

					dangerBMap.put(formCode, danger);
				}
				danger.getLocations().add(data.getReportLocation());

				String picture = data.getPicture();
				if (StrUtil.isNotBlank(picture)) {
					List<String> split = StrUtil.split(picture, ",");
					if (CollUtil.isNotEmpty(split)) {
						danger.getPictures().addAll(split);
					}
				}

				String rectificationPic = data.getRectificationPic();
				if (StrUtil.isNotBlank(rectificationPic)) {
					List<String> split = StrUtil.split(rectificationPic, ",");
					if (CollUtil.isNotEmpty(split)) {
						danger.getRectificationPics().addAll(split);
					}
				}

			});
			nconform.addAll(new ArrayList<UrbanVillageDanger>(dangerBMap.values()));
		}
	}

}
