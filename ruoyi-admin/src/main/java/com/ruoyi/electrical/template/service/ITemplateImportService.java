package com.ruoyi.electrical.template.service;

import java.util.List;

import com.ruoyi.electrical.template.dto.TemplateImportDto;

public interface ITemplateImportService {

	public boolean saveFormData(Long templateId, List<TemplateImportDto> importData, boolean delete);
}
