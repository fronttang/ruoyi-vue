package com.ruoyi.electrical.report.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.electrical.dto.OwnerUnitReportDto;
import com.ruoyi.electrical.dto.OwnerUnitReportPassDto;
import com.ruoyi.electrical.project.domain.OwnerUnit;
import com.ruoyi.electrical.project.service.IOwnerUnitService;
import com.ruoyi.electrical.report.domain.OwnerUnitReport;
import com.ruoyi.electrical.report.service.IOwnerUnitReportService;
import com.ruoyi.electrical.vo.OwnerUnitReportVo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 报告批量操作
 */
@Slf4j
@RestController
@RequestMapping("/report/batch")
public class BatchOperateController extends BaseController {

	@Autowired
	private IOwnerUnitReportService ownerUnitReportService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	@Autowired
	private OriginalRecordsReportController originalRecordsReport;
	
	@Autowired
	private UnitReportController reportGenerateService;

	/**
	 * 批量通过
	 * 
	 * @return
	 */
	@Transactional
	@PostMapping("/pass")
	public AjaxResult pass(@RequestBody OwnerUnitReportDto dto) {
		List<OwnerUnitReportVo> list = new ArrayList<OwnerUnitReportVo>();

		if (dto.getUnitIds() != null && dto.getUnitIds().length > 0) {
			list = ownerUnitReportService.selectOwnerUnitReportListByUnitIds(dto.getUnitIds(), dto.getProjectId(),
					dto.getType());
		} else {
			list = ownerUnitReportService.selectOwnerUnitReportList(dto);
		}
		if (CollUtil.isNotEmpty(list)) {
			// 只对同审批状态进行批量操作，否则提示“无效操作”

			Map<String, List<OwnerUnitReportVo>> groupByStatusMap = list.stream()
					.collect(Collectors.groupingBy(OwnerUnitReportVo::getStatus, Collectors.toList()));
			if (groupByStatusMap.size() > 1) {
				return AjaxResult.error("无效操作");
			}

			for (OwnerUnitReportVo report : list) {
				OwnerUnitReport rp = ownerUnitReportService.selectOwnerUnitReportByUnitIdAndType(report.getUnitId(),
						dto.getType());
				if (rp != null) {
					ownerUnitReportService.pass(rp.getId());
				}
			}
		}
		return AjaxResult.success();
	}

	@Transactional
	@PostMapping("/notpass")
	public AjaxResult notpass(@RequestBody OwnerUnitReportDto dto) {

		List<OwnerUnitReportVo> list = new ArrayList<OwnerUnitReportVo>();

		if (dto.getUnitIds() != null && dto.getUnitIds().length > 0) {
			list = ownerUnitReportService.selectOwnerUnitReportListByUnitIds(dto.getUnitIds(), dto.getProjectId(),
					dto.getType());
		} else {
			list = ownerUnitReportService.selectOwnerUnitReportList(dto);
		}

		if (CollUtil.isNotEmpty(list)) {
			// 只对同审批状态进行批量操作，否则提示“无效操作”

			Map<String, List<OwnerUnitReportVo>> groupByStatusMap = list.stream()
					.collect(Collectors.groupingBy(OwnerUnitReportVo::getStatus, Collectors.toList()));
			if (groupByStatusMap.size() > 1) {
				return AjaxResult.error("无效操作");
			}

			for (OwnerUnitReportVo report : list) {
				OwnerUnitReport rp = ownerUnitReportService.selectOwnerUnitReportByUnitIdAndType(report.getUnitId(),
						dto.getType());
				if (rp != null) {
					OwnerUnitReportPassDto passDto = new OwnerUnitReportPassDto();
					passDto.setReportId(rp.getId());
					passDto.setRemark(dto.getRemark());
					passDto.setOperationPic(dto.getOperationPic());
					ownerUnitReportService.notPass(passDto);
				}
			}
		}
		return AjaxResult.success();
	}

	@Transactional
	@PostMapping("/date")
	public AjaxResult setReportDate(@RequestBody OwnerUnitReportDto dto) {

		List<OwnerUnitReportVo> list = new ArrayList<OwnerUnitReportVo>();

		if (dto.getUnitIds() != null && dto.getUnitIds().length > 0) {
			list = ownerUnitReportService.selectOwnerUnitReportListByUnitIds(dto.getUnitIds(), dto.getProjectId(),
					dto.getType());
		} else {
			list = ownerUnitReportService.selectOwnerUnitReportList(dto);
		}
		if (CollUtil.isNotEmpty(list)) {

			for (OwnerUnitReportVo report : list) {
				OwnerUnitReport rp = ownerUnitReportService.selectOwnerUnitReportByUnitIdAndType(report.getUnitId(),
						dto.getType());
				if (rp != null) {

					rp.setStartDate(dto.getStartDate());
					rp.setEndDate(dto.getEndDate());

					ownerUnitReportService.setReportDate(rp);
				}
			}
		}
		return AjaxResult.success();
	}

