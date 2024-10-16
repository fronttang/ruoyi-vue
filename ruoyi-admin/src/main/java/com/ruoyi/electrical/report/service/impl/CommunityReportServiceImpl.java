package com.ruoyi.electrical.report.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSON;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.ByteArrayPictureRenderData;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.plugin.table.LoopRowTableRenderPolicy;
import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.electrical.danger.mapper.OwnerUnitDangerMapper;
import com.ruoyi.electrical.danger.service.ComputeHighScoreService;
import com.ruoyi.electrical.dto.OwnerUnitReportPassDto;
import com.ruoyi.electrical.dto.WorkerRoleSettingDto;
import com.ruoyi.electrical.project.domain.Project;
import com.ruoyi.electrical.project.service.IProjectService;
import com.ruoyi.electrical.report.domain.CommunityReport;
import com.ruoyi.electrical.report.domain.OwnerUnitReportLog;
import com.ruoyi.electrical.report.dto.CommunityReportData;
import com.ruoyi.electrical.report.dto.CommunityReportData.UnitTypeGroupData;
import com.ruoyi.electrical.report.dto.CommunityReportData.UnitTypeGroupData.DangerInfo;
import com.ruoyi.electrical.report.dto.CommunityReportOwnerUnit;
import com.ruoyi.electrical.report.dto.EchartRenderingData;
import com.ruoyi.electrical.report.dto.EchartRenderingData.EchartsReportData;
import com.ruoyi.electrical.report.dto.high.HighDangerInfo;
import com.ruoyi.electrical.report.mapper.CommunityReportMapper;
import com.ruoyi.electrical.report.mapper.OwnerUnitReportLogMapper;
import com.ruoyi.electrical.report.service.ICommunityReportService;
import com.ruoyi.electrical.role.domain.DetectUnit;
import com.ruoyi.electrical.role.service.IDetectUnitService;
import com.ruoyi.electrical.util.BigDecimalUtil;
import com.ruoyi.electrical.util.RestTemplateUtils;
import com.ruoyi.electrical.vo.CommunityReportVo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 社区报告Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-10-11
 */
@Slf4j
@Service
public class CommunityReportServiceImpl implements ICommunityReportService {

	@Autowired
	private CommunityReportMapper communityReportMapper;

	@Autowired
	private RedisCache redisCache;

	@Autowired
	private OwnerUnitReportLogMapper reportLogMapper;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IDetectUnitService detectUnitService;

	@Override
	public List<CommunityReportVo> selectCommunityReportList(CommunityReport report) {
		return communityReportMapper.selectCommunityReportList(report);
	}

	@Override
	public CommunityReport getCommunityReportByAreaAndType(CommunityReport data) {
		CommunityReport report = communityReportMapper.getCommunityReportByAreaAndType(data);

		if (report == null) {
			report = new CommunityReport();
			report.setId(IdUtil.getSnowflake().nextId());
			report.setProjectId(data.getProjectId());
			report.setDistrict(data.getDistrict());
			report.setStreet(data.getStreet());
			report.setCommunity(data.getCommunity());
			report.setType("1");
			report.setStatus("0");
			report.setCreateTime(DateUtils.getNowDate());
			report.setUpdateTime(DateUtils.getNowDate());
			communityReportMapper.insertCommunityReport(report);
		}
		return report;
	}

