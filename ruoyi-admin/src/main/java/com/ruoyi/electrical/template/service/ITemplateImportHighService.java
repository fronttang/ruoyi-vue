package com.ruoyi.electrical.template.service;

import java.util.List;

import com.ruoyi.electrical.template.dto.TemplateImportHighForm;

public interface ITemplateImportHighService {

	public boolean saveFormData(Long templateId, String unitType, List<TemplateImportHighForm> formList,
			boolean delete);

}
