package com.ruoyi.electrical.template.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class TemplateImportHighForm {

	private String name;

	private String maxScore;

	private Map<String, TemplateImportHighFormData> dataMap = new HashMap<String, TemplateImportHighFormData>();

	@SuppressWarnings("unused")
	private List<TemplateImportHighFormData> datas;

	public List<TemplateImportHighFormData> getDatas() {
		return new ArrayList<TemplateImportHighFormData>(dataMap.values());
	}
}
