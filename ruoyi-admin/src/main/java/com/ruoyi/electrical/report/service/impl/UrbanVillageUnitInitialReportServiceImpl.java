package com.ruoyi.electrical.report.service.impl;

import java.io.File;
import java.io.FileOutputStream;
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

import com.alibaba.fastjson2.JSONObject;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.FilePictureRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
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
import com.ruoyi.electrical.report.dto.InitialReport;
import com.ruoyi.electrical.report.dto.OwnerUnitInfo;
import com.ruoyi.electrical.report.dto.OwnerUnitReportInfo;
import com.ruoyi.electrical.report.service.IOwnerUnitReportService;
import com.ruoyi.electrical.report.service.IUrbanVillageUnitInitialReportService;
import com.ruoyi.electrical.role.domain.DetectUnit;
import com.ruoyi.electrical.role.service.IDetectUnitService;
import com.ruoyi.electrical.template.domain.IntuitiveDetectDanger;
import com.ruoyi.electrical.template.domain.IntuitiveDetectData;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDangerService;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDataService;
import com.ruoyi.system.service.ISysDictDataService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
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
	public int initialReport(Long reportId) {

		InitialReport initialReport = getReportData(reportId);
		if (initialReport == null) {
			return 0;
		}

		log.info("initialReport : " + initialReport);

		LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
		ConfigureBuilder configureBuilder = Configure.builder().useSpringEL().bind("data", policy)
				.bind("device", policy).bind("formb.B1", policy).bind("formb.BB1", policy).bind("formb.B2", policy)
				.bind("formb.B3", policy).bind("formb.B4", policy).bind("formb.B5", policy).bind("formb.B6", policy)
				.bind("formb.B7", policy).bind("formb.B8", policy).bind("formb.B9", policy).bind("formb.B10", policy)
				.bind("formb.B11", policy).bind("formb.B12", policy).bind("formb.B13", policy).bind("formb.B14", policy)
				.bind("formb.B15", policy);
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

			// return AjaxResult.success(data);
		} catch (Exception e) {
			log.error("生成电气检测初检报告失败！", e);
			return 0;
		} finally {

		}
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
			Class<?> formbClass = frombMap.get(dict.getDictValue());

			try {
				formb.put(dict.getDictValue(), Arrays.asList(formbClass.newInstance()));
			} catch (InstantiationException | IllegalAccessException e) {
				log.error("", e);
			}
		});

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
						if (frombMap.get(dang.getFormCode()) != null) {
							try {
								Object newInstance = frombMap.get(dang.getFormCode()).newInstance();
								if (Objects.nonNull(formb)) {
									JSONObject formbData = formbJson.getJSONObject("data");
									if (Objects.nonNull(formbData)) {
										BeanUtils.copyProperties(formbData, newInstance);
									}
								}
								return newInstance;
							} catch (InstantiationException | IllegalAccessException e) {
								log.error("", e);
							}
						}
						return formbJson;
					}, Collectors.toList())));

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
		BeanUtils.copyProperties(detectUnit, detectUnitInfo, "qualification", "logo");

		if (StrUtil.isNotBlank(detectUnit.getQualification())) {

			// 本地资源路径
			String localPath = RuoYiConfig.getProfile();
			// 数据库资源地址
			String filePath = localPath
					+ StringUtils.substringAfter(detectUnit.getQualification(), Constants.RESOURCE_PREFIX);

			FilePictureRenderData qualification = new FilePictureRenderData(filePath);
			PictureStyle pictureStyle = new PictureStyle();
			pictureStyle.setScalePattern(WidthScalePattern.FIT);
			qualification.setPictureStyle(pictureStyle);
			detectUnitInfo.setQualification(qualification);
		}

		if (StrUtil.isNotBlank(detectUnit.getLogo())) {

			// 本地资源路径
			String localPath = RuoYiConfig.getProfile();
			// 数据库资源地址
			String filePath = localPath + StringUtils.substringAfter(detectUnit.getLogo(), Constants.RESOURCE_PREFIX);

			FilePictureRenderData logo = new FilePictureRenderData(filePath);

			PictureStyle pictureStyle = new PictureStyle();
			pictureStyle.setWidth(50);
			pictureStyle.setHeight(50);
			logo.setPictureStyle(pictureStyle);
			detectUnitInfo.setLogo(logo);
		}

		OwnerUnitReportInfo reportInfo = new OwnerUnitReportInfo();
		BeanUtils.copyProperties(report, reportInfo, "detectData");
		reportInfo.setDetectData(DateUtil.format(report.getDetectData(), DatePattern.CHINESE_DATE_FORMATTER));

		OwnerUnitInfo unitInfo = new OwnerUnitInfo();
		BeanUtils.copyProperties(ownerUnit, unitInfo, "nature", "testStartDate", "testEndDate");
		unitInfo.setTestStartDate(DateUtil.format(ownerUnit.getTestStartDate(), DatePattern.CHINESE_DATE_FORMATTER));
		unitInfo.setTestEndDate(DateUtil.format(ownerUnit.getTestEndDate(), DatePattern.CHINESE_DATE_FORMATTER));

		buildOwnerUnitNatureInfo(ownerUnit, unitInfo);

		buildOwnerUnitDetecContentInfo(ownerUnit, unitInfo);

		Map<String, List<Object>> formb = queryFormb(ownerUnit);

		// getDetectForm(ownerUnit, project);

		initialReport.setCreateDate(DateUtil.format(new Date(), DatePattern.CHINESE_DATE_FORMATTER));
		initialReport.setDetect(detectUnitInfo);
		initialReport.setProject(project);
		initialReport.setUnit(unitInfo);
		initialReport.setReport(reportInfo);
		initialReport.setData(getDetectForm(initialReport.getUnit(), initialReport.getProject()));
		initialReport.setFormb(formb);

		// Map<String, Object> dataMap = BeanUtil.beanToMap(records);
		return initialReport;
	}

	private List<DetectFormData> getDetectForm(OwnerUnitInfo ownerUnit, Project project) {
		// 直观检测表

		List<DetectFormData> formDatas = new ArrayList<DetectFormData>();

		IntuitiveDetectData dataQuery = new IntuitiveDetectData();
		dataQuery.setTemplateId(project.getTemplateId());

		List<IntuitiveDetectData> detectData = intuitiveDetectDataService.selectIntuitiveDetectDataList(dataQuery);
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

					Long dangers = intuitiveDetectDangerService.countDangersByDataIdAndUnitId(data.getId(),
							ownerUnit.getId());
					formData.setDecide(dangers != null && dangers > 0 ? "不符合" : "符合");
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

}