	@Override
	public boolean pass(Long reportId) {

		CommunityReport report = communityReportMapper.selectCommunityReportById(reportId);
		if (report == null) {
			return false;
		}

		SysUser user = SecurityUtils.getLoginUser().getUser();

		WorkerRoleSettingDto workerRole = redisCache
				.getCacheObject(CacheConstants.USER_PROJECT_ROLE_KEY + user.getUserId());

		String workerRoleId = "";
		String workerRoleName = "";

		if (workerRole != null) {
			workerRoleId = workerRole.getId();
			workerRoleName = workerRole.getName();
		}

		CommunityReport update = new CommunityReport();
		update.setId(reportId);
		update.setReject("0");
		update.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
		update.setUpdateTime(DateUtils.getNowDate());

		OwnerUnitReportLog reportLog = new OwnerUnitReportLog();
		reportLog.setReportId(reportId);
		reportLog.setOperator(user.getNickName());
		reportLog.setOperatorId(user.getUserId());
		reportLog.setOperatorRole(workerRoleId);
		reportLog.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
		reportLog.setCreateTime(DateUtils.getNowDate());

		// 未审核 -> 待初审核
		if ("0".equalsIgnoreCase(report.getStatus())) {
			update.setStatus("1");// 2待复审

			reportLog.setOperationType("2");// 2提交初审
			reportLog.setContent(StrUtil.format("由{}（{}）提交初审", workerRoleName, user.getNickName()));

		} else if ("1".equalsIgnoreCase(report.getStatus())) {
			// 初审核通过提交复审核

			update.setStatus("2");// 2待复审

			reportLog.setOperationType("6");// 6完成复审
			reportLog.setContent(StrUtil.format("由{}（{}）通过初审并提交复审", workerRoleName, user.getNickName()));
		} else if ("2".equalsIgnoreCase(report.getStatus())) {
			// 复审通过完成

			update.setStatus("3");// 3完成

			reportLog.setOperationType("4");// 4初审通过提交复审
			reportLog.setContent(StrUtil.format("由{}（{}）通过复审并完结", workerRoleName, user.getNickName()));

		} else {
			return false;
		}

		communityReportMapper.updateCommunityReport(update);
		// 添加日志
		reportLogMapper.insertOwnerUnitReportLog(reportLog);

		return true;
	}

	@Override
	public boolean notPass(OwnerUnitReportPassDto data) {

		CommunityReport report = communityReportMapper.selectCommunityReportById(data.getReportId());
		if (report == null) {
			return false;
		}

		SysUser user = SecurityUtils.getLoginUser().getUser();

		WorkerRoleSettingDto workerRole = redisCache
				.getCacheObject(CacheConstants.USER_PROJECT_ROLE_KEY + user.getUserId());

		String workerRoleId = "";
		String workerRoleName = "";

		if (workerRole != null) {
			workerRoleId = workerRole.getId();
			workerRoleName = workerRole.getName();
		}

		CommunityReport update = new CommunityReport();
		update.setId(report.getId());
		update.setReject("1");
		update.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
		update.setUpdateTime(DateUtils.getNowDate());

		OwnerUnitReportLog reportLog = new OwnerUnitReportLog();
		reportLog.setReportId(report.getId());
		reportLog.setOperator(user.getNickName());
		reportLog.setOperatorId(user.getUserId());
		reportLog.setOperatorRole(workerRoleId);
		reportLog.setRemark(data.getRemark());
		reportLog.setOperationPic(data.getOperationPic());
		reportLog.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
		reportLog.setCreateTime(DateUtils.getNowDate());

		// 报告状态 0未审核，1待初审 2待复审 3完成

		// 操作类型 1初检 2提交初审 3初审驳回 4初审通过提交复审 5复审驳回 6完成复审

		// 状态 未审核 驳回不做处理
		if ("0".equalsIgnoreCase(report.getStatus())) {
			return true;
		} else if ("1".equalsIgnoreCase(report.getStatus())) {
			// 报告状态 0未审核，1待初审 2待复审 3完成

			update.setStatus("0");// 0未审核

			reportLog.setOperationType("3");// 3初审驳回
			reportLog.setContent(StrUtil.format("由{}（{}）驳回", workerRoleName, user.getNickName()));
		} else if ("2".equalsIgnoreCase(report.getStatus())) {

			update.setStatus("1");// 1待初审

			reportLog.setOperationType("5");// 5复审驳回
			reportLog.setContent(StrUtil.format("由{}（{}）驳回", workerRoleName, user.getNickName()));

		} else {
			return false;
		}

		communityReportMapper.updateCommunityReport(update);
		// 添加日志
		reportLogMapper.insertOwnerUnitReportLog(reportLog);

		return true;
	}

