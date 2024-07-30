package com.ruoyi.electrical.template.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TemplateImportHighFormSubData {

	private String firstContent;

	private String maxScore;

	private String accMethod;

	private List<TemplateImportHighFormDataDanger> dangers = new ArrayList<TemplateImportHighFormDataDanger>();
}
