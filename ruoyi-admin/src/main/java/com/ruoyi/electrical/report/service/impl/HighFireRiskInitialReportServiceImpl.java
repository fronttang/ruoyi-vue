package com.ruoyi.electrical.report.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.electrical.danger.mapper.OwnerUnitDangerMapper;
import com.ruoyi.electrical.project.domain.OwnerUnit;
import com.ruoyi.electrical.project.domain.OwnerUnitConfig;
import com.ruoyi.electrical.project.mapper.OwnerUnitMapper;
import com.ruoyi.electrical.project.service.IOwnerUnitConfigService;
import com.ruoyi.electrical.report.annotation.HighConfig;
import com.ruoyi.electrical.report.domain.OwnerUnitReport;
import com.ruoyi.electrical.report.dto.high.HighDangerInfo;
import com.ruoyi.electrical.report.dto.high.HighReportInfo;
import com.ruoyi.electrical.report.dto.high.OwnerUnitInfo;
import com.ruoyi.electrical.report.service.IHighFireRiskInitialReportService;
import com.ruoyi.electrical.report.service.IOwnerUnitReportService;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HighFireRiskInitialReportServiceImpl implements IHighFireRiskInitialReportService {

	@Autowired
	private OwnerUnitMapper ownerUnitMapper;

	@Autowired
	private IOwnerUnitReportService unitReportService;

	@Autowired
	private IOwnerUnitConfigService ownerUnitConfigService;

	@Autowired
	private OwnerUnitDangerMapper ownerUnitDangerMapper;

	private static Map<String, String> HIGH_TYPE_NAME_MAP = new HashMap<String, String>();

	private static Set<Class<?>> allConfigBeans = new HashSet<Class<?>>();
	static {
		HIGH_TYPE_NAME_MAP.put("1", "出租屋");
		HIGH_TYPE_NAME_MAP.put("2", "三小场所");
		HIGH_TYPE_NAME_MAP.put("3", "住宅小区");
		HIGH_TYPE_NAME_MAP.put("4", "工业企业");
		HIGH_TYPE_NAME_MAP.put("5", "公共场所");
		HIGH_TYPE_NAME_MAP.put("6", "大型综合体");

		Reflections reflections = new Reflections(
				new ConfigurationBuilder().forPackages("com.ruoyi.electrical.report.config")
						.filterInputsBy(new FilterBuilder()
								.includePackage("BOOT-INF.classes.com.ruoyi.electrical.report.config")
								.includePackage("com.ruoyi.electrical.report.config"))
						.setScanners(Scanners.TypesAnnotated));

		allConfigBeans = reflections.getTypesAnnotatedWith(HighConfig.class);
	}

	@Override
	public int initialReport(Long reportId) {

		OwnerUnitReport report = unitReportService.selectOwnerUnitReportById(reportId);
		if (report == null) {
			return 0;
		}

		OwnerUnit ownerUnit = ownerUnitMapper.getOwnerUnitById(report.getUnitId());

		if (ownerUnit == null) {
			return 0;
		}

		OwnerUnitConfig ownerUnitConfig = ownerUnitConfigService.selectOwnerUnitConfigByUnitId(ownerUnit.getId());

		OwnerUnitInfo ownerUnitInfo = new OwnerUnitInfo();
		BeanUtils.copyProperties(ownerUnit, ownerUnitInfo);

		try {
			getOwnerUnitConfig(ownerUnitConfig, ownerUnitInfo);

			List<HighDangerInfo> danger = ownerUnitDangerMapper.selectOwnerDangerHighReportByUnitId(ownerUnit.getId());

			HighReportInfo reportInfo = new HighReportInfo();
			reportInfo.setCreateDate(DateUtil.format(new Date(), DatePattern.CHINESE_DATE_FORMATTER));
			reportInfo.setUnit(ownerUnitInfo);
			if (CollUtil.isNotEmpty(danger)) {
				reportInfo.setDanger(danger);
			}

			log.info("reportInfo:{}", reportInfo);

			LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
			ConfigureBuilder configureBuilder = Configure.builder().useSpringEL().bind("danger", policy);
			Configure config = configureBuilder.build();

			Map<String, Object> dataMap = BeanUtil.beanToMap(reportInfo);

			log.info("dataMap:{}", dataMap);

			NiceXWPFDocument main;

			// 标题
			InputStream mainInputStream = ClassPathResource.class.getClassLoader().getResourceAsStream(
					StrUtil.format("report/initial/high/Initial_Report{}.docx", ownerUnit.getHighRiskType()));
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

			Integer wordFileVersion = report.getWordFileVersion();

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

	private void getOwnerUnitConfig(OwnerUnitConfig ownerUnitConfig, OwnerUnitInfo ownerUnitInfo)
			throws InstantiationException, IllegalAccessException {

		Map<String, Class<?>> configBeanMap = new HashMap<String, Class<?>>();

		if (CollUtil.isNotEmpty(allConfigBeans)) {

			allConfigBeans.forEach((clazz) -> {
				HighConfig annotation = clazz.getAnnotation(HighConfig.class);
				configBeanMap.put(annotation.value(), clazz);
			});

		}

		log.info("configBeanMap:{}", configBeanMap);

		Class<?> configclass = configBeanMap.get(ownerUnitInfo.getHighRiskType());

		if (ownerUnitConfig != null && Objects.nonNull(ownerUnitConfig.getConfig()) && configclass != null) {

			Object config = ownerUnitConfig.getConfig().toJavaObject(configclass);

			ownerUnitInfo.setConfig(BeanUtil.beanToMap(config));
		} else if (configclass != null) {
			ownerUnitInfo.setConfig(BeanUtil.beanToMap(configclass.newInstance()));
		} else {
			ownerUnitInfo.getConfig().put("doorPicture", null);
			ownerUnitInfo.getConfig().put("businessLicensePic", null);
		}
		log.info("config json:{}", ownerUnitInfo.getConfig());
	}

}
