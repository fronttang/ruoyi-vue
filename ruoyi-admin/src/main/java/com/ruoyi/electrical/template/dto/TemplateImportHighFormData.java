package com.ruoyi.electrical.template.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class TemplateImportHighFormData {

	private String firstContent;

	private Map<String, TemplateImportHighFormSubData> subDataMap = new HashMap<String, TemplateImportHighFormSubData>();

	@SuppressWarnings("unused")
	private List<TemplateImportHighFormSubData> subDatas;

	public List<TemplateImportHighFormSubData> getSubDatas() {
		return new ArrayList<TemplateImportHighFormSubData>(subDataMap.values());
	}
}