	/**
	 * 1制式报告，2归档报告，3原始记录
	 * 
	 * @param dto
	 * @param type
	 * @return
	 * @throws IOException
	 */
	@Transactional
	@PostMapping("/download/{type}")
	public AjaxResult download(@RequestBody OwnerUnitReportDto dto, @PathVariable String type) {

		List<OwnerUnitReportVo> list = new ArrayList<OwnerUnitReportVo>();

		if (dto.getUnitIds() != null && dto.getUnitIds().length > 0) {
			list = ownerUnitReportService.selectOwnerUnitReportListByUnitIds(dto.getUnitIds(), dto.getProjectId(),
					dto.getType());
		} else {
			list = ownerUnitReportService.selectOwnerUnitReportList(dto);
		}

		ZipOutputStream zip = null;
		try {

			if (CollUtil.isNotEmpty(list)) {

				LocalDateTime now = LocalDateTime.now();
				String timestamp = DateUtil.format(now, DatePattern.PURE_DATETIME_MS_PATTERN);
				String zipFileName = timestamp + IdUtils.fastSimpleUUID().toUpperCase() + ".zip";
				String datePath = DateUtil.format(now, "yyyy/MM/dd");

				String zipFilePath = StrUtil.format("{}/{}", datePath, zipFileName);

				String baseDir = RuoYiConfig.getUploadPath();
				File saveFile = FileUploadUtils.getAbsoluteFile(baseDir, zipFilePath);

				FileOutputStream outputStream = new FileOutputStream(saveFile);
				zip = new ZipOutputStream(outputStream);

				for (OwnerUnitReportVo report : list) {
					OwnerUnitReport rp = ownerUnitReportService.selectOwnerUnitReportByUnitIdAndType(report.getUnitId(),
							dto.getType());
					if (rp != null) {

						OwnerUnit ownerUnit = ownerUnitService.selectOwnerUnitById(report.getUnitId());
						if (ownerUnit == null) {
							continue;
						}

						String fileName = null;
						String filePath = null;

						if ("1".equalsIgnoreCase(type)) {
							fileName = StrUtil.format("Z{}.docx", ownerUnit.getName());

							String wordFilePath = rp.getWordFile();
							if (StrUtil.isNotBlank(wordFilePath)) {
								String localPath = RuoYiConfig.getProfile();
								filePath = localPath
										+ StringUtils.substringAfter(wordFilePath, Constants.RESOURCE_PREFIX);
							} else {
								try {
									// 沒有就直接生成
									AjaxResult initialReport = reportGenerateService.initialReport(ownerUnit.getId(), report.getType());
									if (initialReport != null && initialReport.isSuccess()) {
	
										String initialReportPath = String
												.valueOf(initialReport.get(AjaxResult.DATA_TAG));
	
										if (StrUtil.isNotBlank(initialReportPath)) {
											String localPath = RuoYiConfig.getProfile();
											filePath = localPath + StringUtils.substringAfter(initialReportPath,
													Constants.RESOURCE_PREFIX);
										}
									}
								}catch (Exception e) {
									log.error("", e);
								}
							}
						} else if ("2".equalsIgnoreCase(type)) {
							fileName = StrUtil.format("C{}.docx", ownerUnit.getName());

							String archivedWord = rp.getArchivedWord();
							if (StrUtil.isNotBlank(archivedWord)) {
								String localPath = RuoYiConfig.getProfile();
								filePath = localPath
										+ StringUtils.substringAfter(archivedWord, Constants.RESOURCE_PREFIX);
							} else {
								try {
									// 沒有就直接生成
									AjaxResult initialReport = reportGenerateService.initialReport(ownerUnit.getId(), report.getType());
									if (initialReport != null && initialReport.isSuccess()) {
	
										String initialReportPath = String
												.valueOf(initialReport.get(AjaxResult.DATA_TAG));
	
										if (StrUtil.isNotBlank(initialReportPath)) {
											String localPath = RuoYiConfig.getProfile();
											filePath = localPath + StringUtils.substringAfter(initialReportPath,
													Constants.RESOURCE_PREFIX);
										}
									}
								}catch (Exception e) {
									log.error("", e);
								}
							}
						} else if ("3".equalsIgnoreCase(type)) {
							fileName = StrUtil.format("Y{}.docx", ownerUnit.getName());
							try {
								AjaxResult originalRecords = originalRecordsReport.originalRecords(ownerUnit.getId(),
										report.getType());
								if (originalRecords != null && originalRecords.isSuccess()) {

									String originalRecordPath = String
											.valueOf(originalRecords.get(AjaxResult.DATA_TAG));

									if (StrUtil.isNotBlank(originalRecordPath)) {
										String localPath = RuoYiConfig.getProfile();
										filePath = localPath + StringUtils.substringAfter(originalRecordPath,
												Constants.RESOURCE_PREFIX);
									}
								}
							} catch (Exception e) {
								log.error("", e);
							}
						}
						if (StrUtil.isNotBlank(fileName) || StrUtil.isNotBlank(filePath)) {
							try {
								zip.putNextEntry(new ZipEntry(fileName));
								IoUtil.write(zip, false, FileUtil.readBytes(filePath));
								zip.flush();
								zip.closeEntry();
							} catch (Exception e) {
								log.error("", e);
							}
						}
					}
				}
				zip.finish();
				String path = FileUploadUtils.getPathFileName(baseDir, zipFilePath);
				return AjaxResult.success().put("data", path);
			} else {
				return AjaxResult.error();
			}
		} catch (Exception e) {
			log.error("", e);
			return AjaxResult.error();
		} finally {
			try {
				zip.close();
			} catch (IOException e) {
				log.error("", e);
			}
		}

	}
}