	@Override
	public boolean resetStatusByReportId(Long reportId) {

		CommunityReport report = communityReportMapper.selectCommunityReportById(reportId);
		if (report == null) {
			return false;
		} else {

			CommunityReport update = new CommunityReport();
			update.setId(report.getId());
			update.setStatus("0");
			update.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
			update.setUpdateTime(DateUtils.getNowDate());

			SysUser user = SecurityUtils.getLoginUser().getUser();

			WorkerRoleSettingDto workerRole = redisCache
					.getCacheObject(CacheConstants.USER_PROJECT_ROLE_KEY + user.getUserId());

			String workerRoleId = "";
			String workerRoleName = "";

			if (workerRole != null) {
				workerRoleId = workerRole.getId();
				workerRoleName = workerRole.getName();
			}

			OwnerUnitReportLog reportLog = new OwnerUnitReportLog();
			reportLog.setReportId(report.getId());
			reportLog.setOperator(user.getNickName());
			reportLog.setOperatorId(user.getUserId());
			reportLog.setOperatorRole(workerRoleId);
			reportLog.setRemark(null);
			reportLog.setOperationPic(null);
			reportLog.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
			reportLog.setCreateTime(DateUtils.getNowDate());
			reportLog.setOperationType("7");// 7重置为未初审
			reportLog.setContent(StrUtil.format("由{}（{}）重置为未审核", workerRoleName, user.getNickName()));

			communityReportMapper.updateCommunityReport(update);
			// 添加日志
			reportLogMapper.insertOwnerUnitReportLog(reportLog);
		}
		return true;

	}

	@Autowired
	private OwnerUnitDangerMapper ownerUnitDangerMapper;

	public static final Map<String, String> HIGH_TYPE_NAME_MAP = new HashMap<String, String>();

	static {
		HIGH_TYPE_NAME_MAP.put("1", "出租屋");
		HIGH_TYPE_NAME_MAP.put("2", "三小场所");
		HIGH_TYPE_NAME_MAP.put("3", "住宅小区");
		HIGH_TYPE_NAME_MAP.put("4", "工业企业");
		HIGH_TYPE_NAME_MAP.put("5", "公共场所");
	}

	private ComputeHighScoreService compute = new ComputeHighScoreService();

