package com.ruoyi.electrical.project.service;

import org.springframework.web.multipart.MultipartFile;

import com.ruoyi.common.core.domain.AjaxResult;

public interface IOwnerUnitImportService {

	AjaxResult importOwnerUnit(Long projectId, String highRiskType, MultipartFile file) throws Exception;
}
