package com.ruoyi.electrical.report.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.SysUser;
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
import com.ruoyi.system.service.ISysDictTypeService;
import com.ruoyi.system.service.ISysUserService;

import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
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

	@Autowired
	private ISysDictTypeService dictTypeService;

	@Autowired
	private ISysUserService sysUserService;

	@Autowired
	private WxMaService wxMaService;

	private static Map<String, String> HIGH_TYPE_NAME_MAP = new HashMap<String, String>();

	private static TextRenderData CHECKED = new TextRenderData("R", new Style("Wingdings 2", 12));

	private static TextRenderData UNCHECK = new TextRenderData("\u00A3", new Style("Wingdings 2", 12));

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
	public int reviewReport(Long reportId) {
		return initialReport(reportId);
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

		buildMngQrcode(ownerUnitInfo);

		try {

			if ("1".equalsIgnoreCase(report.getDetectStatus())) {
				// 无法检测
				ownerUnitInfo.setOpenStatus(StrUtil.format("无法检测({})", report.getIsTestReason()));
			}

			buildOwnerUnitUnitType(ownerUnitInfo, ownerUnit);

			getOwnerUnitConfig(ownerUnitConfig, ownerUnitInfo);

			List<HighDangerInfo> danger = ownerUnitDangerMapper.selectOwnerDangerHighReportByUnitId(ownerUnit.getId());

			HighReportInfo reportInfo = new HighReportInfo();
			reportInfo.setCreateDate(DateUtil.format(new Date(), DatePattern.CHINESE_DATE_FORMATTER));
			reportInfo.setUnit(ownerUnitInfo);
			if (CollUtil.isNotEmpty(danger)) {
				reportInfo.setDanger(danger);

				HighDangerInfo dangerInfo = danger.get(danger.size() - 1);

				SysUser sysUser = sysUserService.selectUserById(dangerInfo.getInspectorId());

				if (sysUser != null) {

					List<String> inspector = new ArrayList<String>();
					if (StrUtil.isNotBlank(sysUser.getNickName())) {
						inspector.add(sysUser.getNickName());
					}
					if (StrUtil.isNotBlank(sysUser.getRecorder())) {
						inspector.add(sysUser.getRecorder());
					}
					ownerUnitInfo.setInspector(String.join(",", inspector));
				}
			}

			log.info("reportInfo:{}", reportInfo);

			LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
			ConfigureBuilder configureBuilder = Configure.builder().useSpringEL().bind("danger", policy);
			Configure config = configureBuilder.build();

			Map<String, Object> dataMap = BeanUtil.beanToMap(reportInfo);

			log.info("dataMap:{}", dataMap);

			NiceXWPFDocument main;

			String template = StrUtil.format("report/initial/high/Initial_Report{}.docx", ownerUnit.getHighRiskType());
			if ("2".equalsIgnoreCase(report.getType())) {
				template = StrUtil.format("report/review/high/Review_Report{}.docx", ownerUnit.getHighRiskType());
			}

			// 标题
			InputStream mainInputStream = ClassPathResource.class.getClassLoader().getResourceAsStream(template);
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
			log.error("生成报告失败！", e);
			return 0;
		} finally {

		}
	}

	private void buildMngQrcode(OwnerUnitInfo ownerUnitInfo) {
		if (StrUtil.isNotBlank(ownerUnitInfo.getMngQrcode())) {
			return;
		}
		try {
			String page = "pages/home/user/index";

			DES des = SecureUtil.des("0185786A0362F7F2B0C316B31D1BAD62".getBytes());

			String key = des.encryptHex(String.valueOf(ownerUnitInfo.getId()));

			WxMaQrcodeService qrcodeService = wxMaService.getQrcodeService();
			byte[] qrCodeByte = qrcodeService.createWxaCodeUnlimitBytes(key, page, false,
					WxMaConstants.DEFAULT_ENV_VERSION, 430, true, null, false);

			LocalDateTime now = LocalDateTime.now();
			String timestamp = DateUtil.format(now, DatePattern.PURE_DATETIME_MS_PATTERN);
			String fileName = timestamp + IdUtil.getSnowflakeNextIdStr();
			String datePath = DateUtil.format(now, "yyyy/MM/dd");

			String filePath = StrUtil.format("{}/{}.png", datePath, fileName);

			String baseDir = RuoYiConfig.getUploadPath();
			File saveFile = FileUploadUtils.getAbsoluteFile(baseDir, filePath);

			ImgUtil.write(ImgUtil.toImage(qrCodeByte), saveFile);

			// String qrcode = ImgUtil.toBase64DataUri(ImgUtil.toImage(qrCodeByte), "png");
			String qrcode = FileUploadUtils.getPathFileName(baseDir, filePath);
			ownerUnitInfo.setMngQrcode(qrcode);

			OwnerUnit update = new OwnerUnit();
			update.setId(ownerUnitInfo.getId());
			update.setMngQrcode(ownerUnitInfo.getMngQrcode());
			ownerUnitMapper.updateOwnerUnit(update);
		} catch (Exception e) {
			log.error("", e);
		}
	}

	/**
	 * 单位类型
	 * 
	 * @param ownerUnitInfo
	 * @param ownerUnit
	 */
	private void buildOwnerUnitUnitType(OwnerUnitInfo ownerUnitInfo, OwnerUnit ownerUnit) {
		List<SysDictData> unitType = new ArrayList<SysDictData>();
		if ("2".equalsIgnoreCase(ownerUnit.getHighRiskType())) {
			// 三小场所
			unitType = dictTypeService.selectDictDataByType("small_unit_type");
		} else if ("4".equalsIgnoreCase(ownerUnit.getHighRiskType())) {
			// 工业企业
			unitType = dictTypeService.selectDictDataByType("industrial_unit_type");
		} else if ("5".equalsIgnoreCase(ownerUnit.getHighRiskType())) {
			// 公共场所
			unitType = dictTypeService.selectDictDataByType("public_places_unit_type");
		} else if ("6".equalsIgnoreCase(ownerUnit.getHighRiskType())) {
			// 大型综合体
			unitType = dictTypeService.selectDictDataByType("public_places_unit_type");
		}

		Map<String, Object> unitTypeMap = ownerUnitInfo.getUnitType();
		String checkedUnitType = ownerUnit.getUnitType();

		unitType.forEach((type) -> {

			if (type.getDictValue().equalsIgnoreCase(checkedUnitType)) {
				unitTypeMap.put(StrUtil.format("item{}", type.getDictValue()), CHECKED);
			} else {
				unitTypeMap.put(StrUtil.format("item{}", type.getDictValue()), UNCHECK);
			}
		});
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