	@Override
	public String wordReport(Long reportId) {
		CommunityReport report = communityReportMapper.selectCommunityReportById(reportId);
		if (report != null) {

			Project project = projectService.selectProjectById(report.getProjectId());

			if (project == null) {
				return null;
			}

			DetectUnit detectUnit = detectUnitService.selectDetectUnitById(project.getDetectId());
			if (detectUnit == null) {
				return null;
			}

			// 查询社区所有的业主单元
			List<CommunityReportOwnerUnit> ownerUnit = communityReportMapper.getCommunityOwnerUnitList(report);
			if (CollUtil.isNotEmpty(ownerUnit)) {
				ownerUnit.forEach((unit) -> {
					List<HighDangerInfo> dangers = ownerUnitDangerMapper
							.selectOwnerDangerHighReportByUnitId(unit.getId());
					unit.setDanger(dangers);
					unit.setScore(compute.compute(dangers));
				});

				CommunityReportData data = new CommunityReportData();
				data.setDistrictName(report.getDistrictName());
				data.setStreetName(report.getStreetName());
				data.setDetectName(detectUnit.getName());
				data.setReportDate(DateUtil.format(new Date(), DatePattern.CHINESE_DATE_FORMATTER));

				// 三小场所
				List<CommunityReportOwnerUnit> smallOwnerUnit = ownerUnit.stream()
						.filter((d) -> "2".equalsIgnoreCase(d.getType())).collect(Collectors.toList());
				UnitTypeGroupData small = buildUnitGroupData(smallOwnerUnit, "2", HIGH_TYPE_NAME_MAP.get("2"));
				data.setSmall(small);

				// 出租屋
				List<CommunityReportOwnerUnit> rentalHouseOwnerUnit = ownerUnit.stream()
						.filter((d) -> "1".equalsIgnoreCase(d.getType())).collect(Collectors.toList());
				UnitTypeGroupData rentalHouse = buildUnitGroupData(rentalHouseOwnerUnit, "1",
						HIGH_TYPE_NAME_MAP.get("1"));
				data.setRentalHouse(rentalHouse);

				// 公共场所
				List<CommunityReportOwnerUnit> publicPlacesOwnerUnit = ownerUnit.stream()
						.filter((d) -> "5".equalsIgnoreCase(d.getType()) || "6".equalsIgnoreCase(d.getType()))
						.collect(Collectors.toList());
				UnitTypeGroupData publicPlaces = buildUnitGroupData(publicPlacesOwnerUnit, "5",
						HIGH_TYPE_NAME_MAP.get("5"));
				data.setPublicPlaces(publicPlaces);

				// 工业企业
				List<CommunityReportOwnerUnit> industrialOwnerUnit = ownerUnit.stream()
						.filter((d) -> "4".equalsIgnoreCase(d.getType())).collect(Collectors.toList());
				UnitTypeGroupData industrial = buildUnitGroupData(industrialOwnerUnit, "4",
						HIGH_TYPE_NAME_MAP.get("4"));
				data.setIndustrial(industrial);

				// 住宅小区
				List<CommunityReportOwnerUnit> residentialOwnerUnit = ownerUnit.stream()
						.filter((d) -> "3".equalsIgnoreCase(d.getType())).collect(Collectors.toList());
				UnitTypeGroupData residential = buildUnitGroupData(residentialOwnerUnit, "3",
						HIGH_TYPE_NAME_MAP.get("3"));
				data.setResidential(residential);

				// 分组数据
				List<UnitTypeGroupData> groupData = new ArrayList<UnitTypeGroupData>();
				groupData.add(small);
				groupData.add(rentalHouse);
				groupData.add(publicPlaces);
				groupData.add(industrial);
				groupData.add(residential);

				data.setGroupData(groupData);

				// 累计数据
				UnitTypeGroupData total = new UnitTypeGroupData();
				total.setUnitTotal(groupData.stream().mapToLong(UnitTypeGroupData::getUnitTotal).sum());
				total.setDetectedUnit(groupData.stream().mapToLong(UnitTypeGroupData::getDetectedUnit).sum());

				total.setDangerTotal(groupData.stream().mapToLong(UnitTypeGroupData::getDangerTotal).sum());
				total.setDangerFinish(groupData.stream().mapToLong(UnitTypeGroupData::getDangerFinish).sum());
				// 整改率
				BigDecimal finishRate = BigDecimal.valueOf(total.getDangerFinish())
						.divide(BigDecimal.valueOf(total.getDangerTotal()), 8, RoundingMode.HALF_EVEN);
				finishRate = finishRate.multiply(BigDecimal.valueOf(100));
				total.setFinishRate(finishRate);

				data.setTotal(total);

				try {
					LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
					ConfigureBuilder configureBuilder = Configure.builder().useSpringEL();
					configureBuilder = configureBuilder.bind("small.dangerInfo", policy);
					configureBuilder = configureBuilder.bind("rentalHouse.dangerInfo", policy);
					configureBuilder = configureBuilder.bind("publicPlaces.dangerInfo", policy);
					configureBuilder = configureBuilder.bind("industrial.dangerInfo", policy);
					configureBuilder = configureBuilder.bind("residential.dangerInfo", policy);
					configureBuilder = configureBuilder.bind("groupData", policy);
					Configure config = configureBuilder.build();

					Map<String, Object> dataMap = BeanUtil.beanToMap(data);

					log.info("dataMap:{}", JSON.toJSONString(dataMap));

					NiceXWPFDocument main;

					InputStream mainInputStream = ClassPathResource.class.getClassLoader()
							.getResourceAsStream("report/community/community_report.docx");
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

					Integer wordFileVersion = report.getWordFileVersion();

					if (wordFileVersion == null) {
						wordFileVersion = 1;
					} else {
						wordFileVersion = wordFileVersion + 1;
					}

					CommunityReport result = new CommunityReport();
					result.setId(reportId);
					result.setWordFileVersion(wordFileVersion);
					result.setWordFile(FileUploadUtils.getPathFileName(baseDir, filePath));

					communityReportMapper.updateCommunityReport(result);

					return FileUploadUtils.getPathFileName(baseDir, filePath);

				} catch (Exception e) {
					log.error("", e);
				}
			}
		}
		return null;
	}

