package com.ruoyi.electrical.report.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.ruoyi.electrical.report.dto.station.StationDangerTableRenderPolicy;
import com.ruoyi.electrical.report.dto.station.StationForm;
import com.ruoyi.electrical.report.dto.station.StationFormData;
import com.ruoyi.electrical.report.dto.station.StationInitialReport;
import com.ruoyi.electrical.report.dto.station.StationOwnerUnitInfo;
import com.ruoyi.electrical.report.dto.station.StationPeprePic;
import com.ruoyi.electrical.report.dto.station.StationPic;
import com.ruoyi.electrical.report.dto.station.StationPile;
import com.ruoyi.electrical.report.dto.station.StationPileForm;
import com.ruoyi.electrical.report.dto.station.StationPileForm.StationPileFormData;
import com.ruoyi.electrical.report.dto.station.StationPileForm.StationPileFormDatas;
import com.ruoyi.electrical.report.dto.station.StationPileFormTableRenderPolicy;
import com.ruoyi.electrical.report.service.IChargingStationInitialReportService;
import com.ruoyi.electrical.report.service.IOwnerUnitReportService;
import com.ruoyi.electrical.role.domain.DetectUnit;
import com.ruoyi.electrical.role.service.IDetectUnitService;
import com.ruoyi.electrical.template.mapper.IntuitiveDetectDataMapper;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
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

	private static final Map<String, String> OVERVIEW_MODULE_MAP = new LinkedHashMap<String, String>();

	static {
		MODULE_MAP.put("1", "整体安全检查");
		MODULE_MAP.put("2", "用电安全检查");
		MODULE_MAP.put("3", "消防设施安全检查");
		MODULE_MAP.put("4", "电化学储能设施安全检查");
		MODULE_MAP.put("5", "场站其他检查");
		MODULE_MAP.put("6", "充电系统检查");

		OVERVIEW_MODULE_MAP.put("1", "1、整体安全");
		OVERVIEW_MODULE_MAP.put("2", "2、用电安全");
		OVERVIEW_MODULE_MAP.put("6", "3、充电系统安全");
		OVERVIEW_MODULE_MAP.put("3", "4、消防安全");
		OVERVIEW_MODULE_MAP.put("4", "5、储能系统安全");
	}

	@Override
	public int reviewReport(Long reportId) {
		return 0;
	}

	@Override
	public int initialReport(Long reportId) {

		StationInitialReport report = getStationInitialReport(reportId);
		try {

			log.info("reportInfo:{}", report);

			LoopRowTableRenderPolicy policy = new LoopRowTableRenderPolicy();
			ConfigureBuilder configureBuilder = Configure.builder().useSpringEL().bind("pileGroup", policy)
					.bind("piles", policy).bind("form", policy).bind("data", policy).bind("form.data", policy)
					.bind("danger", new StationDangerTableRenderPolicy(report.getUnit()))
					.bind("pileFormData", new StationPileFormTableRenderPolicy()).bind("dangerPic", policy);
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

		List<ChargingPileInfo> pileGroup = chargingPileService.selectStationPileList(ownerUnit.getId());
		data.setPileGroup(pileGroup);

		List<StationForm> forms = new ArrayList<StationForm>();

		List<StationDanger> dangers = new ArrayList<StationDanger>();

		List<StationFormData> scoreDatas = new ArrayList<StationFormData>();

		boolean pileForm = false;

		List<StationFormData> formDatas = detectDataMapper.selectStationDetectData(project.getTemplateId(),
				ownerUnit.getId());

		List<StationDanger> stationDangerList = ownerUnitDangerMapper.stationReportDangerList(ownerUnit.getId());

		if (CollUtil.isNotEmpty(formDatas)) {
			scoreDatas.addAll(formDatas.stream().filter((d) -> d.getDangers() > 0).collect(Collectors.toList()));
			unitInfo.setScoreDatas(scoreDatas);
		}

		String detectModule = ownerUnit.getDetectModule();

		if (StrUtil.isNotBlank(detectModule)) {

			List<String> moduleList = StrUtil.split(detectModule, ",");
			moduleList.sort(Comparator.naturalOrder());

			pileForm = moduleList.contains("6");

			for (int i = 0; i < moduleList.size(); i++) {

				String module = moduleList.get(i);

				if (!"6".equalsIgnoreCase(module)) {
					StationForm form = new StationForm();
					form.setName(MODULE_MAP.get(module));

					List<StationFormData> formData = formDatas.stream()
							.filter((d) -> module.equalsIgnoreCase(d.getDetectModule())).collect(Collectors.toList());
					form.setData(formData);
					forms.add(form);
				}
			}
		}

		OVERVIEW_MODULE_MAP.forEach((module, name) -> {
			StationDanger danger = new StationDanger();
			danger.setLocation(name);
			danger.setMerge(true);
			dangers.add(danger);

			if (CollUtil.isNotEmpty(stationDangerList)) {
				List<StationDanger> dList = stationDangerList.stream()
						.filter((d) -> module.equalsIgnoreCase(d.getFormId())).collect(Collectors.toList());

				Map<Long, StationDanger> dangerMap = new HashMap<Long, StationDanger>();
				// 合并相同dangerId数据
				if (CollUtil.isNotEmpty(dList)) {
					dList.forEach((dan) -> {
						Long dangerId = dan.getDangerId();
						if (dangerId == null) {
							dangerId = IdUtil.getSnowflakeNextId();
						}
						StationDanger stationDanger = dangerMap.get(dangerId);
						if (stationDanger == null) {
							stationDanger = dan;
							dangerMap.put(dangerId, dan);
						}

						String location = dan.getLocation();
						if (StrUtil.isNotBlank(dan.getChargingPileName())) {
							location = StrUtil.format("({}) {}", dan.getChargingPileName(), location);
						}
						stationDanger.getLocations().add(location);
					});
				}

				if (CollUtil.isNotEmpty(dangerMap)) {
					dangers.addAll(new ArrayList<StationDanger>(dangerMap.values()));
				} else {
					StationDanger noDanger = new StationDanger();
					noDanger.setLocation("无");
					noDanger.setDescription("/");
					noDanger.setSuggestions("/");
					dangers.add(noDanger);
				}
			} else {
				StationDanger noDanger = new StationDanger();
				noDanger.setLocation("无");
				noDanger.setDescription("/");
				noDanger.setSuggestions("/");
				dangers.add(noDanger);
			}
		});

		data.setForm(forms);
		data.setDanger(dangers);

		if (pileForm) {
			data.setPileForm(buildPileForm(data, project, chargingPiles, formDatas, stationDangerList));
		}
		// 隐患图片
		data.setDangerPic(buildDangerPic(stationDangerList));

		// 代表照片
		List<StationPeprePic> peprePictures = chargingPileService.getStationPeprePicture(ownerUnit.getId(),
				ownerUnit.getRounds());
		data.setPic(buildPeprePictures(peprePictures));

		return data;
	}

	private List<StationPic> buildPeprePictures(List<StationPeprePic> pictures) {
		List<StationPic> picture = new ArrayList<StationPic>();

		Map<String, List<StationPic>> pictureMap = new HashMap<String, List<StationPic>>();

		if (CollUtil.isNotEmpty(pictures)) {
			int i = 0;

			List<StationPic> tempList = new ArrayList<StationPic>();

			for (StationPeprePic ppic : pictures) {
				if (StrUtil.isNotBlank(ppic.getPicture())) {
					List<String> pics = StrUtil.split(ppic.getPicture(), ",");
					for (String pic : pics) {

						if (i % 2 == 0) {
							tempList = new ArrayList<StationPic>();
							pictureMap.put(String.valueOf(i), tempList);
						}

						StationPic stationPic = new StationPic();
						stationPic.setPic1(pic);
						if (StrUtil.isNotBlank(ppic.getCode())) {
							stationPic.setCode1(StrUtil.format("({})", ppic.getCode()));
						}

						tempList.add(stationPic);

						i++;
					}
				}
			}
		}

		if (CollUtil.isNotEmpty(pictureMap)) {
			pictureMap.forEach((id, pics) -> {

				if (CollUtil.isNotEmpty(pics)) {

					StationPic pic = pics.get(0);

					if (pics.size() >= 2) {
						StationPic picture2 = pics.get(1);
						pic.setPic2(picture2.getPic1());
						pic.setCode2(picture2.getCode1());
						pic.setDescription2(picture2.getDescription1());
					}

					picture.add(pic);
				}
			});
		}

		return picture;
	}

	private List<StationPic> buildDangerPic(List<StationDanger> stationDangerList) {
		List<StationPic> dangerPic = new ArrayList<StationPic>();

		Map<String, List<StationPic>> pictureMap = new HashMap<String, List<StationPic>>();

		if (CollUtil.isNotEmpty(stationDangerList)) {
			int i = 0;

			List<StationPic> tempList = new ArrayList<StationPic>();

			for (StationDanger danger : stationDangerList) {
				if (StrUtil.isNotBlank(danger.getDangerPic())) {
					List<String> pictures = StrUtil.split(danger.getDangerPic(), ",");
					for (String pic : pictures) {

						if (i % 2 == 0) {
							tempList = new ArrayList<StationPic>();
							pictureMap.put(String.valueOf(i), tempList);
						}

						StationPic picture = new StationPic();
						picture.setPic1(pic);
						if (StrUtil.isNotBlank(danger.getFirstCode())) {
							picture.setCode1(StrUtil.format("({})", danger.getFirstCode()));
						}

						picture.setDescription1(danger.getDescription());

						tempList.add(picture);

						i++;
					}
				}
			}
		}

		if (CollUtil.isNotEmpty(pictureMap)) {
			pictureMap.forEach((id, pics) -> {

				if (CollUtil.isNotEmpty(pics)) {

					StationPic picture = pics.get(0);

					if (pics.size() >= 2) {
						StationPic picture2 = pics.get(1);
						picture.setPic2(picture2.getPic1());
						picture.setCode2(picture2.getCode1());
						picture.setDescription2(picture2.getDescription1());
					}

					dangerPic.add(picture);
				}
			});
		}

		return dangerPic;
	}

	private StationPile buildPileForm(StationInitialReport data, Project project, List<ChargingPile> chargingPiles,
			List<StationFormData> formDatas, List<StationDanger> stationDangerList) {

		StationPile stationPile = new StationPile();
		stationPile.setName(StrUtil.format("{}、{}", data.getForm().size() + 1, MODULE_MAP.get("6")));

		if (CollUtil.isNotEmpty(formDatas) && CollUtil.isNotEmpty(chargingPiles)) {
			List<StationPileForm> pileForms = new ArrayList<StationPileForm>();

			int index = 1;
			List<ChargingPile> chargingPiles1 = chargingPiles.stream()
					.filter((d) -> "非车载充电桩".equalsIgnoreCase(d.getType())).collect(Collectors.toList());

			List<StationFormData> formData1 = formDatas.stream()
					.filter((d) -> CollUtil.isNotEmpty(d.getAttribution()) && d.getAttribution().contains("非车载充电桩"))
					.collect(Collectors.toList());

			if (CollUtil.isNotEmpty(formData1) && CollUtil.isNotEmpty(chargingPiles1)) {
				// 充电桩检测项分组
				StationPileForm pileForm1 = new StationPileForm();
				pileForm1.setName(StrUtil.format("{}、非车载充电桩", index++));

				buildStationFormData(formData1, chargingPiles1, pileForm1, stationDangerList);
				pileForms.add(pileForm1);
			}

			List<ChargingPile> chargingPiles2 = chargingPiles.stream()
					.filter((d) -> "交流充电桩".equalsIgnoreCase(d.getType())).collect(Collectors.toList());

			List<StationFormData> formData2 = formDatas.stream()
					.filter((d) -> CollUtil.isNotEmpty(d.getAttribution()) && d.getAttribution().contains("交流充电桩"))
					.collect(Collectors.toList());

			if (CollUtil.isNotEmpty(formData2) && CollUtil.isNotEmpty(chargingPiles2)) {
				StationPileForm pileForm2 = new StationPileForm();
				pileForm2.setName(StrUtil.format("{}、交流充电桩", index++));

				buildStationFormData(formData2, chargingPiles2, pileForm2, stationDangerList);
				pileForms.add(pileForm2);
			}

			stationPile.setPileForms(pileForms);
		}
		return stationPile;
	}

	private void buildStationFormData(List<StationFormData> formData, List<ChargingPile> chargingPiles,
			StationPileForm pileForm, List<StationDanger> stationDangerList) {
		List<StationPileFormDatas> pileFormDatasList = new ArrayList<StationPileFormDatas>();

		int pileSize = chargingPiles.size();
		int page = pileSize % 4 == 0 ? pileSize / 4 : (pileSize / 4) + 1;

		for (int i = 1; i <= page; i++) {
			StationPileFormDatas pileFormDatas = new StationPileFormDatas();

			List<StationPileFormData> pileFormDataList = new ArrayList<StationPileFormData>();

			List<ChargingPile> subList = chargingPiles.stream().skip((i - 1) * 4).limit(4).collect(Collectors.toList());

			for (StationFormData d : formData) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("firstCode", d.getFirstCode());
				map.put("firstContent", d.getFirstContent());
				try {
					for (int c = 1; c <= subList.size(); c++) {
						ChargingPile chargingPile = subList.get(c - 1);
						Long dangers = countChargingPileDangers(stationDangerList, chargingPile.getId(), d.getId());

						map.put("pileName" + c, chargingPile.getCode());
						map.put("result" + c, dangers > 0 ? "发现风险点" : "无风险");
					}
				} catch (Exception e) {
				}

				StationPileFormData pileFormData = BeanUtil.toBean(map, StationPileFormData.class);
				pileFormDataList.add(pileFormData);
			}
			pileFormDatas.setPileFormData(pileFormDataList);
			pileFormDatasList.add(pileFormDatas);
		}

		pileForm.setPileFormDatas(pileFormDatasList);
	}

	private Long countChargingPileDangers(List<StationDanger> stationDangerList, Long pileId, Long formDataId) {
		if (CollUtil.isNotEmpty(stationDangerList)) {
			return stationDangerList.stream().filter((d) -> {
				return d.getChargingPileId().contains(pileId) && formDataId != null
						&& formDataId.equals(d.getFormDataId());
			}).count();
		}
		return 0L;
	}
}
