package com.ruoyi.electrical.template.service;

import java.util.List;

import com.ruoyi.electrical.template.dto.TemplateImportStationDto;

public interface ITemplateImportStationService {

	public boolean saveFormData(Long templateId, List<TemplateImportStationDto> importData, boolean delete);
}