	private UnitTypeGroupData buildUnitGroupData(List<CommunityReportOwnerUnit> ownerUnit, String type, String name) {
		UnitTypeGroupData data = new UnitTypeGroupData();
		if (CollUtil.isNotEmpty(ownerUnit)) {
			data.setType(type);
			data.setName(name);

			final Long unitTotal = Long.valueOf(ownerUnit.size());
			// 总业主单元
			data.setUnitTotal(Long.valueOf(ownerUnit.size()));

			// 已检测业主单元
			long detected = ownerUnit.stream().filter((e) -> Objects.nonNull(e.getReportId())).count();
			data.setDetectedUnit(detected);

			// 总得分
			Optional<BigDecimal> totalScoreOptional = ownerUnit.stream().map(CommunityReportOwnerUnit::getScore)
					.reduce(BigDecimal::add);
			BigDecimal totalScore = totalScoreOptional.isPresent() ? totalScoreOptional.get() : BigDecimal.ZERO;
			data.setTotalScore(totalScore);

			// 平均得分
			data.setAverageScore(totalScore.divide(BigDecimal.valueOf(ownerUnit.size()), 8, RoundingMode.HALF_EVEN));

			// 隐患数据
			List<HighDangerInfo> dangers = ownerUnit.stream().map(CommunityReportOwnerUnit::getDanger)
					.flatMap((d) -> d.stream()).collect(Collectors.toList());

			if (CollUtil.isNotEmpty(dangers)) {

				// data.setDangers(dangers);

				final Long dangerTotal = Long.valueOf(dangers.size());
				// 总隐患
				data.setDangerTotal(dangerTotal);
				// 整改隐患
				long dangerFinish = dangers.stream().filter((d) -> "2".equals(d.getStatusCode())).count();
				data.setDangerFinish(dangerFinish);

				// 整改率
				BigDecimal finishRate = BigDecimal.valueOf(data.getDangerFinish())
						.divide(BigDecimal.valueOf(data.getDangerTotal()), 8, RoundingMode.HALF_EVEN);
				finishRate = finishRate.multiply(BigDecimal.valueOf(100));
				data.setFinishRate(finishRate);

				// 隐患汇总数据
				List<DangerInfo> dangerInfos = new ArrayList<DangerInfo>();

				Map<String, List<HighDangerInfo>> dangerGroupMap = dangers.stream()
						.collect(Collectors.groupingBy(HighDangerInfo::getDescription, Collectors.toList()));

				AtomicLong index = new AtomicLong(1);
				dangerGroupMap.forEach((description, dgs) -> {
					HighDangerInfo highDangerInfo = dgs.get(0);

					DangerInfo di = new DangerInfo();
					di.setCode(index.getAndIncrement());
					di.setDescription(highDangerInfo.getDescription());
					di.setSuggestions(highDangerInfo.getSuggestions());
					di.setFirstCode(highDangerInfo.getFirstCode());
					di.setScore(highDangerInfo.getScore());

					List<String> dangerPics = dgs.stream().filter((d) -> StrUtil.isNotBlank(d.getDangerPic()))
							.map((d) -> d.getDangerPic()).flatMap(str -> Arrays.stream(str.split(",")))
							.collect(Collectors.toList());
					di.setDangerPics(dangerPics);

					long unitCount = dgs.stream().map(HighDangerInfo::getUnitId).distinct().count();
					di.setUnitCount(unitCount);

					// 占场所总数比例
					BigDecimal unitRate = BigDecimal.valueOf(unitCount).divide(BigDecimal.valueOf(unitTotal), 8,
							RoundingMode.HALF_EVEN);
					unitRate = unitRate.multiply(BigDecimal.valueOf(100));
					di.setUnitRate(unitRate);

					Long dangerCount = Long.valueOf(dgs.size());
					di.setDangerCount(dangerCount);

					// 占问题总数比例
					BigDecimal dangerRate = BigDecimal.valueOf(dangerCount).divide(BigDecimal.valueOf(dangerTotal), 8,
							RoundingMode.HALF_EVEN);
					dangerRate = dangerRate.multiply(BigDecimal.valueOf(100));
					di.setDangerRate(dangerRate);

					dangerInfos.add(di);
				});

				data.setDangerInfo(dangerInfos);
				data.setPieImage1(getPieImage1(data));
				data.setPieImage2(getPieImage2(data));
			}
		}
		return data;
	}

	public ByteArrayPictureRenderData getPieImage1(UnitTypeGroupData data) {
		EchartRenderingData param = new EchartRenderingData();

		List<DangerInfo> dangerInfos = data.getDangerInfo();
		if (CollUtil.isNotEmpty(dangerInfos)) {

			int i = 1;
			List<EchartsReportData> paramDatas = new ArrayList<EchartRenderingData.EchartsReportData>();
			for (DangerInfo danger : dangerInfos) {
				EchartsReportData paramData = new EchartsReportData();
				paramData.setName(StrUtil.format("问题{}", i));
				paramData.setValue(danger.getUnitCount());
				paramData.setRate(StrUtil.format("{}%", BigDecimalUtil.roundHalfUp(danger.getUnitRate(), 2)));
				paramDatas.add(paramData);
				i++;
			}
			param.setData(paramDatas);
			return getPieImage(param);
		}
		return null;
	}

	public ByteArrayPictureRenderData getPieImage2(UnitTypeGroupData data) {
		EchartRenderingData param = new EchartRenderingData();

		List<DangerInfo> dangerInfos = data.getDangerInfo();
		if (CollUtil.isNotEmpty(dangerInfos)) {

			int i = 1;
			List<EchartsReportData> paramDatas = new ArrayList<EchartRenderingData.EchartsReportData>();
			for (DangerInfo danger : dangerInfos) {
				EchartsReportData paramData = new EchartsReportData();
				paramData.setName(StrUtil.format("问题{}", i));
				paramData.setValue(danger.getDangerCount());
				paramData.setRate(StrUtil.format("{}%", BigDecimalUtil.roundHalfUp(danger.getDangerRate(), 2)));
				paramDatas.add(paramData);
				i++;
			}
			param.setData(paramDatas);
			return getPieImage(param);
		}
		return null;
	}

	public ByteArrayPictureRenderData getPieImage(EchartRenderingData param) {
		try {
			AjaxResult result = RestTemplateUtils.exchange(RuoYiConfig.getEcharts().getApi(), HttpMethod.POST, null,
					param, AjaxResult.class);

			if (result != null && Objects.nonNull(result.get("data"))) {

				String data = String.valueOf(result.get("data"));

				BufferedImage image = ImgUtil.toImage(StrUtil.subAfter(data, ",", false));

				ByteArrayPictureRenderData picture = new ByteArrayPictureRenderData(ImgUtil.toBytes(image, "png"));
				PictureStyle pictureStyle = new PictureStyle();
				pictureStyle.setScalePattern(WidthScalePattern.FIT);
				picture.setPictureStyle(pictureStyle);
				return picture;
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

}
